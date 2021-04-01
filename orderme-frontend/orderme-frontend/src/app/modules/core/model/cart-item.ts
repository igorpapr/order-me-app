import {Goods} from "./goods";

export class CartItem {

  goods: Goods;
  amount: number;

  constructor(goods: Goods, amount: number) {
    this.goods = goods;
    this.amount = amount;
  }
}
