import { Injectable } from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {HandleErrorsService} from "../util/handle-errors.service";
import {Observable} from "rxjs";
import {Order} from "../../model/order";
import {catchError} from "rxjs/operators";
import {OrderStatus} from "../../model/order-status";
import {OrderDto} from "../../model/dto/orderDto";
import {Page} from "../../model/page";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private ORDERS_URL = `${environment.apiUrl_v1}orders`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService) {
  }

  /**
   * Getting the list of orders
   * @param page
   * @param size
   * @param shopId
   * @param createdBy
   * @param processingBy
   * @param status
   */
  public getOrdersList(page: number,
                       size: number,
                       shopId: number,
                       createdBy: string | null,
                       processingBy: string | null,
                       status: OrderStatus | null): Observable<Page<Order>> {

    const requestParams: HttpParams = new HttpParams()

    if (createdBy !== null) {
      requestParams.set('createdBy', createdBy);
    }
    if (processingBy !== null) {
      requestParams.set('processingBy', processingBy);
    }
    if (status !== null) {
      requestParams.set('status', String(status))
    }
    if (status !== null) {
      requestParams.set('shopId', String(shopId))
    }
    return this.http.get<Page<Order>>(this.ORDERS_URL, {
        headers: this.httpOptions.headers,
        params: requestParams})
      .pipe(catchError(this.handleErrorsService.handleError<Page<Order>>('getOrdersList')));
  }

  public getOrderById(orderId: string): Observable<Order> {
    return this.http.get<Order>(this.ORDERS_URL + '/' + orderId, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('getOrderById')));
  }

  public createOrder(orderDto: OrderDto) {
    console.log('Trying to create order: ' + orderDto);
    return this.http.post<Order>(this.ORDERS_URL, orderDto, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('createOrder')));
  }

  public patchOrder(orderDto: OrderDto, orderId: string) {
    console.log('Trying to patchOrder with id ' + orderId + '. OrderToPatch: ' + orderDto);
    return this.http.patch<Order>(this.ORDERS_URL + '/' + orderId, orderDto, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('patchOrder')));
  }
}
