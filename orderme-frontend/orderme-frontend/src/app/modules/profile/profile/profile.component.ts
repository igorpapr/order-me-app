import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../core/model/user";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserService} from "../../core/services/user/user.service";
import {Subscription} from "rxjs";
import {ToastsService} from "../../core/services/util/toasts.service";
import {UserDto} from "../../core/model/dto/userDto";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {

  // @ts-ignore
  currentUser: User;

  currentUserCopy: User | undefined;
  isLoading: boolean =  true;
  subscription: Subscription = new Subscription();

  isEditModeEnabled: boolean = false;

  //todo change password (maybe reuse the recover password component)

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private toastsService: ToastsService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.fetchCurrentUser();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private fetchCurrentUser(): void {
    this.subscription.add(this.userService.getUserById(this.authenticationService.currentUserValue.userId)
      .subscribe(data => {
          this.currentUser = data;
          this.isLoading = false;
        },
        error => {
          this.isLoading = false;
          console.error(error);
          this.toastsService.toastAddDanger("Something went wrong while fetching the user's data. Please, contact the administrator");
        })
    );
  }

  public startEdit(): void {
    this.isEditModeEnabled = true;
    this.currentUserCopy = new User();
    this.currentUserCopy = Object.assign(this.currentUserCopy, this.currentUser);
  }

  public saveUserData(): void {
    this.isLoading = true;
    let userDto: UserDto = new UserDto();
    //todo - add email confirmation
    userDto.email = this.currentUser.email;
    userDto.firstName = this.currentUser.firstName;
    userDto.lastName = this.currentUser.lastName;
    this.subscription.add(this.userService.patchUser(userDto, this.currentUser.userId).subscribe(
      data => {
        this.currentUser = data;
        this.isLoading = false;
        this.isEditModeEnabled = false;
        this.toastsService.toastAddSuccess('Your data was successfully edited');
      }, error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong while editing ')
      }
    ));
  }

  public cancelEditing(): void {
    this.currentUser = Object.assign(this.currentUser, this.currentUserCopy);
    this.currentUserCopy = undefined;
    this.isEditModeEnabled = false;
  }

  changePassword() {
    if (this.isEditModeEnabled) {
      this.cancelEditing();
    }
    //todo - change
  }
}
