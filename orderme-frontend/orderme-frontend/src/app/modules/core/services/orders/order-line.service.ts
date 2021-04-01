import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {HandleErrorsService} from "../util/handle-errors.service";
import {catchError} from "rxjs/operators";
import {OrderLineDto} from "../../model/dto/order-line-dto";
import {OrderLine} from "../../model/order-line";

@Injectable({
  providedIn: 'root'
})
export class OrderLineService {

  private ORDER_LINES_URL = `${environment.apiUrl_v1}orderLines`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService) {
  }

  public patchOrderLine(orderId: string, goodsId: string, orderLineDto: OrderLineDto) {
    const requestParams: HttpParams = new HttpParams()
      .set('orderId', orderId)
      .set('goodsId', goodsId);

    console.log('Trying to patch Order Line with orderId ' + orderId + 'and goodsId: ' + goodsId);
    return this.http.patch<OrderLine>(this.ORDER_LINES_URL, orderLineDto, {
      headers: this.httpOptions.headers,
      params: requestParams})
      .pipe(catchError(this.handleErrorsService.handleError<any>('patchOrderLine')));
  }

  public deleteOrderLine(orderId: string, goodsId: string) {
    const requestParams: HttpParams = new HttpParams()
      .set('orderId', orderId)
      .set('goodsId', goodsId);

    return this.http.delete(this.ORDER_LINES_URL, {
      headers: this.httpOptions.headers,
      params: requestParams})
      .pipe(catchError(this.handleErrorsService.handleError<any>('deleteOrderLine')));
  }
}
