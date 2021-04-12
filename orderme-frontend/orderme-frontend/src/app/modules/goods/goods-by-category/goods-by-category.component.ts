import {Component, OnDestroy, OnInit} from '@angular/core';
import {Goods} from "../../core/model/goods";
import {GoodsService} from "../../core/services/goods/goods.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {Page} from "../../core/model/page";
import {Observable, Subscription} from "rxjs";
import {ShopsService} from "../../core/services/shops/shops.service";
import {Shop} from "../../core/model/shop";
import {UserRole} from "../../core/model/userRole";
import {AuthenticationService} from "../../core/services/auth/authentication.service";

@Component({
  selector: 'app-goods-by-category',
  templateUrl: './goods-by-category.component.html',
  styleUrls: ['./goods-by-category.component.scss']
})
export class GoodsByCategoryComponent implements OnInit, OnDestroy {

  readonly pageSize: number = 8;
  goodsList: Goods[] = [];
  currentGoodsTypeId: number;

  subscription: Subscription = new Subscription();
  isLoading: boolean;
  isEmpty: boolean;
  faSpinner = faSpinner;
  currentPage: number;
  currentShop: Observable<Shop>;
  public state = '';
  // @ts-ignore
  paginationObject: Page<Goods>;
  readonly noImagePath: string = './assets/img/no-image.jpg';
  isAdministrator: boolean = false;

  constructor(private goodsService: GoodsService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              public toastsService: ToastsService,
              private goodsTypeService: GoodsTypeService,
              private shopsService: ShopsService,
              private authenticationService: AuthenticationService) {
    this.isLoading = false;
    this.isEmpty = false;
    this.currentGoodsTypeId = this.activatedRoute.snapshot.params.id;
    this.currentShop = shopsService.currentShop;
    this.currentPage = 1;
    if (authenticationService.isAuthenticated()) {
      this.isAdministrator = (authenticationService.currentUserValue.userRole === UserRole.ADMIN
        || authenticationService.currentUserValue.userRole === UserRole.SUPER_ADMIN);
    }
  }

  ngOnInit(): void {
    this.state = window.history.state.categoryTitle;
    if (this.state === undefined) {
      this.fetchGoodsType(this.currentGoodsTypeId);
    }
    this.fetchGoodsByCurrentGoodsTypeId();
  }

  private fetchGoodsByCurrentGoodsTypeId() {
    this.isLoading = true;
    this.subscription.add(this.goodsService.getAllGoodsListByGoodsType(this.shopsService.currentShopValue?.shopId,
      this.currentGoodsTypeId, this.currentPage - 1, this.pageSize)
      .subscribe(data => {
        this.paginationObject = data;
        if (data.content?.length===0) {
          this.isEmpty = true;
        }
        this.goodsList = data.content;
        this.isLoading = false;
      }, error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong while fetching the goods list. Please, contact the administrator');
      }));
  }

  private fetchGoodsType(currentGoodsTypeId: number) {
    this.goodsTypeService.getGoodsTypeById(currentGoodsTypeId)
      .subscribe(data => {
        this.state = data.title;
      },
        error => {
        console.error(error);
        this.toastsService.toastAddDanger("Something went wrong while fetching the goods category title. " +
          "Please, contact the administrator");
        });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  changePage(event: number) {
    this.currentPage = event;
    this.fetchGoodsByCurrentGoodsTypeId();
    this.scrollToTop();
  }

  scrollToTop() {
    const scrollToTop = window.setInterval(() => {
      const pos = window.pageYOffset;
      if (pos > 0) {
        window.scrollTo(0, pos - 40);
      } else {
        window.clearInterval(scrollToTop);
      }
    }, 16);
  }

  getGoodsAvailability(item: Goods) {
    return this.goodsService.getGoodsAvailabilityByShop(item, this.shopsService.currentShopValue.shopId);
  }
}
