import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoodsService} from "../../core/services/goods/goods.service";
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {Router} from "@angular/router";
import {GoodsDto} from "../../core/model/dto/goodsDto";
import {ToastsService} from "../../core/services/util/toasts.service";
import {GoodsType} from "../../core/model/goods-type";

@Component({
  selector: 'app-goods-create',
  templateUrl: './goods-create.component.html',
  styleUrls: ['./goods-create.component.scss']
})
export class GoodsCreateComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription();
  goodsTypes: GoodsType[] = [];

  // @ts-ignore
  chosenGoodsTypeId: number;

  goodsCreationFormGroup: FormGroup =  new FormGroup({
    Title: new FormControl('', [
      Validators.required,
      Validators.minLength(3)
    ]),
    Description: new FormControl('', [
      Validators.minLength(4)
    ]),
    Price: new FormControl('', [
      Validators.required,
      Validators.min(0.1),
      Validators.max(999999),
      Validators.pattern(/^\d+(\.\d{1,2})?$/)
    ]),
    Category: new FormControl({}, [
      Validators.required
    ])
  });

  constructor(private goodsService: GoodsService,
              private goodsTypeService: GoodsTypeService,
              private router: Router,
              private toastsService: ToastsService) {
  }

  ngOnInit(): void {
    this.fetchGoodsTypes();
  }

  saveNewGoods(): void {
    let goodsDto: GoodsDto = new GoodsDto();
    goodsDto.title = this.goodsCreationFormGroup.get('Title')?.value;
    goodsDto.description = this.goodsCreationFormGroup.get('Description')?.value;
    goodsDto.actualPrice = this.goodsCreationFormGroup.get('Price')?.value;
    goodsDto.oldPrice = goodsDto.actualPrice;
    goodsDto.goodsTypeId = this.goodsCreationFormGroup.get('Category')?.value.goodsTypeId;

    this.subscription.add(
      this.goodsService.createGoods(goodsDto).subscribe(
        data => {
          this.toastsService.toastAddSuccess('The new goods was successfully created.')
          this.router.navigateByUrl(`/goods/${data.goodsId}`);
        }, error => {
          console.error(error)
          this.toastsService.toastAddDanger('Something went wrong during the new goods creation')
        }
      )
    )
  }

  fetchGoodsTypes(): void {
    this.subscription.add(this.goodsTypeService.getAllGoodsTypesList().subscribe(
      data => {
        this.goodsTypes = data;
      }, error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong during the categories fetching from the server')
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
