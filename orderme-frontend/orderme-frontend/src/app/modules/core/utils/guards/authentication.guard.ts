import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthenticationService} from "../../services/auth/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router, private authenticationService: AuthenticationService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const info = this.authenticationService.currentUserValue;

    if (info) {
      if (next.data.roles && next.data.roles.indexOf(info.userRole) === -1) {
        return this.unavailable();
      }
    } else {
      return this.unavailable();
    }
    return true;
  }

  unavailable(): boolean {
    this.router.navigate(['/']);
    //possibly 404 custom page here
    return false;
  }

}
