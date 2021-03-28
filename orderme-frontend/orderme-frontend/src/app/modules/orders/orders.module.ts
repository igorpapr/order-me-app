import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { AdminOrdersComponent } from './admin-orders/admin-orders.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [MyOrdersComponent, AdminOrdersComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class OrdersModule { }
