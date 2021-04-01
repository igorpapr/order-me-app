import {Goods} from "./goods";

export class OrderLineIdKey {
  // @ts-ignore
  orderId: string;
  // @ts-ignore
  goodsId: string;
}

export class OrderLine {
  // @ts-ignore
  orderLineId: OrderLineIdKey;
  // @ts-ignore
  goods: Goods;
  // @ts-ignore
  amount: number;

}
