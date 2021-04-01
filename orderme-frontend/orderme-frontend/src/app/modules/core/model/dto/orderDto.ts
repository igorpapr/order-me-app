import {OrderLineDto} from "./order-line-dto";

export class OrderDto {

  orderId: string | undefined

  // @ts-ignore
  orderStatus: string;

  createdById: string | undefined

  processingById: string | undefined

  // @ts-ignore
  orderLines: OrderLineDto[];

  shopId: number | undefined

}
