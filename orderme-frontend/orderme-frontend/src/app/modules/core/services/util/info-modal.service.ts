import { Injectable } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {InfoModalComponent} from "../../../shared/info-modal/info-modal.component";

@Injectable({
  providedIn: 'root'
})
export class InfoModalService {

  constructor(private modalService: NgbModal) {
  }

  show(title: string, body: string) {
    const modalRef = this.modalService.open(InfoModalComponent,
      {centered: true});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.body = body;
  }
}
