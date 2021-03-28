import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninComponent } from './signin/signin.component';
import {SharedModule} from "../shared/shared.module";
import {AppRoutingModule} from "../../app-routing.module";
import { SignupComponent } from './signup/signup.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [SigninComponent, SignupComponent],
  imports: [
    CommonModule,
    SharedModule,
    AppRoutingModule,
    FormsModule
  ],
  exports: [
    SigninComponent
  ]
})
export class AuthModule { }
