import {Component, OnDestroy, OnInit} from '@angular/core';
import {GoodsAvailabilityService} from "../../core/services/goods/goods-availability.service";
import {ActivatedRoute} from "@angular/router";
import {GoodsService} from "../../core/services/goods/goods.service";
import {Subscription} from "rxjs";
import {Goods} from "../../core/model/goods";
import {ToastsService} from "../../core/services/util/toasts.service";
import {ShopsService} from "../../core/services/shops/shops.service";
import {Shop} from "../../core/model/shop";
import {GoodsAvailability} from "../../core/model/goods-availability";
import {AvailabilityStatus} from "../../core/model/availability-status";
import {GoodsAvailabilityDto} from "../../core/model/dto/goodsAvailabilityDto";

@Component({
  selector: 'app-goods-availability-configuration',
  templateUrl: './goods-availability-configuration.component.html',
  styleUrls: ['./goods-availability-configuration.component.scss']
})
export class GoodsAvailabilityConfigurationComponent implements OnInit, OnDestroy {

  currentGoodsId: string;
  // @ts-ignore
  currentGoods: Goods;
  subscription: Subscription = new Subscription();
  // @ts-ignore
  shops: Shop[];
  // @ts-ignore
  selectedShop: Shop | undefined;
  // @ts-ignore
  selectedAvailability: GoodsAvailability | null;
  statuses: AvailabilityStatus[];
  // @ts-ignore
  selectedStatus: AvailabilityStatus | undefined;

  constructor(private goodsAvailabilityService: GoodsAvailabilityService,
              private goodsService: GoodsService,
              private activatedRoute: ActivatedRoute,
              private toastsService: ToastsService,
              private shopsService: ShopsService) {
    this.currentGoodsId = activatedRoute.snapshot.params.id;
    this.statuses = [AvailabilityStatus.AVAILABLE, AvailabilityStatus.NOT_AVAILABLE, AvailabilityStatus.LAST_ONES]
  }

  ngOnInit(): void {
    this.fetchCurrentGoods();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private fetchCurrentGoods() {
    this.subscription.add(
      this.goodsService.getGoodsByIdAndShopId(null, this.currentGoodsId).subscribe(
        data => {
          this.currentGoods = data;
        }, error => {
          console.error(error);
          this.toastsService.toastAddDanger('Something went wrong during fetching the goods from the server. Please, contact administrator')
        }, () => {
          this.fetchShops();
        }
      ));
  }

  private fetchShops() {
    this.subscription.add(
      this.shopsService.getAllShops().subscribe(
        data => {
          this.shops = data;
        }, error => {
          console.error(error);
          this.shops = [];
          this.toastsService.toastAddDanger('Something went wrong while fetching the shops list.')
        }
      )
    )
  }

  setAvailabilitiesDataBySelectedShop() {
    if (this.currentGoods.goodsAvailabilities && this.selectedShop) {
      for (let item of this.currentGoods.goodsAvailabilities) {
        if (item.goodsAvailabilitiesId.shopId === this.selectedShop.shopId) {
          this.selectedAvailability = item;
          return;
        }
      }
    }
    this.selectedAvailability = null;
  }

  saveAvailability() {
    if (!(this.selectedShop && this.selectedStatus)) {
      this.toastsService.toastAddDanger('Please, select the shop and new status')
    } else {
      const dto: GoodsAvailabilityDto = new GoodsAvailabilityDto(this.goodsAvailabilityService.getAvailabilityStatusKey(this.selectedStatus));
      this.subscription.add(
        this.goodsAvailabilityService.addGoodsAvailability(dto, this.selectedShop.shopId, this.currentGoodsId).subscribe(
          () => {
            this.toastsService.toastAddSuccess('The goods availability was successfully saved');
            window.location.reload();
            }, error => {
            console.error(error);
            this.toastsService.toastAddDanger('Something went wrong during saving the goods availability');
          }
        )
      )
    }
  }

  getAvailabilityValue(availabilityStatus: AvailabilityStatus): string {
    return this.goodsService.getAvailabilityStatus(availabilityStatus);
  }
}
