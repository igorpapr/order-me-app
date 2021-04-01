import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { AdminOrdersComponent } from './admin-orders/admin-orders.component';
import {SharedModule} from "../shared/shared.module";
import { CartComponent } from './cart/cart.component';
import {AppRoutingModule} from "../../app-routing.module";
import { ViewOrderComponent } from './view-order/view-order.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [MyOrdersComponent, AdminOrdersComponent, CartComponent, ViewOrderComponent],
  imports: [
    CommonModule,
    SharedModule,
    AppRoutingModule,
    FormsModule
  ]
})
export class OrdersModule { }
