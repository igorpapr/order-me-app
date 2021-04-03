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
    this.isLoading = false;
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
}
