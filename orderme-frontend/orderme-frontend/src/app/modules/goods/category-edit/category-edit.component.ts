import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastsService} from "../../core/services/util/toasts.service";
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {GoodsType} from "../../core/model/goods-type";
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoodsTypeDto} from "../../core/model/dto/goodsTypeDto";
import {FirebaseService} from "../../core/services/util/firebase.service";
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.scss']
})
export class CategoryEditComponent implements OnInit, OnDestroy {

  subscription: Subscription = new Subscription();
  // @ts-ignore
  currentGoodsType: GoodsType;
  currentGoodsTypeId: number;
  isLoading: boolean = true;
  isEditModeEnabled: boolean = false;
  cachedGoodsType: GoodsType | undefined;
  faSpinner = faSpinner;


  isUploadingPhoto: boolean = false;
  // @ts-ignore
  uploadPercent: Observable<number | undefined>;
  // @ts-ignore
  filePath: string | undefined;

  goodsTypeEditForm: FormGroup =  new FormGroup({
    Title: new FormControl('', [
      Validators.required,
      Validators.minLength(3)
    ]),
    Description: new FormControl('', [
      Validators.minLength(4)
    ])
  });

  constructor(private toastsService: ToastsService,
              private goodsTypeService: GoodsTypeService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private firebaseService: FirebaseService) {
    this.currentGoodsTypeId = activatedRoute.snapshot.params.id;
  }

  ngOnInit(): void {
    this.fetchCurrentGoodsType();
  }

  private fetchCurrentGoodsType() {
    this.isLoading = true;
    this.subscription.add(
      this.goodsTypeService.getGoodsTypeById(this.currentGoodsTypeId).subscribe(
        data => {
          this.isLoading = false;
          this.currentGoodsType = data;
        }, error => {
          console.error(error);
          this.toastsService.toastAddDanger('Something went wrong while fetching the data. Please, contact the administrator')
        }
      )
    )
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  saveGoodsTypeEditing() {
    this.isLoading = true;
    let goodsTypeDto = new GoodsTypeDto();
    goodsTypeDto.title = this.goodsTypeEditForm.get('Title')?.value;
    goodsTypeDto.description = this.goodsTypeEditForm.get('Description')?.value;
    if (this.filePath) {
      goodsTypeDto.imageLink = this.filePath;
    }
    this.subscription.add(this.goodsTypeService.patchGoodsType(goodsTypeDto, this.currentGoodsTypeId).subscribe(
      data => {
        this.isLoading = false;
        this.currentGoodsType = data;
        this.toastsService.toastAddSuccess('The category was successfully edited');
        this.isEditModeEnabled = false;
        window.location.reload();
      }, error => {
        console.error(error);
        this.toastsService.toastAddDanger('Something went wrong during category updating. Please, contact the administrator');
      }
    ))
  }

  cancelEditing() {
    this.currentGoodsType = Object.assign(this.currentGoodsType, this.cachedGoodsType);
    this.isEditModeEnabled = false;
    this.cachedGoodsType = undefined;
  }

  startEditing() {
    this.isEditModeEnabled = true;
    this.cachedGoodsType = new GoodsType();
    this.cachedGoodsType = Object.assign(this.cachedGoodsType, this.currentGoodsType);
  }

  deleteCurrentGoodsType() {
    this.subscription.add(this.goodsTypeService.deleteGoodsType(this.currentGoodsTypeId).subscribe(
      () => {
        this.isLoading = true;
        this.toastsService.toastAddSuccess('The category was successfully deleted');
        this.router.navigateByUrl('categories');
        }, error => {
        console.error(error);
        this.isLoading = false;
        this.toastsService.toastAddDanger('Something went wrong while deleting the category. Please, contact the administrator');
      }
    ))
  }

  uploadFile(event: any) {
    const file = event.target.files[0];
    this.filePath = undefined;
    this.isUploadingPhoto = true;
    const filePathToUpload = `categories/${this.currentGoodsTypeId}`;

    let uploadTask = this.firebaseService
      .uploadFile(file, filePathToUpload);

    this.uploadPercent = uploadTask.percentageChanges();
    uploadTask.snapshotChanges().pipe(
      finalize(() => {
        this.filePath = filePathToUpload
        this.currentGoodsType.imageLink = undefined;
        this.isUploadingPhoto = false;
      })
    ).subscribe()
  }
}
