import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {RegistrationRequestDto} from "../../core/model/dto/registrationRequestDto";
import {InfoModalService} from "../../core/services/util/info-modal.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  emailRegexp = new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
  isLoading: boolean;
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  confirmPassword = '';
  // @ts-ignore
  message: string;

  constructor(private authenticationService: AuthenticationService,
              private modalMessageService: InfoModalService,
              public activeModal: NgbActiveModal) {
    this.isLoading = false;
  }

  ngOnInit(): void {
  }

  registerUser(): void {
    if (this.validateFieldsCorrect()) {
      this.isLoading = true;
      let dto: RegistrationRequestDto = new RegistrationRequestDto(this.email, this.password, this.firstName, this.lastName);
      this.authenticationService.registerUser(dto)
        .subscribe( next => {
          //   this.isSent = true;
              console.log(next);
              this.activeModal.close();
              this.modalMessageService.show('Successful registration!',
                'Your account was successfully created. You can now log in to your account');
          },
          error => {
            this.message = error.error
              ? error.error.message
              : 'Something went wrong during the registration. Please try again or contact the administrators';
            console.error(error);
          }
        );
      this.isLoading = false;
    }

  }

  private validateFieldsCorrect(): boolean {
    if (!this.firstName) {
      this.message = 'The first name field must not be empty';
      return false;
    }
    if (!this.email) {
      this.message = 'The email field must not be empty';
      return false;
    }
    if (!this.emailRegexp.test(this.email)) {
      this.message = 'Please, provide valid email';
      return false;
    }
    if (!this.password || this.password.length < 5) {
      this.message = 'The password is too short';
      return false;
    }
    if (this.password.trim().length === 0) {
      this.message = 'Please, provide not blank password (any characters excluding whitespace)';
      return false;
    }
    if (this.password !== this.confirmPassword) {
      this.message = "The password and password confirmation don't match"
      return false;
    }
    return true;
  }

}
