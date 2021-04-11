import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {Shop} from "../../model/shop";

@Injectable({
  providedIn: 'root'
})
export class ShopsService {

  private _currentShopSubject: BehaviorSubject<Shop>;
  public currentShop: Observable<Shop>;

  private SHOPS_URL = `${environment.apiUrl_v1}shops`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
    // @ts-ignore
    this._currentShopSubject = new BehaviorSubject<Shop>(undefined);
    this.currentShop = this._currentShopSubject.asObservable();
  }

  public get currentShopValue(): Shop {
    return this._currentShopSubject.value;
  }

  public set currentShopValue(value: Shop) {
    this._currentShopSubject.next(value);
  }

  public getAllShops(): Observable<Shop[]> {
    return this.http.get<Shop[]>(this.SHOPS_URL, this.httpOptions);
  }

}
