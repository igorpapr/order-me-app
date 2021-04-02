import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Goods} from "../../core/model/goods";
import {GoodsService} from "../../core/services/goods/goods.service";
import {Router} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Page} from "../../core/model/page";

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

  currentPageNumber: number = 0;
  readonly pageSize: number = 9;

  //todo @INPUT as separate component
  // @ts-ignore
  currentShopId: number;

  constructor(private goodsService: GoodsService,
              private router: Router,
              private toastsService: ToastsService) { }

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
}
