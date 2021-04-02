import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminCreationComponent } from './admin-creation/admin-creation.component';
import {SharedModule} from "../shared/shared.module";
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [AdminCreationComponent],
  imports: [
    CommonModule,
    SharedModule,
    FormsModule
  ]
})
export class AdminModule { }
