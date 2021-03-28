import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LandingPageComponent} from "./modules/main/landing-page/landing-page.component";
import {CategoriesComponent} from "./modules/goods/categories/categories.component";
import {MyOrdersComponent} from "./modules/orders/my-orders/my-orders.component";
import {SigninComponent} from "./modules/auth/signin/signin.component";

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'categories', component: CategoriesComponent},
  {path: 'orders/my', component: MyOrdersComponent},
  {path: 'sign-in', component:SigninComponent},
  // {path: '/goods', component: },
  // {path: '/contact', component: LandingPageComponent},
  // {path: '', component: LandingPageComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
