import {GoodsType} from "./goods-type";
import {GoodsAvailability} from "./goods-availability";

export class Goods {
  goodsId: string | undefined;

  title: string | undefined;

  description: string | undefined;

  oldPrice: number | undefined;

  actualPrice: number | undefined;

  imageLink: string | null | undefined;

  goodsType: GoodsType | null | undefined;

  goodsAvailabilities: Set<GoodsAvailability> | undefined;
}
