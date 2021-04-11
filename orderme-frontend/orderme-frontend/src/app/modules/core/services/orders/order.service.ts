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
   * @param unprocessedOnly - this parameter is used when it's needed
   *                               to fetch the unprocessed orders only.
   *                               Important!: If the processingBy parameter is provided,
   *                               this parameter is ignored.
   *                               Default value = false
   */
  public getOrdersList(page: number,
                       size: number,
                       shopId: number | null,
                       createdBy: any,
                       processingBy: any,
                       status: OrderStatus | null | undefined,
                       unprocessedOnly: boolean = false): Observable<Page<Order>> {

    let requestParams: HttpParams = new HttpParams()
      .append('page', String(page))
      .append('size', String(size))
      .append('sort', "creationTime,desc");

    console.log(`Fetching orders: page: ${page}, size: ${size}, createdBy: ${createdBy}, processingBy: ${processingBy}, status: ${status}, shopId: ${shopId}, unprocessedOnly: ${unprocessedOnly}`)

    if (createdBy !== null) {
      requestParams = requestParams.append('createdBy', String(createdBy));
    }
    if (processingBy !== null) {
      requestParams = requestParams.append('processingBy', String(processingBy));
    }
    if (status !== undefined && status !== null) {
      requestParams = requestParams.append('status', String(status))
    }
    if (shopId) {
      requestParams = requestParams.append('shopId', String(shopId))
    }
    requestParams = requestParams.append('unprocessedOnly', String(unprocessedOnly))

    return this.http.get<Page<Order>>(this.ORDERS_URL, {
        headers: this.httpOptions.headers,
        params: requestParams})
      .pipe(catchError(this.handleErrorsService.handleError<Page<Order>>('getOrdersList')));
  }

  public getOrderById(orderId: string): Observable<Order> {
    return this.http.get<Order>(this.ORDERS_URL + '/' + orderId, this.httpOptions);
      // .pipe(catchError(this.handleErrorsService.handleError<any>('getOrderById')));
  }

  public createOrder(orderDto: OrderDto) {
    console.log('Trying to create order: ' + JSON.stringify(orderDto));
    return this.http.post<Order>(this.ORDERS_URL, orderDto, this.httpOptions);
 //     .pipe(catchError(this.handleErrorsService.handleError<any>('createOrder')));
  }

  public patchOrder(orderDto: OrderDto, orderId: string) {
    console.log('Trying to patchOrder with id ' + orderId + '. OrderToPatch: ' + JSON.stringify(orderDto));
    return this.http.patch<Order>(this.ORDERS_URL + '/' + orderId, orderDto, this.httpOptions);
  }

  public getOrderStatusKey(status: OrderStatus): string{
    // @ts-ignore
    return Object.keys(OrderStatus)
      // @ts-ignore
      .find(key => OrderStatus[key] == status);
  }
}
