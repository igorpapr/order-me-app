import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import jwt_decode from 'jwt-decode';
import {map} from "rxjs/operators";
import {UserData} from "../../model/user-data";
import {AuthenticationRequestDto} from "../../model/dto/authenticationRequestDto";
import {RegistrationRequestDto} from "../../model/dto/registrationRequestDto";
import {HandleErrorsService} from "../util/handle-errors.service";
import {LocalStorageService} from "../util/local-storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public readonly NUMBER_OF_HASHING_ITERATIONS = 5;
  private currentUserDataSubject: BehaviorSubject<UserData>;
  public currentUserData: Observable<UserData>;

  private readonly REGISTER_URL = `${environment.apiUrl_v1}auth/register`;
  private readonly AUTHENTICATE_URL = `${environment.apiUrl_v1}auth/authenticate`;
  private readonly ADMIN_REGISTER_URL = `${environment.apiUrl_v1}auth/register/admin`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      observe: 'response'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService,
              private localStorageService: LocalStorageService) {

    this.currentUserDataSubject = new BehaviorSubject<UserData>(
      // @ts-ignore
      this.localStorageService.getJwt()
        // @ts-ignore
        ? jwt_decode(this.localStorageService.getJwt())
        : undefined);
    this.currentUserData = this.currentUserDataSubject.asObservable();
  }

  public get currentUserValue(): UserData {
    return this.currentUserDataSubject.value;
  }

  public isAuthenticated(): boolean {
    return !!this.currentUserDataSubject.value;
  }

  /**
   * Authenticating user
   * @returns a UserData observable
   * @param authRequest
   */
  public authenticateUser(authRequest: AuthenticationRequestDto): Observable<UserData> {
    // const authenticationRequest = {
    //   email,
    //   password: password//this.passwordHashing(password, this.PASSWORD_HASHING_ITERATIONS_AMOUNT)
    // }; TODO - password hashing
    return this.http.post<UserData>(
        this.AUTHENTICATE_URL,
        authRequest,
        this.httpOptions)
      .pipe(map(data => {
        const jsonResponse: any = data;
        this.localStorageService.setJwt(jsonResponse.jwt);
        // @ts-ignore
        const decodedUserData: UserData = jwt_decode(jsonResponse.jwt);
        this.currentUserDataSubject.next(decodedUserData);
        //this.localeService.initUserLang(this.settingsService.getLanguage());
        return decodedUserData;
      })
    );
  }

  /**
   * Register user with given data
   * @param registrationRequest - registration data to send to the server
   */
  public registerUser(registrationRequest: RegistrationRequestDto): Observable<any> {
    // const userInfo = {
    //   username,
    //   password: this.passwordHashing(password, this.PASSWORD_HASHING_ITERATIONS_AMOUNT),
    //   email,
    //   language: this.localeService.getLanguage()
    // };JSON.stringify(userInfo)
    //TODO - password hashing
    return this.http.post<any>(this.REGISTER_URL, registrationRequest, this.httpOptions);
  }

  /**
   * Register admin with given data
   * @param registrationRequest - registration data to send to the server
   */
  public registerAdmin(registrationRequest: RegistrationRequestDto): Observable<any> {
    return this.http.post<any>(this.ADMIN_REGISTER_URL, registrationRequest, this.httpOptions)
  }

  /**
   * Logs out the current user
   * (also deletes jwt token and cart data from local storage)
   */
  public logOut(): void {
    this.localStorageService.removeJwt();
    this.localStorageService.clearCart();
    // @ts-ignore
    this.currentUserDataSubject.next(null);
  }

  //TODO - password hashing
}
