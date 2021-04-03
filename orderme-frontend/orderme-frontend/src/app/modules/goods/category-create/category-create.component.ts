import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {Router} from "@angular/router";
import {GoodsTypeDto} from "../../core/model/dto/goodsTypeDto";
import {ToastsService} from "../../core/services/util/toasts.service";

@Component({
  selector: 'app-category-create',
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.scss']
})
export class CategoryCreateComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription();

  // @ts-ignore
  categoryCreationFormGroup: FormGroup =  new FormGroup({
    Title: new FormControl('', [
      Validators.required,
      Validators.minLength(3)
    ]),
    Description: new FormControl('', [
      Validators.minLength(4)
    ])
  });

  constructor(private goodsTypeService: GoodsTypeService,
              private toastsService: ToastsService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  saveNewCategory(): void {
    let goodsTypeDto: GoodsTypeDto = new GoodsTypeDto();
    goodsTypeDto.title = this.categoryCreationFormGroup.get('Title')?.value;
    goodsTypeDto.description = this.categoryCreationFormGroup.get('Description')?.value;
    this.subscription.add(
      this.goodsTypeService.createGoodsType(goodsTypeDto).subscribe(
        () => {
          this.toastsService.toastAddSuccess('The new category was successfully created.')
          this.router.navigateByUrl('/categories');
        }, error => {
          console.error(error)
          this.toastsService.toastAddDanger('Something went wrong during the new category creation')
        }
      )
    )
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
