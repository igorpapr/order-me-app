import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { ToastsComponent } from './toasts/toasts.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {AppRoutingModule} from "../../app-routing.module";
import { InfoModalComponent } from './info-modal/info-modal.component';
import { ShopsComponent } from './shops/shops.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [NavbarComponent, ToastsComponent, InfoModalComponent, ShopsComponent],
    imports: [
        CommonModule,
        AppRoutingModule,
        NgbModule,
        FontAwesomeModule,
        FormsModule
    ],
  exports: [
    NavbarComponent,
    ToastsComponent,
    NgbModule
  ]
})
export class SharedModule { }
