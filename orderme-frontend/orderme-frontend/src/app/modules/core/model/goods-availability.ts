import {AvailabilityStatus} from "./availability-status";

export class GoodsAvailabilityIdKey {
  // @ts-ignore
  goodsId: string;
  // @ts-ignore
  shopId: number;
}

export class GoodsAvailability {

  // @ts-ignore
  goodsAvailabilitiesId: GoodsAvailabilityIdKey;

  // @ts-ignore
  availabilityStatus: AvailabilityStatus

}
