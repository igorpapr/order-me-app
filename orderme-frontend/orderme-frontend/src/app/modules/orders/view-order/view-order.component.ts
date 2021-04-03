import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Subscription} from "rxjs";
import {OrderService} from "../../core/services/orders/order.service";
import {Order} from "../../core/model/order";
import {OrderStatus} from "../../core/model/order-status";
import {DateService} from "../../core/services/util/date.service";
import {OrderDto} from "../../core/model/dto/orderDto";
import {OrderLineDto} from "../../core/model/dto/order-line-dto";
import {OrderLine} from "../../core/model/order-line";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss']
})
export class ViewOrderComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription();
  currentOrderId: string;
  isLoading: boolean;
  // @ts-ignore
  currentOrder: Order = new Order();
  // @ts-ignore
  cachedOrderLinesBeforeSaving: OrderLine[];
  isEditModeEnabled: boolean = false;
  // @ts-ignore
  isCurrentUserIsAdminAndProcessing: boolean;
  isEditingStatus: boolean = false;
  // @ts-ignore
  private cachedStatus: OrderStatus | undefined;
  // @ts-ignore
  orderStatusSelectorHolder: OrderStatus;
  availableOrderStatuses: OrderStatus[] = [];

  constructor(private ordersService: OrderService,
              private activatedRoute: ActivatedRoute,
              private toastsService: ToastsService,
              public dateService: DateService,
              private authenticationService: AuthenticationService) {
    this.isLoading = false;
    this.currentOrderId = this.activatedRoute.snapshot.params.id;

  }

  ngOnInit(): void {
    this.isLoading = true;
    this.fetchCurrentOrder();
 }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private fetchCurrentOrder() {
    this.subscription.add(
      this.ordersService.getOrderById(this.currentOrderId)
        .subscribe(
          data => {
            this.currentOrder = data;
            this.isLoading = false;
            this.isCurrentUserIsAdminAndProcessing =
              this.currentOrder.processingBy &&
              (this.authenticationService.currentUserValue.userRole === UserRole.ADMIN)
              && (this.currentOrder.processingBy.userId === this.authenticationService.currentUserValue.userId);
          }, error => {
            console.error(error);
            this.toastsService.toastAddDanger('Something went wrong while fetching this order. ' +
              'Please, contact administrator.');
          }
        )
    );
  }

  getOrderStatus(status: OrderStatus) {
    // @ts-ignore
    return OrderStatus[status];
  }

  isOrderEditable(): boolean {
    // @ts-ignore
    switch (OrderStatus[this.currentOrder.orderStatus]) {
      case OrderStatus.WAITING_FOR_PROCESSING:
        return true;
      default:
        return false;
    }
  }

  startEditing(): void {
    this.cachedOrderLinesBeforeSaving = this.currentOrder.orderLines.map(l => ({...l}));
    this.isEditModeEnabled = true;
  }

  saveOrderLines() {
    //todo modal yes or not here
    //todo validation of amounts here
    let orderDto = new OrderDto();
    orderDto.orderLines = this.mapOrderLinesToDtos(this.currentOrder.orderLines);

    this.subscription.add(this.ordersService.patchOrder(orderDto, this.currentOrderId)
      .subscribe(
        data => {
          this.currentOrder = data;
          this.toastsService.toastAddSuccess('The order was successfully edited')
          this.isEditModeEnabled = false;
          this.cachedOrderLinesBeforeSaving = [];
        }, error => {
          console.error(error);
          this.toastsService.toastAddDanger("Something went wrong during editing this order. Please, contact administrator");
        }
      ));
  }

  private mapOrderLinesToDtos(orderLines: OrderLine[]): OrderLineDto[] {
    return orderLines.map(
      orderLine => {
        let dto = new OrderLineDto();
        dto.orderId = orderLine.orderLineId.orderId;
        dto.goodsId = orderLine.orderLineId.goodsId;
        dto.amount = orderLine.amount;
        return dto;
      }
    )
  }

  recalculateFullPrice() {
      this.currentOrder.fullPrice = this.currentOrder.orderLines
        .map(x => x.goods.actualPrice * x.amount)
        .reduce((a,b) => a + b, 0);
  }

  cancelSaving() {
    this.currentOrder.orderLines = this.cachedOrderLinesBeforeSaving.map(i => ({...i}));
    this.cachedOrderLinesBeforeSaving = [];
    this.isEditModeEnabled = false;
  }

  isStatusEditable(): boolean {
    // @ts-ignore
    switch (OrderStatus[this.currentOrder.orderStatus]) {
      case OrderStatus.CANCELED:
      case OrderStatus.COMPLETED:
        return false;
      default:
        return true;
    }
  }

  cancelOrder(): void {
    //todo yesno modal
    let orderDto = new OrderDto();
    orderDto.orderStatus = this.ordersService.getOrderStatusKey(OrderStatus.CANCELED);
    this.subscription.add(
      this.ordersService.patchOrder(orderDto, this.currentOrderId)
        .subscribe(
          data => {
            this.currentOrder = data;
            this.toastsService.toastAddSuccess('The order was successfully canceled')
          }, error => {
            console.error(error);
            this.toastsService.toastAddDanger("Something went wrong during canceling this order. Please, contact administrator");
          }
        ));
  }

  startEditingStatus(): void {
    this.isEditingStatus = true;
    this.fillAvailableOrderStatuses();
    if (this.availableOrderStatuses.length != 0) {
      this.setOrderStatusSelectorHolder(this.availableOrderStatuses[0]);
    }
    this.cachedStatus = JSON.parse(JSON.stringify(this.currentOrder.orderStatus));
  }

  setOrderStatusSelectorHolder(value: any) {
  // @ts-ignore
  this.orderStatusSelectorHolder = this.ordersService.getOrderStatusKey(value);
}

  saveNewOrderStatus(): void {
    let orderDto = new OrderDto();
    // @ts-ignore
    orderDto.orderStatus = this.orderStatusSelectorHolder;
    this.subscription.add(
      this.ordersService.patchOrder(orderDto, this.currentOrderId)
        .subscribe(
          data => {
            this.currentOrder = data;
            this.isEditingStatus = false;
            this.cachedStatus = undefined;
            this.toastsService.toastAddSuccess('The order status was successfully updated')
          }, error => {
            console.error(error);
            this.toastsService.toastAddDanger("Something went wrong during updating this order. Please, contact administrator");
          }
        ));
  }

  cancelStatusEditing(): void {
    this.isEditingStatus = false;
    this.currentOrder.orderStatus = JSON.parse(JSON.stringify(this.cachedStatus));
    this.cachedStatus = undefined;
  }

  fillAvailableOrderStatuses(): void {
    if (this.currentOrder.orderStatus) {
      // @ts-ignore
      let statusValue = OrderStatus[this.currentOrder.orderStatus];
      switch (statusValue) {
        case OrderStatus.WAITING_FOR_PROCESSING:
          this.availableOrderStatuses = [OrderStatus.PROCESSING, OrderStatus.COLLECTING, OrderStatus.READY, OrderStatus.COMPLETED, OrderStatus.CANCELED]
          break;
        case OrderStatus.PROCESSING:
          this.availableOrderStatuses = [OrderStatus.COLLECTING, OrderStatus.READY, OrderStatus.COMPLETED, OrderStatus.CANCELED]
          break;
        case OrderStatus.COLLECTING:
          this.availableOrderStatuses = [OrderStatus.READY, OrderStatus.COMPLETED, OrderStatus.CANCELED];
          break;
        case OrderStatus.READY:
          this.availableOrderStatuses = [OrderStatus.COMPLETED, OrderStatus.CANCELED];
          break;
        default:
          break;
      }
    }
  }

  canBeCanceled() {
    return this.isStatusEditable() && !this.isEditingStatus &&
      (this.isCurrentUserIsAdminAndProcessing ||
        this.authenticationService.currentUserValue.userId === this.currentOrder.createdBy.userId);

  }
}
