import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Shop} from "../../model/shop";

@Injectable({
  providedIn: 'root'
})
export class ShopsService {

  private SHOPS_URL = `${environment.apiUrl_v1}shops`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  public getAllShops(): Observable<Shop[]> {
    return this.http.get<Shop[]>(this.SHOPS_URL, this.httpOptions);
  }

}
