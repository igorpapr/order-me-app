import { Injectable } from '@angular/core';
import {OrderService} from "./order.service";
import {CartItem} from "../../model/cart-item";
import {Goods} from "../../model/goods";
import {BehaviorSubject, Observable} from "rxjs";
import {OrderDto} from "../../model/dto/orderDto";
import {AuthenticationService} from "../auth/authentication.service";
import {ToastsService} from "../util/toasts.service";
import {OrderLineDto} from "../../model/dto/order-line-dto";
import {Router} from "@angular/router";
import {LocalStorageService} from "../util/local-storage.service";
import {Order} from "../../model/order";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private currentCartListSubject: BehaviorSubject<CartItem[]>;
  public currentCartList$: Observable<CartItem[]>;

  constructor(private orderService: OrderService,
              private authenticationService: AuthenticationService,
              private toastsService: ToastsService,
              private router: Router,
              private localStorageService: LocalStorageService) {
    // @ts-ignore
    let dataFromStorage = JSON.parse(this.localStorageService.getCart());
    this.currentCartListSubject = new BehaviorSubject<CartItem[]>(
      // @ts-ignore
        // @ts-ignore
        dataFromStorage
        ? dataFromStorage
        : undefined);
    this.currentCartList$ = this.currentCartListSubject.asObservable();
  }

  public get currentCartList(): CartItem[] {
    return this.currentCartListSubject.value;
  }

  public addToCart(goods: Goods, amount: number): void {
    const cartItem = new CartItem(goods, amount);
    if (this.currentCartList){
      let items = this.currentCartList;
      let filtered = items.filter(item => goods.goodsId != item.goods.goodsId);
      filtered.push(cartItem);
      this.currentCartListSubject.next(filtered);
    } else {
      this.currentCartListSubject.next([cartItem]);
    }
    this.saveToLocalStorage();
  }

  public removeFromCart(goodsId: string): void {
    if (this.currentCartList) {
      this.currentCartListSubject.next(this.currentCartList.filter(item => goodsId != item.goods.goodsId))
      this.saveToLocalStorage();
    } else {
      //for development purposes only:
      console.error('Trying to remove the cart item from an empty cart!');
    }
  }

  public checkout(shopId: number): Observable<Order> | null {
    let orderDto: OrderDto = new OrderDto();
    orderDto.orderLines = [];
    orderDto.shopId = shopId;
    if (this.authenticationService.currentUserValue) {
      orderDto.createdById = this.authenticationService.currentUserValue.userId;
      for (let item of this.currentCartList) {
        let orderLine: OrderLineDto = new OrderLineDto();
        orderLine.amount = item.amount;
        orderLine.goodsId = item.goods.goodsId;
        orderDto.orderLines.push(orderLine);
      }
      return this.orderService.createOrder(orderDto);
    } else {
      this.toastsService.toastAddWarning("Please, sign in or create new account to process your order.");
      return null;
    }
  }

  private saveToLocalStorage(): void {
    this.localStorageService.setCart(JSON.stringify(this.currentCartList));
  }
}
