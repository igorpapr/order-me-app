import {Component, OnDestroy, OnInit} from '@angular/core';
import {Order} from "../../core/model/order";
import {OrderService} from "../../core/services/orders/order.service";
import {Subscription} from "rxjs";
import {Page} from "../../core/model/page";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import {ToastsService} from "../../core/services/util/toasts.service";
import {OrderStatus} from "../../core/model/order-status";
import {DateService} from "../../core/services/util/date.service";
import {WindowService} from "../../core/services/util/window.service";

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.scss']
})
export class MyOrdersComponent implements OnInit, OnDestroy {
  active = 1;
  isLoading: boolean = false;
  isEmpty: boolean = false;
  readonly pageSize: number = 1;
  //todo
  shopId: number = 4;

  subscription: Subscription = new Subscription();
  faSpinner = faSpinner;
  currentPage: number;
  // @ts-ignore
  paginationObject: Page<Order>;
  ordersList: Order[] = [];

  constructor(private orderService: OrderService,
              private authenticationService: AuthenticationService,
              private toastsService: ToastsService,
              public dateService: DateService,
              private windowService: WindowService) {
    this.isLoading = false;
    this.isEmpty = false;
    this.currentPage = 1;
  }

  ngOnInit(): void {
    this.fetchOrders();
  }

  private fetchOrders() {
    this.subscription.add(
      this.orderService.getOrdersList(this.currentPage - 1, this.pageSize,
        null, this.authenticationService.currentUserValue.userId,
        null, null)
        .subscribe(
          data => {
            this.paginationObject = data;
            if (data.content?.length===0) {
              this.isEmpty = true;
            }
            this.ordersList = data.content;
            this.isLoading = false;
          }, error => {
            console.error(error);
            this.toastsService.toastAddDanger('Something went wrong while fetching the orders list. Please, contact the administrator');
          }
        )
    )
  }

  changePage(event: number) {
    this.currentPage = event;
    this.fetchOrders();
    this.windowService.scrollToTop();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getOrderStatus(status: OrderStatus) {
    // @ts-ignore
    return OrderStatus[status.toString()];
  }
}
