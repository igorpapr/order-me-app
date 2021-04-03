import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {HandleErrorsService} from "../util/handle-errors.service";
import {Observable} from "rxjs";
import {GoodsType} from "../../model/goods-type";
import {catchError} from "rxjs/operators";
import {GoodsTypeDto} from "../../model/dto/goodsTypeDto";

@Injectable({
  providedIn: 'root'
})
export class GoodsTypeService {

  private GOODS_TYPES_URL = `${environment.apiUrl_v1}goodsTypes`

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private handleErrorsService: HandleErrorsService) {
    // @ts-ignore
  }

  /**
   * Get a list of goods types
   */
  public getAllGoodsTypesList(): Observable<GoodsType[]> {
    return this.http.get<GoodsType[]>(this.GOODS_TYPES_URL, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<GoodsType[]>('getAllGoodsTypesList', [])));
  }

  /**
   * Get a goods type by id
   */
  public getGoodsTypeById(goodsTypeId: number): Observable<GoodsType> {
    return this.http.get<GoodsType>(this.GOODS_TYPES_URL + '/' + goodsTypeId,
      this.httpOptions).pipe(
        catchError(this.handleErrorsService.handleError<GoodsType>('getGoodsTypeById')));
  }

  /**
   * Create goods type
   * @param goodsTypeToCreate - goodsTypeDto to send to the server
   */
  public createGoodsType(goodsTypeToCreate: GoodsTypeDto) {
    console.log('Trying to create goods: ' + goodsTypeToCreate);
    return this.http.post<GoodsType>(this.GOODS_TYPES_URL, goodsTypeToCreate, this.httpOptions);
  }

  /**
   * Patch goods type
   * @param goodsTypeToPatch - goodsTypeDto to send to the server
   * @param goodsTypeId - goods type id of which goods type to patch
   */
  public patchGoodsType(goodsTypeToPatch: GoodsTypeDto, goodsTypeId: string) {
    console.log('Trying to patch goods with id ' + goodsTypeId + '. GoodsTypeToPatch: ' + goodsTypeToPatch);
    return this.http.patch<GoodsType>(this.GOODS_TYPES_URL + '/' + goodsTypeId, goodsTypeToPatch, this.httpOptions)
      .pipe(catchError(this.handleErrorsService.handleError<any>('patchGoodsType')));
  }

}
