import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SharedModule} from "../shared/shared.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { ProfileComponent } from './profile/profile.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [ProfileComponent],
  imports: [
    CommonModule,
    SharedModule,
    NgbModule,
    FormsModule
  ],
  exports: [ProfileComponent]
})
export class ProfileModule { }
