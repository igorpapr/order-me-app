import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {GoodsAvailabilityDto} from "../../model/dto/goodsAvailabilityDto";
import {GoodsAvailability} from "../../model/goods-availability";
import {AvailabilityStatus} from "../../model/availability-status";

@Injectable({
  providedIn: 'root'
})
export class GoodsAvailabilityService {

  private GOODS_AVAILABILITIES_URL = `${environment.apiUrl_v1}goodsAvailabilities`;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  public addGoodsAvailability(goodsAvailabilityDto:GoodsAvailabilityDto,
                              shopId: number,
                              goodsId: string) {
    const requestParams: HttpParams = new HttpParams()
      .set('shopId', String(shopId))
      .set('goodsId', String(goodsId));

    console.log(requestParams);
    console.log(shopId);
    console.log(goodsId);
    console.log(goodsAvailabilityDto);
    return this.http.post<GoodsAvailability>(this.GOODS_AVAILABILITIES_URL,
      goodsAvailabilityDto,
      {
        headers: this.httpOptions.headers,
        params: requestParams
      });
  }

  public getAvailabilityStatusKey(status: AvailabilityStatus): string{
    // @ts-ignore
    return Object.keys(AvailabilityStatus)
      // @ts-ignore
      .find(key => AvailabilityStatus[key] == status);
  }

// @PatchMapping
// public ResponseEntity<?> patchByIds(@RequestBody GoodsAvailabilityDto goodsAvailabilityDto,
// @RequestParam Integer shopId,
// @RequestParam UUID goodsId) {
//   return new ResponseEntity<>(goodsAvailabilityService.patchByShopIdAndGoodsId(goodsAvailabilityDto, shopId, goodsId),
//     HttpStatus.OK);
// }
//
// @DeleteMapping
// public ResponseEntity<?> deleteByIds(@RequestParam Integer shopId,
// @RequestParam UUID goodsId) {
//   goodsAvailabilityService.deleteByShopIdAndGoodsId(shopId, goodsId);
//   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
// }

}
