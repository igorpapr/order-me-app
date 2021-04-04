import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Goods} from "../../core/model/goods";
import {GoodsService} from "../../core/services/goods/goods.service";
import {Router} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Page} from "../../core/model/page";
import {WindowService} from "../../core/services/util/window.service";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";

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
  readonly pageSize: number = 9;

  //todo @INPUT as separate component
  // @ts-ignore
  currentShopId: number;
  isAdministrator: boolean = false;
  readonly noImagePath: string = './assets/img/no-image.jpg';

  constructor(private goodsService: GoodsService,
              private router: Router,
              private toastsService: ToastsService,
              private windowService: WindowService,
              private authenticationService: AuthenticationService) {
    if (authenticationService.isAuthenticated()) {
      this.isAdministrator = (authenticationService.currentUserValue.userRole === UserRole.ADMIN
        || authenticationService.currentUserValue.userRole === UserRole.SUPER_ADMIN);
    }
  }

  ngOnInit(): void {
    this.fetchGoods();
  }
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  private fetchGoods() {
    this.isLoading = true;
    //todo maybe add filtration by goods type also - than change service method call dependently
    this.goodsService.getAllGoodsList(this.currentPageNumber - 1, this.pageSize, this.currentShopId)
      .subscribe(
        data => {
            this.paginationObject = data;
            this.isLoading = false;
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


}
