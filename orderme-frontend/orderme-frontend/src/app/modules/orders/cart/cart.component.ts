import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {CartService} from "../../core/services/orders/cart.service";
import {Observable, Subscription} from "rxjs";
import {CartItem} from "../../core/model/cart-item";
import {ToastsService} from "../../core/services/util/toasts.service";
import {LocalStorageService} from "../../core/services/util/local-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, OnDestroy {

  subscription: Subscription = new Subscription();
  cartListObservable: Observable<CartItem[]>;
  isEmpty: boolean = true;
  shopId: number;

  constructor(private authenticationService: AuthenticationService,
              private cartService: CartService,
              private toastsService: ToastsService,
              private localStorageService: LocalStorageService,
              private router: Router) {
    this.cartListObservable = cartService.currentCartList$;
    //todo
    this.shopId = 4;
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

    this.cartService.checkout(this.shopId)?.subscribe(
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
  }
}
