import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {CartService} from "../../core/services/orders/cart.service";
import {Observable, Subscription} from "rxjs";
import {CartItem} from "../../core/model/cart-item";
import {ToastsService} from "../../core/services/util/toasts.service";
import {LocalStorageService} from "../../core/services/util/local-storage.service";
import {Router} from "@angular/router";
import {ShopsService} from "../../core/services/shops/shops.service";
import {Shop} from "../../core/model/shop";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, OnDestroy {

  subscription: Subscription = new Subscription();
  cartListObservable: Observable<CartItem[]>;
  isEmpty: boolean = true;
  currentShop: Observable<Shop>

  constructor(private authenticationService: AuthenticationService,
              private cartService: CartService,
              private toastsService: ToastsService,
              private localStorageService: LocalStorageService,
              private router: Router,
              private shopsService: ShopsService) {
    this.cartListObservable = cartService.currentCartList$;
    this.currentShop = shopsService.currentShop;
  }

  ngOnInit(): void {
    this.subscription.add(
      this.cartService.currentCartList$.subscribe(v => {
        if (v) {
          this.isEmpty = (v.length === 0);
        } else {
          this.isEmpty = true;
        }
    }));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  removeItem(goodsId: string) {
    this.cartService.removeFromCart(goodsId);
  }

  checkout($event: MouseEvent) {
    $event.preventDefault();
    if (this.shopsService.currentShopValue) {
      this.cartService.checkout(this.shopsService.currentShopValue?.shopId)?.subscribe(
        data => {
          this.toastsService.toastAddSuccess("Your order has been successfully created.");
          this.localStorageService.clearCart();
          this.router.navigateByUrl('/orders/' + data.orderId);
        },
        error => {
          console.error(error);
          this.toastsService.toastAddDanger("Something went wrong during your order processing. " +
            "Please, contact the administrator");
        });
    } else {
      this.toastsService.toastAddWarning('Please, choose the shop to checkout your order!')
    }
  }
}
