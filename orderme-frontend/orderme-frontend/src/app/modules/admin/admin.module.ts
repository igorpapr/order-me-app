import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminCreationComponent} from './admin-creation/admin-creation.component';
import {SharedModule} from "../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";


@NgModule({
  declarations: [AdminCreationComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    NgBootstrapFormValidationModule
  ]
})
export class AdminModule { }
