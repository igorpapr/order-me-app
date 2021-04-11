import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Shop} from "../../core/model/shop";
import {ShopsService} from "../../core/services/shops/shops.service";
import {ToastsService} from "../../core/services/util/toasts.service";

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.scss']
})
export class ShopsComponent implements OnInit, OnDestroy {

  subscription: Subscription = new Subscription();

  // @ts-ignore
  shopsList: Shop[];
  // @ts-ignore
  selectedShop: Shop;

  constructor(private shopsService: ShopsService,
              private toastsService: ToastsService) {
  }

  ngOnInit(): void {
    this.fetchShopsList();
  }

  private fetchShopsList() {
    this.subscription.add(
      this.shopsService.getAllShops().subscribe(
        data => {
          this.shopsList = data;
        }, error => {
          console.error(error);
          this.shopsList = [];
          this.toastsService.toastAddDanger('Something went wrong while fetching the shops list')
        }
      )
    )
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setSelectedShop() {
    this.shopsService.currentShopValue = this.selectedShop;
  }
}
