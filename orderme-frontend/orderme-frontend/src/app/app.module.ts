import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from "./modules/core/core.module";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {SharedModule} from "./modules/shared/shared.module";
import {OrdersModule} from "./modules/orders/orders.module";
import {GoodsModule} from "./modules/goods/goods.module";
import {AuthModule} from "./modules/auth/auth.module";
import {ProfileModule} from "./modules/profile/profile.module";
import {AdminModule} from "./modules/admin/admin.module";
import {NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    CoreModule,
    FontAwesomeModule,
    SharedModule,
    ProfileModule,
    OrdersModule,
    GoodsModule,
    AuthModule,
    ReactiveFormsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule,
    AdminModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
