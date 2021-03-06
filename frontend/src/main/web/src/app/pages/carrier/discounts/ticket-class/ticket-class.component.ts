import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TicketClass} from "../shared/model/ticket-class.model";
import {Trip} from "../shared/model/trip.model";

@Component({
  selector: 'ticket-class',
  templateUrl: './ticket-class.component.html',
  styleUrls: ['./ticket-class.component.scss']
})
export class TicketClassComponent implements OnInit {

  @Input() trip: Trip;

  @Output() onAddTicketClassEmitter = new EventEmitter<TicketClass>();

  @Output() onDeleteTicketClassEmitter = new EventEmitter<TicketClass>();

  isDiscountFormActivated = false;

  currentTicketClass: TicketClass;

  constructor() {
  }

  ngOnInit() {
  }

  openTicketClassDiscountForm(ticketClass) {
    this.currentTicketClass = ticketClass;
    this.isDiscountFormActivated = true;
  }

  closeDiscountForm() {
    this.currentTicketClass = null;
    this.isDiscountFormActivated = false;
  }

  emitToMainComponentTicketClassOnDeleteEvent($event) {
    this.onDeleteTicketClassEmitter.emit($event);
  }

  emitToMainComponentTicketClassOnAddEvent($event) {
    this.onAddTicketClassEmitter.emit($event);
  }
}
