import { Component, OnInit } from '@angular/core';
import {GoodsService} from "../../core/services/goods/goods.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Goods} from "../../core/model/goods";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-view-goods',
  templateUrl: './view-goods.component.html',
  styleUrls: ['./view-goods.component.scss']
})
export class ViewGoodsComponent implements OnInit {

  currentGoodsId: string;
  isLoading: boolean;
  //TODO!!!
  // @ts-ignore
  currentShopId: number;
  // @ts-ignore
  currentGoods: Goods;

  constructor(private goodsService: GoodsService,
              private activatedRoute: ActivatedRoute,
              private toastsService: ToastsService,
              private router : Router) {
    this.currentGoodsId = this.activatedRoute.snapshot.params.id;
    this.isLoading = true;
    this.currentGoods = new Goods();
  }

  ngOnInit(): void {
    this.fetchGoodsData();
  }

  fetchGoodsData(): void {
    this.isLoading = true;
    this.goodsService.getGoodsByIdAndShopId(this.currentShopId, this.currentGoodsId)
      .subscribe(data => {
        this.currentGoods = data;
      },
        () => {
          this.toastsService.toastAddDanger("Something went wrong while fetching goods data. Please, contact the administrator.");
          this.router.navigateByUrl('/');
        })
    this.isLoading = false;
  }

}
