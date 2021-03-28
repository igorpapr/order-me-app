import {OrderStatus} from "./order-status";

export class Order {

  orderId: string | undefined;

  creationTime: Date | undefined;

  lastUpdateTime: Date | undefined;

  orderStatus: OrderStatus | undefined;

  createdBy: string | undefined;

  processingBy: string | null | undefined;

}
