import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {HandleErrorsService} from "../util/handle-errors.service";
import {catchError} from "rxjs/operators";
import {UserDto} from "../../model/dto/userDto";
import {User} from "../../model/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private USERS_URL = `${environment.apiUrl_v1}users`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService) {
  }

  /**
   * Get user by id
   * @param userId - user id to find
   */
  public getUserById(userId: string): Observable<User> {
    return this.http.get<User>(this.USERS_URL + '/' + userId, this.httpOptions);
  }

  /**
   * Patch user
   * @param userDto - userDto to send to the server
   * @param userId - userId identifying the user
   */
  public patchUser(userDto: UserDto, userId: string) {
    console.log('Trying to patch user with id ' + userId + '. UserDto: ' + userDto);
    return this.http.patch<User>(this.USERS_URL + '/' + userId, userDto, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('patchUser')));
  }
}
