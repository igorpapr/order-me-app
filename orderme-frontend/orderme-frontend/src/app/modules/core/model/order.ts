import {OrderStatus} from "./order-status";
import {OrderLine} from "./order-line";
import {Shop} from "./shop";
import {User} from "./user";

export class Order {

  // @ts-ignore
  orderId: string;

  creationTime: Date | undefined;

  lastUpdateTime: Date | undefined;

  // @ts-ignore
  orderStatus: OrderStatus;

  // @ts-ignore
  createdBy: User;

  // @ts-ignore
  processingBy: User;

  // @ts-ignore
  orderLines: OrderLine[];

  // @ts-ignore
  fullPrice: number;

  // @ts-ignore
  shop: Shop;

}
