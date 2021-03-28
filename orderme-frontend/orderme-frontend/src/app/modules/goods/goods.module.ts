import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import {SharedModule} from "../shared/shared.module";
import {CoreModule} from "../core/core.module";
import {AppRoutingModule} from "../../app-routing.module";
import { GoodsByCategoryComponent } from './goods-by-category/goods-by-category.component';
import { ViewGoodsComponent } from './view-goods/view-goods.component';



@NgModule({
  declarations: [CategoriesComponent, GoodsByCategoryComponent, ViewGoodsComponent],
  imports: [
    CommonModule,
    SharedModule,
    CoreModule,
    AppRoutingModule
  ]
})
export class GoodsModule { }
