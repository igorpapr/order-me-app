import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {Page} from "../../core/model/page";
import {Order} from "../../core/model/order";
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import {OrderService} from "../../core/services/orders/order.service";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {ToastsService} from "../../core/services/util/toasts.service";
import {DateService} from "../../core/services/util/date.service";
import {OrderStatus} from "../../core/model/order-status";
import {WindowService} from "../../core/services/util/window.service";
import {OrderDto} from "../../core/model/dto/orderDto";
import {Router} from "@angular/router";
import {ShopsService} from "../../core/services/shops/shops.service";
import {Shop} from "../../core/model/shop";

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.scss']
})
export class AdminOrdersComponent implements OnInit, OnDestroy {
  active = 1;
  isLoading: boolean = false;
  isEmpty: boolean = false;
  readonly pageSize: number = 3;
  currentShop: Observable<Shop>;
  subscription: Subscription = new Subscription();
  faSpinner = faSpinner;
  currentPageNumber: number = 1;
  // @ts-ignore
  paginationObject: Page<Order>;
  ordersList: Order[] = [];
  isProcessingByMeChecked: boolean = true;
  isUnprocessedOnlyChecked: boolean = false;
  // @ts-ignore
  filterOrderStatus: OrderStatus;
  orderStatusesToFilter: string[];

  constructor(private orderService: OrderService,
              private authenticationService: AuthenticationService,
              private toastsService: ToastsService,
              public dateService: DateService,
              private windowService: WindowService,
              private router: Router,
              private shopsService: ShopsService) {
    // @ts-ignore
    // This is being used because typescript doesn't have reverse mapping of string enums
    let statuses: string[] = Object.values(OrderStatus).map(value => value.toString());
    statuses.unshift("All")
    this.orderStatusesToFilter = statuses;
    this.currentShop = shopsService.currentShop;
    this.currentShop.subscribe(
      () => {
        this.fetchOrders();
      }
    )
  }

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.isLoading = true;
    this.subscription.add(this.orderService.getOrdersList(
      this.currentPageNumber - 1,
            this.pageSize,
            this.shopsService.currentShopValue?.shopId,
            null,
      this.isProcessingByMeChecked ? this.authenticationService.currentUserValue.userId : null,
            this.filterOrderStatus,
            this.isUnprocessedOnlyChecked
    ).subscribe(
      data => {
        this.isLoading = false;
        this.isEmpty = data?.content.length === 0;
        this.paginationObject = data;
      }, error => {
        console.error(error);
        this.isLoading = false;
        this.toastsService.toastAddDanger("Something went wrong while fetching the orders. Please, contact the administrator");
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  // This is being used because typescript doesn't have reverse mapping of string enums
  getOrderStatus(status: OrderStatus) {
    // @ts-ignore
    return OrderStatus[status.toString()];
  }

  changePage($event: number) {
    this.currentPageNumber = $event;
    this.fetchOrders();
    this.windowService.scrollToTop();
  }

  // This is being used because typescript doesn't have reverse mapping of string enums
  setOrderStatusFilter(value: any) {
    // @ts-ignore
    this.filterOrderStatus = Object.keys(OrderStatus).find(key => OrderStatus[key] === value);
    this.fetchOrders();
  }

  processingByMeCheck(processingBy: any): boolean {
    if (processingBy) {
      if (processingBy.userId === this.authenticationService.currentUserValue.userId) {
        return true;
      }
    }
    return false;
  }

  startProcessing(orderId: string, changeStatus: boolean) {
    this.isLoading = true;
    let orderDto: OrderDto = new OrderDto();
    if (changeStatus) {
      orderDto.orderStatus = this.orderService.getOrderStatusKey(OrderStatus.PROCESSING);
    }
    orderDto.processingById = this.authenticationService.currentUserValue.userId;
    if (orderId) {
      this.subscription.add(this.orderService.patchOrder(orderDto, orderId).subscribe(
        data => {
          this.isLoading = false;
          this.router.navigateByUrl(`/orders/${data.orderId}`);
          this.toastsService.toastAddSuccess('Successfully started processing this order by you!');
        }, error => {
          console.error(error);
          this.toastsService.toastAddDanger('Something went wrong during updating the order. Please, contact the administrator');
        }
      ))
    }
  }
}
