import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {CoreModule} from "../core/core.module";
import {SharedModule} from "../shared/shared.module";



@NgModule({
  declarations: [LandingPageComponent],
  imports: [
    CommonModule,
    CoreModule,
    SharedModule
  ]
})
export class MainModule { }
