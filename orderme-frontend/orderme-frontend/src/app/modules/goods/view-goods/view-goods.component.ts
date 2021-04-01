import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {GoodsService} from "../../core/services/goods/goods.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Goods} from "../../core/model/goods";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Subscription} from "rxjs";
import {CartService} from "../../core/services/orders/cart.service";

@Component({
  selector: 'app-view-goods',
  templateUrl: './view-goods.component.html',
  styleUrls: ['./view-goods.component.scss']
})
export class ViewGoodsComponent implements OnInit, OnDestroy {

  currentGoodsId: string;
  isLoading: boolean;
  //TODO!!!
  // @ts-ignore
  currentShopId: number;
  // @ts-ignore
  currentGoods: Goods;
  private subscription: Subscription = new Subscription();

  currentAmountToAddToCart: number;

  constructor(private goodsService: GoodsService,
              private activatedRoute: ActivatedRoute,
              private toastsService: ToastsService,
              private router: Router,
              private cartService: CartService) {
    this.currentGoodsId = this.activatedRoute.snapshot.params.id;
    this.isLoading = true;
    this.currentGoods = new Goods();
    //todo
    this.currentAmountToAddToCart = 1;
  }

  ngOnInit(): void {
    this.fetchGoodsData();
  }

  fetchGoodsData(): void {
    this.isLoading = true;
    this.subscription.add(
      this.goodsService.getGoodsByIdAndShopId(this.currentShopId, this.currentGoodsId)
        .subscribe(data => {
          this.currentGoods = data;
        },
          () => {
            this.toastsService.toastAddDanger("Something went wrong while fetching goods data. Please, contact the administrator.");
            this.router.navigateByUrl('/');
          })
    );
    this.isLoading = false;
  }

  addToCart(event: Event, toastTemplate: TemplateRef<any>) {
    event.preventDefault();
    this.cartService.addToCart(this.currentGoods, this.currentAmountToAddToCart);
    this.toastsService.toastAddSuccess(toastTemplate);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
