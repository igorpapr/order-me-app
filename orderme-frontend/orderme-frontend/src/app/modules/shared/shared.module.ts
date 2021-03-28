import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { ToastsComponent } from './toasts/toasts.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {AppRoutingModule} from "../../app-routing.module";
import { InfoModalComponent } from './info-modal/info-modal.component';

@NgModule({
  declarations: [NavbarComponent, ToastsComponent, InfoModalComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule
  ],
  exports: [
    NavbarComponent,
    ToastsComponent,
    NgbModule
  ]
})
export class SharedModule { }
