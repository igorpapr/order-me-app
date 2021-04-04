import {Component, OnDestroy, OnInit} from '@angular/core';
import {GoodsType} from "../../core/model/goods-type";
import {Subscription} from "rxjs";
import {GoodsTypeService} from "../../core/services/goods/goods-type.service";
import {Router} from "@angular/router";
import {ToastsService} from "../../core/services/util/toasts.service";
import {AuthenticationService} from "../../core/services/auth/authentication.service";
import {UserRole} from "../../core/model/userRole";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit, OnDestroy {

  subscriptions: Subscription = new Subscription();

  goodsTypeSet: GoodsType[] = [];
  isLoading: boolean = false;
  isEmpty: boolean = false;
  isAdministrator: boolean = false;
  readonly noImagePath: string = './assets/img/no-image.jpg';

  constructor(private goodsTypeService: GoodsTypeService,
              private router: Router,
              private toastsService: ToastsService,
              private authenticationService: AuthenticationService) {
    if (authenticationService.isAuthenticated()){
      this.isAdministrator = (authenticationService.currentUserValue.userRole === UserRole.ADMIN
        || authenticationService.currentUserValue.userRole === UserRole.SUPER_ADMIN);
    }
  }

  ngOnInit(): void {
    this.fetchGoodsTypes();
  }

  fetchGoodsTypes(): void {
    this.isLoading = true;
    this.subscriptions.add(
      this.goodsTypeService.getAllGoodsTypesList()
        .subscribe(
          data => {
            this.goodsTypeSet = data;
            if (this.goodsTypeSet.length === 0) {
              this.isEmpty = true;
            }
          },
          error => {
            console.error(error);
            this.toastsService.toastAddDanger("Something went wrong during categories fetching. " +
              "Please, contact the administrator");
          }
        )
    );
    this.isLoading = false;
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  navigate(event: Event, goodsTypeId: string, title: string) {
    event.preventDefault();

    this.router.navigateByUrl(`/categories/${goodsTypeId}`, {state: { categoryTitle: title}});
  }
}
