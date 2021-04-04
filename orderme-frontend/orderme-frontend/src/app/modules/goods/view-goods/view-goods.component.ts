import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {GoodsService} from "../../core/services/goods/goods.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Goods} from "../../core/model/goods";
import {ToastsService} from "../../core/services/util/toasts.service";
import {Observable, Subscription} from "rxjs";
import {CartService} from "../../core/services/orders/cart.service";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";
import {GoodsDto} from "../../core/model/dto/goodsDto";
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoodsType} from "../../core/model/goods-type";
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {finalize} from "rxjs/operators";
import {FirebaseService} from "../../core/services/util/firebase.service";

@Component({
  selector: 'app-view-goods',
  templateUrl: './view-goods.component.html',
  styleUrls: ['./view-goods.component.scss']
})
export class ViewGoodsComponent implements OnInit, OnDestroy {

  faSpinner = faSpinner;
  currentGoodsId: string;
  isLoading: boolean;
  //TODO!!!
  // @ts-ignore
  currentShopId: number;
  // @ts-ignore
  currentGoods: Goods;
  isAdministrator: boolean = false;
  private subscription: Subscription = new Subscription();

  goodsTypes: GoodsType[] = [];

  isEditModeEnabled: boolean = false;
  cachedGoodsState: Goods | undefined;

  currentAmountToAddToCart: number;

  isUploadingPhoto: boolean = false;

  // @ts-ignore
  uploadPercent: Observable<number | undefined>;
  // @ts-ignore
  filePath: string | undefined;

  // @ts-ignore
  goodsEditFormGroup: FormGroup = new FormGroup(
    {
      Title: new FormControl('', [
        Validators.required,
        Validators.minLength(3)
      ]),
      Description: new FormControl('', [
        Validators.minLength(4)
      ]),
      NewPrice: new FormControl('', [
        Validators.required,
        Validators.min(0.1),
        Validators.max(999999),
        Validators.pattern(/^\d+(\.\d{1,2})?$/)
      ]),
      Category: new FormControl(null, [
        Validators.required
      ])
    });

  constructor(private goodsService: GoodsService,
              private activatedRoute: ActivatedRoute,
              private toastsService: ToastsService,
              private router: Router,
              private cartService: CartService,
              private authenticationService: AuthenticationService,
              private goodsTypesService: GoodsTypeService,
              private firebaseService: FirebaseService) {
    this.currentGoodsId = this.activatedRoute.snapshot.params.id;
    this.isLoading = true;
    this.currentGoods = new Goods();
    //todo
    this.currentAmountToAddToCart = 1;
    if (authenticationService.isAuthenticated()) {
      this.isAdministrator =
        this.authenticationService.currentUserValue.userRole === UserRole.SUPER_ADMIN
        || this.authenticationService.currentUserValue.userRole === UserRole.ADMIN;
    }
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
          error => {
          console.error(error);
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

  deleteCurrentGoods() {
    this.subscription.add(
      this.goodsService.deleteGoods(this.currentGoods.goodsId).subscribe(
        () => {
          this.toastsService.toastAddSuccess('This goods was successfully deleted.');
          this.router.navigateByUrl('/goods');
        },error => {
          console.error(error);
          this.toastsService.toastAddDanger('Something went wrong during the deletion of current goods.')
        }
      ));
  }

  fetchGoodsTypesIfEmpty() {
    if (this.goodsTypes.length === 0) {
      this.subscription.add(
        this.goodsTypesService.getAllGoodsTypesList().subscribe(
          data => {
            this.goodsTypes = data;
            this.enrichFieldValuesToTheForm();
          }, error => {
            console.error(error);
            this.toastsService.toastAddDanger('Something went wrong while fetching the goods types list. Please, contact the administrator');
          }
        )
      )
    }
  }

  startEditing() {
    this.fetchGoodsTypesIfEmpty();
    this.enrichFieldValuesToTheForm();
    this.cachedGoodsState = new Goods();
    this.cachedGoodsState = Object.assign(this.cachedGoodsState, this.currentGoods)
    this.isEditModeEnabled = true;
  }

  cancelEditing() {
    this.currentGoods = Object.assign(this.currentGoods, this.cachedGoodsState);
    this.cachedGoodsState = undefined;
    this.isEditModeEnabled = false;
  }

  saveGoodsEditing() {
    this.isEditModeEnabled = true;
    let goodsDto: GoodsDto = new GoodsDto();
    goodsDto.title = this.goodsEditFormGroup.get('Title')?.value;
    goodsDto.description = this.goodsEditFormGroup.get('Description')?.value;
    goodsDto.actualPrice = this.goodsEditFormGroup.get('NewPrice')?.value;
    goodsDto.goodsTypeId = this.goodsEditFormGroup.get('Category')?.value.goodsTypeId;
    if (this.filePath) {
      goodsDto.imageLink = this.filePath;
    }
    this.subscription.add(this.goodsService.patchGoods(goodsDto, this.currentGoodsId).subscribe(
      data => {
        this.currentGoods = data;
        this.isEditModeEnabled = false;
        this.isLoading = false;
      },error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong while editing the goods');
      }
    ))
  }

  private enrichFieldValuesToTheForm() {
    this.goodsEditFormGroup.patchValue({
      Title: this.currentGoods.title,
      Description: this.currentGoods.description,
      NewPrice: this.currentGoods.actualPrice,
      Category: this.goodsTypes.find(value => value.goodsTypeId === this.currentGoods.goodsType?.goodsTypeId)
    });
  }

  uploadFile(event: any) {
    const file = event.target.files[0];
    const filePathToUpload = `goods/${this.currentGoodsId}`;
    this.filePath = undefined;
    this.isUploadingPhoto = true;
    const task = this.firebaseService.uploadFile(file, filePathToUpload);

    // observe percentage changes
    this.uploadPercent = task.percentageChanges();
    // get notified when the download URL is available
    task.snapshotChanges().pipe(
      finalize(() => {
        this.filePath = filePathToUpload
        this.currentGoods.imageLink = undefined;
        this.isUploadingPhoto = false;
      })
    ).subscribe()
  }
}
