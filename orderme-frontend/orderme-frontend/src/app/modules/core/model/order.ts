import {OrderStatus} from "./order-status";
import {OrderLine} from "./order-line";
import {Shop} from "./shop";

export class Order {

  orderId: string | undefined;

  creationTime: Date | undefined;

  lastUpdateTime: Date | undefined;

  // @ts-ignore
  orderStatus: OrderStatus;

  createdBy: string | undefined;

  processingBy: string | null | undefined;

  // @ts-ignore
  orderLines: OrderLine[];

  // @ts-ignore
  fullPrice: number;

  // @ts-ignore
  shop: Shop;

}
