import {Component, OnInit, TemplateRef} from '@angular/core';
import {ToastsService} from "../../core/services/util/toasts.service";

@Component({
  selector: 'app-toasts',
  templateUrl: './toasts.component.html',
  styleUrls: ['./toasts.component.scss']
})
export class ToastsComponent implements OnInit {


  constructor(public toastsService: ToastsService) {
  }

  ngOnInit(): void {
  }

  isTemplate(toast: any) {
    return toast.textOrTpl instanceof TemplateRef;
  }

}
