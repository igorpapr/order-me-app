import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import {SharedModule} from "../shared/shared.module";
import {CoreModule} from "../core/core.module";
import {AppRoutingModule} from "../../app-routing.module";
import { GoodsByCategoryComponent } from './goods-by-category/goods-by-category.component';
import { ViewGoodsComponent } from './view-goods/view-goods.component';
import { AllGoodsComponent } from './all-goods/all-goods.component';
import { CategoryCreateComponent } from './category-create/category-create.component';
import {ReactiveFormsModule} from "@angular/forms";
import {NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import { GoodsCreateComponent } from './goods-create/goods-create.component';



@NgModule({
  declarations: [CategoriesComponent, GoodsByCategoryComponent, ViewGoodsComponent, AllGoodsComponent, CategoryCreateComponent, GoodsCreateComponent],
  imports: [
    CommonModule,
    SharedModule,
    CoreModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgBootstrapFormValidationModule
  ]
})
export class GoodsModule { }
