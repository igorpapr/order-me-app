import {OrderStatus} from "../order-status";
import {OrderLine} from "../order-line";

export class OrderDto {

  orderId: string | undefined

  orderStatus: OrderStatus | undefined

  createdById: string | undefined

  processingById: string | undefined

  orderLines: Set<OrderLine> | undefined

  shopId: number | undefined

}
