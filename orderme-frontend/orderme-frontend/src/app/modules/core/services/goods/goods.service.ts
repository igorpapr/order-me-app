import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {HandleErrorsService} from "../util/handle-errors.service";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {Goods} from "../../model/goods";
import {Page} from "../../model/page";
import {GoodsDto} from "../../model/dto/goodsDto";
import {AvailabilityStatus} from "../../model/availability-status";

@Injectable({
  providedIn: 'root'
})
export class GoodsService {

  private GOODS_URL = `${environment.apiUrl_v1}goods`
  private GOODS_GOODTYPE_URL = this.GOODS_URL + '/goodsType';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService) {

  }

  /**
   * Get a page of goods by parameters
   * @param page - the page numbed starting from 0
   * @param size - the size of the page
   * @param shopId - (optional) - shopId
   */
  public getAllGoodsList(page: number,
                         size: number,
                         shopId: number): Observable<Page<Goods>> {
    const requestParams: HttpParams = new HttpParams()
      .set('page', String(page))
      .set('size', String(size));

    if (shopId !== undefined) {
      requestParams.set('shopId', String(shopId));
    }

    return this.http.get<Page<Goods>>(this.GOODS_URL, {
      headers: this.httpOptions.headers,
      params: requestParams});
  }

  /**
   * Get a page of goods by goods type id
   * @param page - the page numbed starting from 0
   * @param goodsTypeId - goods type id
   * @param size - the size of the page
   * @param shopId - (optional) - shopId
   */
  public getAllGoodsListByGoodsType(shopId: number,
                                   goodsTypeId: number,
                                   page: number,
                                   size: number): Observable<Page<Goods>> {
    const requestParams: HttpParams = new HttpParams()
      .set('page', String(page))
      .set('size', String(size));
      if (shopId !== undefined) {
        requestParams.set('shopId', String(shopId));
      }

    return this.http.get<Page<Goods>>(this.GOODS_GOODTYPE_URL + '/' + goodsTypeId,
      {
        headers: this.httpOptions.headers,
        params: requestParams
      })
      .pipe(catchError(this.handleErrorsService.handleError<Page<Goods>>('getAllGoodsListByGoodsType', undefined)));
  }

  /**
   * Get a single goods by goods id and (optional) shopId
   * @param goodsId - the goods id
   * @param shopId - (optional) - shopId
   */
  public getGoodsByIdAndShopId(shopId: number | null,
                               goodsId: string): Observable<Goods> {
    const requestParams: HttpParams = new HttpParams();
    if (shopId !== null) {
      requestParams.set('shopId', String(shopId));
    }

    return this.http.get<Goods>(this.GOODS_URL + '/' + goodsId,
      {
        headers: this.httpOptions.headers,
        params: requestParams
      })
      .pipe(catchError(this.handleErrorsService.handleError<Goods>('getGoodsByIdAndShopId', undefined)));
  }

  /**
   * Create goods
   * @param goodsToCreate - goodsDto to send to the server
   */
  public createGoods(goodsToCreate: GoodsDto) {
    console.log('Trying to create goods: ' + goodsToCreate);
    return this.http.post<Goods>(this.GOODS_URL, goodsToCreate, this.httpOptions);
  }

  /**
   * Patch goods
   * @param goodsToPatch - goodsDto to send to the server
   * @param goodsId - goods id of which goods to patch
   */
  public patchGoods(goodsToPatch: GoodsDto, goodsId: string) {
    console.log('Trying to patch goods with id ' + goodsId + '. GoodsToPatch: ' + JSON.stringify(goodsToPatch));
    return this.http.patch<Goods>(this.GOODS_URL + '/' + goodsId, goodsToPatch, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('patchGoods')));
  }

  /**
   * Delete goods
   * @param goodsId - goods id of which goods to delete
   */
  public deleteGoods(goodsId: string) {
    console.log('Trying to delete goods with id ' + goodsId);
    return this.http.delete<any>(this.GOODS_URL + '/' + goodsId, this.httpOptions);
  }

  public getGoodsAvailabilityByShop(goods: Goods, shopId: number) {
    if (goods.goodsAvailabilities) {
      for (let item of goods.goodsAvailabilities) {
        if (item.goodsAvailabilitiesId.shopId === shopId) {
          return item.availabilityStatus ? this.getAvailabilityStatus(item.availabilityStatus) : AvailabilityStatus.NOT_AVAILABLE;
        }
      }
    }
    return AvailabilityStatus.NOT_AVAILABLE;
  }

  public getAvailabilityStatus(status: AvailabilityStatus) {
    // @ts-ignore
    return AvailabilityStatus[status.toString()];
  }

}
