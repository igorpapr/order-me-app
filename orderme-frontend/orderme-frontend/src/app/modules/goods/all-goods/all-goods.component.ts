import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {Goods} from "../../core/model/goods";
import {GoodsService} from "../../core/services/goods/goods.service";
import {Router} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Page} from "../../core/model/page";
import {WindowService} from "../../core/services/util/window.service";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";
import {ShopsService} from "../../core/services/shops/shops.service";
import {Shop} from "../../core/model/shop";

@Component({
  selector: 'app-all-goods',
  templateUrl: './all-goods.component.html',
  styleUrls: ['./all-goods.component.scss']
})
export class AllGoodsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription = new Subscription();

  goodsTypeSet: Goods[] = [];
  isLoading: boolean = false;
  isEmpty: boolean = false;

  // @ts-ignore
  paginationObject: Page<Goods>;

  currentPageNumber: number = 1;
  readonly pageSize: number = 8;

  currentShop: Observable<Shop>;
  isAdministrator: boolean = false;
  readonly noImagePath: string = './assets/img/no-image.jpg';

  constructor(private goodsService: GoodsService,
              private router: Router,
              private toastsService: ToastsService,
              private windowService: WindowService,
              private authenticationService: AuthenticationService,
              private shopsService: ShopsService) {
    if (authenticationService.isAuthenticated()) {
      this.isAdministrator = (authenticationService.currentUserValue.userRole === UserRole.ADMIN
        || authenticationService.currentUserValue.userRole === UserRole.SUPER_ADMIN);
    }
    this.currentShop = shopsService.currentShop;
  }

  ngOnInit(): void {
    this.fetchGoods();
  }
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  private fetchGoods() {
    this.isLoading = true;
    this.goodsService.getAllGoodsList(this.currentPageNumber - 1, this.pageSize, this.shopsService.currentShopValue?.shopId)
      .subscribe(
        data => {
            this.paginationObject = data;
            this.isLoading = false;
            this.isEmpty = data?.content.length === 0;
        }, error => {
          console.error(error);
          this.toastsService.toastAddDanger('Something went wrong during fetching goods from the server. Please, contact the administrators');
          this.isLoading = false;
        }
      );
  }

  changePage(event: number) {
    this.currentPageNumber = event;
    this.fetchGoods();
    this.windowService.scrollToTop();
  }

  getGoodsAvailability(goods: Goods) {
    return this.goodsService.getGoodsAvailabilityByShop(goods, this.shopsService.currentShopValue.shopId);
  }

}
