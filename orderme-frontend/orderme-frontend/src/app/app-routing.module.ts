import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LandingPageComponent} from "./modules/main/landing-page/landing-page.component";
import {CategoriesComponent} from "./modules/goods/categories/categories.component";
import {MyOrdersComponent} from "./modules/orders/my-orders/my-orders.component";
import {SigninComponent} from "./modules/auth/signin/signin.component";
import {GoodsByCategoryComponent} from "./modules/goods/goods-by-category/goods-by-category.component";
import {ViewGoodsComponent} from "./modules/goods/view-goods/view-goods.component";
import {CartComponent} from "./modules/orders/cart/cart.component";
import {ViewOrderComponent} from "./modules/orders/view-order/view-order.component";
import {AllGoodsComponent} from "./modules/goods/all-goods/all-goods.component";
import {ProfileComponent} from "./modules/profile/profile/profile.component";
import {AdminCreationComponent} from "./modules/admin/admin-creation/admin-creation.component";

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'categories', component: CategoriesComponent},
  {path: 'my-orders', component: MyOrdersComponent},
  {path: 'sign-in', component:SigninComponent},
  {path: 'categories/:id', component: GoodsByCategoryComponent},
  {path: 'goods/:id', component: ViewGoodsComponent},
  {path: 'goods', component: AllGoodsComponent},
  {path: 'cart', component: CartComponent},
  {path: 'orders/:id', component: ViewOrderComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'admin-create', component: AdminCreationComponent},
  // {path: '/contact', component: LandingPageComponent},
  // {path: '/info', component: LandingPageComponent},
  // {path: '', component: LandingPageComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
