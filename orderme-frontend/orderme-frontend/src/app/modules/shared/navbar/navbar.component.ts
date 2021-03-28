import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {SigninComponent} from "../../auth/signin/signin.component";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";
import {Router} from "@angular/router";
import {SignupComponent} from "../../auth/signup/signup.component";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit{

  isSignedIn: boolean;
  isAdmin: boolean;
  isSuperAdmin: boolean;

  constructor(private authenticationService: AuthenticationService,
              private modalService: NgbModal,
              private router: Router) {
    this.isSignedIn = false;
    this.isAdmin = false;
    this.isSuperAdmin = false
  }


  ngOnInit(): void {
    this.isSignedIn = (this.authenticationService.currentUserValue !== undefined);
    if (this.isSignedIn) {
      let currentRole = this.authenticationService.currentUserValue.userRole;
      this.isAdmin = (this.isSignedIn &&
        currentRole === UserRole.ADMIN);
      this.isSuperAdmin = (this.isSignedIn &&
        currentRole === UserRole.SUPER_ADMIN);
    }
  }

  openSignInForm() {
      this.modalService.open(SigninComponent);
  }

  openSignUpForm() {
    this.modalService.open(SignupComponent);
  }

  logOut() {
    this.router.navigate(['/']).then(
      () => {
        this.authenticationService.logOut();
        location.reload()}
    );
  }

}
