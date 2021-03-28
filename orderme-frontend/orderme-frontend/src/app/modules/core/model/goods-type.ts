export class GoodsType {

  goodsTypeId: string;

  title: string;

  description: string;

  constructor(goodsTypeId: string, title: string, description: string) {
    this.goodsTypeId = goodsTypeId;
    this.title = title;
    this.description = description;
  }
}
