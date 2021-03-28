import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from "../../services/auth/authentication.service";
import {environment} from "../../../../../environments/environment";
import {UserData} from "../../model/user-data";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) {
  }

  /**
   * Adds current user's jwt to the headers of the outgoing request to backend api
   * @param request
   * @param next
   */
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const currentUser: UserData = this.authenticationService.currentUserValue;
    const isApiUrl: boolean = request.url.startsWith(environment.apiUrl_v1);

    if ((currentUser) && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.authenticationService.getJwtFromStorage()}`
        }
      });
    }
    return next.handle(request);
  }
}
