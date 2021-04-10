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
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import { GoodsCreateComponent } from './goods-create/goods-create.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { CategoryEditComponent } from './category-edit/category-edit.component';
import {AngularFireStorageModule} from "@angular/fire/storage";
import { GoodsAvailabilityConfigurationComponent } from './goods-availability-configuration/goods-availability-configuration.component';



@NgModule({
  declarations: [CategoriesComponent, GoodsByCategoryComponent, ViewGoodsComponent, AllGoodsComponent, CategoryCreateComponent, GoodsCreateComponent, CategoryEditComponent, GoodsAvailabilityConfigurationComponent],
    imports: [
        CommonModule,
        SharedModule,
        CoreModule,
        AppRoutingModule,
        ReactiveFormsModule,
        NgBootstrapFormValidationModule,
        FontAwesomeModule,
        AngularFireStorageModule,
        FormsModule
    ]
})
export class GoodsModule { }
