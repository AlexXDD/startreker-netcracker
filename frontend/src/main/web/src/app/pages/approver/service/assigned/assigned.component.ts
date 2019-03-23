import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { clone } from 'ramda';
import { HttpResponse } from '@angular/common/http';

import {Service} from '../shared/model/service';
import { ServiceService } from '../shared/service/service.service';
import { checkToken } from '../../../../modules/api';

@Component({
  selector: 'app-assigned',
  templateUrl: './assigned.component.html',
  styleUrls: ['./assigned.component.scss']
})
export class AssignedComponent implements OnInit {

  readonly pageNumber: number = 10;

  pageFrom: number;

  services: Service[];

  form: FormGroup;

  currentServiceForReply: Service;

  loadingService: Service;

  setFormInDefault() {
    this.form = new FormGroup(
      {
        reply_text: new FormControl('', [Validators.required]),
      }
    );
  }

  onWriteReply(serviceForReply) {
    this.currentServiceForReply = serviceForReply;
  }

  resetReply() {
    this.currentServiceForReply = null;
  }

  resetLoading() {
    this.loadingService = null;
  }

  onPublish(service) {
    service.service_status = "PUBLISHED";

    this.loadingService = service;

    this.serviceService.updateServiceReview(service)
    .subscribe((resp: HttpResponse<any>) => {
      checkToken(resp.headers);
      this.getServices();
      alert(service.service_name + ' is now published');
    }, () => {
      this.resetLoading();
      alert('Something went wrong');
    });
  }

  onReview(service) {
    service.service_status = "UNDER_CLARIFICATION";
    service.reply_text = this.form.value.reply_text;

    this.loadingService = service;

    this.serviceService.updateServiceReview(service)
    .subscribe((resp: HttpResponse<any>) => {
      checkToken(resp.headers);
      this.getServices();
      alert(service.service_name + ' is now under clarification');
    }, () => {
      this.resetLoading();
      alert('Something went wrong');
    });
  }

  getServices() {
    this.serviceService.getServicesForApprover(this.pageFrom, this.pageNumber, "ASSIGNED")
    .subscribe((resp: HttpResponse<any>) => {
      checkToken(resp.headers);
      this.resetLoading();
      this.services = resp.body.map(item => {
        return new Service(
            item.id,
            item.approver_name,
            item.service_name,
            item.service_descr,
            item.service_status,
            new Date(item.creation_date),
            item.reply_text
        );
      });
    });
  }

  constructor(private serviceService: ServiceService) { }

  ngOnInit() {
    this.pageFrom = 0;
    this.setFormInDefault();
    this.getServices();
  }

  onPageUpdate(from: number) {
    this.pageFrom = from;
    this.getServices();
  }

}
