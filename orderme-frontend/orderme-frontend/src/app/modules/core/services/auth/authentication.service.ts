import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import jwt_decode from 'jwt-decode';
import {catchError, map} from "rxjs/operators";
import {UserData} from "../../model/user-data";
import {AuthenticationRequestDto} from "../../model/dto/authenticationRequestDto";
import {RegistrationRequestDto} from "../../model/dto/registrationRequestDto";
import {HandleErrorsService} from "../util/handle-errors.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public readonly NUMBER_OF_HASHING_ITERATIONS = 5;
  private readonly LOCAL_STORAGE_JWT_KEY = 'token';
  // private readonly LOCAL_STORAGE_USER_DATA_KEY = 'userData';
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
              private handleErrorsService: HandleErrorsService) {

    //TODO check correctness
    this.currentUserDataSubject = new BehaviorSubject<UserData>(
      // @ts-ignore
      localStorage.getItem(this.LOCAL_STORAGE_JWT_KEY)
        // @ts-ignore
        ? jwt_decode(localStorage.getItem(this.LOCAL_STORAGE_JWT_KEY))
        : undefined);
    this.currentUserData = this.currentUserDataSubject.asObservable();
  }

  public get currentUserValue(): UserData {
    return this.currentUserDataSubject.value;
  }

  public getJwtFromStorage(): string | null {
    return localStorage.getItem(this.LOCAL_STORAGE_JWT_KEY);

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
        localStorage.setItem(this.LOCAL_STORAGE_JWT_KEY, jsonResponse.jwt);
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
    console.log("outcoming request: " + JSON.stringify(registrationRequest));
    return this.http.post<any>(this.REGISTER_URL, registrationRequest, this.httpOptions);
      // .pipe(catchError(this.handleErrorsService.handleError<any>('registerUser')));
  }

  /**
   * Register admin with given data
   * @param registrationRequest - registration data to send to the server
   */
  public registerAdmin(registrationRequest: RegistrationRequestDto): Observable<any> {
    return this.http.post<any>(this.ADMIN_REGISTER_URL, registrationRequest, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('registerAdmin')));
  }

  /**
   * Logs out the current user
   * (also deletes jwt token from local storage)
   */
  public logOut(): void {
    localStorage.removeItem(this.LOCAL_STORAGE_JWT_KEY);
    // @ts-ignore
    this.currentUserDataSubject.next(null);
  }

  //TODO - maybe locale
  //TODO - change password
  //TODO - password hashing
}
