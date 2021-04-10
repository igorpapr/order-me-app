import {GoodsType} from "./goods-type";
import {GoodsAvailability} from "./goods-availability";

export class Goods {
  // @ts-ignore
  goodsId: string;

  title: string | undefined;

  description: string | undefined;

  oldPrice: number | undefined;

  // @ts-ignore
  actualPrice: number;

  imageLink: string | null | undefined;

  goodsType: GoodsType | null | undefined;

  // @ts-ignore
  goodsAvailabilities: Set<GoodsAvailability>;
}
