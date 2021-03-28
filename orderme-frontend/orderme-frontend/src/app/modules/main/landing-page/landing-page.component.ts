import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {AuthenticationRequestDto} from "../../core/model/dto/authenticationRequestDto";

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

}
