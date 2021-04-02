import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {RegistrationRequestDto} from "../../core/model/dto/registrationRequestDto";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-admin-creation',
  templateUrl: './admin-creation.component.html',
  styleUrls: ['./admin-creation.component.scss']
})
export class AdminCreationComponent implements OnInit {

  private subscription: Subscription = new Subscription();

  // @ts-ignore
  formGroup: FormGroup =  new FormGroup({
    Email: new FormControl('', [
      Validators.required,
      Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
    ]),
    FirstName: new FormControl('', [
      Validators.required,
      Validators.minLength(1)
    ]),
    LastName: new FormControl('', [
      Validators.required,
      Validators.minLength(1)
    ]),
    Password: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20)
    ]),
    ConfirmPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20)
    ])
    // @ts-ignore
  }, { validators : notMatchingConfirmPasswordValidator});


  constructor(private authenticationService: AuthenticationService,
              private toastsService: ToastsService) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {

  }

  onSubmit() {
    let email = this.formGroup.get('Email')?.value;
    let firstName = this.formGroup.get('FirstName')?.value;
    let lastName = this.formGroup.get('LastName')?.value;
    let password = this.formGroup.get('Password')?.value;
    const request = new RegistrationRequestDto(email, password, firstName, lastName);
    this.subscription.add(this.authenticationService.registerAdmin(request).subscribe(
      data => {
        this.toastsService.toastAddSuccess('The new admin was successfully created!');
      }, error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong during new admin creation. Check console')
      }
    ));
  }

  onReset() {
    this.formGroup.reset();
  }
}

export const notMatchingConfirmPasswordValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
    const confirmPassword = control.get("ConfirmPassword")?.value;
    const password = control.get("Password")?.value;
    return (confirmPassword === password) ? null : {passwordsDontMatch: {value: control.value}};
}
