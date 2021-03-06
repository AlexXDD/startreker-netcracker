import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {GrowlModule, MessageService, ProgressBarModule, CalendarModule, TabViewModule} from "primeng/primeng";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {CarrierRoutingModule} from './carrier-routing.module';
import {NavbarComponent} from './navbar/navbar.component';
import {IndexComponent} from './index/index.component';
import {TripsComponent} from './trips/trips.component';
import {ServicesComponent} from './services/services.component';
import {SuggestionsComponent} from './suggestions/suggestions.component';
import {DashboardsComponent} from './dashboard/dashboards/dashboards.component';
import {CarrierComponent} from './carrier.component';
import {SalesComponent} from './dashboard/sales/sales.component';
import {ViewsComponent} from './dashboard/views/views.component';
import {ServiceTableComponent} from './services/service-table/service-table.component';
import {ServiceCrudComponent} from './services/service-crud/service-crud.component';
import {ClarificationComponent} from './services/clarification/clarification.component';
import {ArchiveComponent} from './services/archive/archive.component';
import {ServiceFilterPipe} from './services/shared/pipe/service-filter.pipe';
import {NgxPaginationModule} from 'ngx-pagination';
import {DiscountFormComponent} from "./discounts/discount-form/discount-form.component";
import {DiscountMainPageComponent} from "./discounts/discount-main-page/discount-main-page.component";
import {CarrierDiscountsService} from "./discounts/shared/service/carrier-discount.service";
import {SuggestionComponent} from "./discounts/suggestion/suggestion.component";
import {TicketClassComponent} from "./discounts/ticket-class/ticket-class.component";
import {ToastModule} from "primeng/toast";
import {TripsTableComponent} from './trips/trips-table/trips-table.component';
import {TripsCrudComponent} from './trips/trips-crud/trips-crud.component';
import {TripFilterPipe} from './trips/shared/pipes/filter-pipe.pipe';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {TreeModule} from 'primeng/tree';
import { SuggestionsTreeComponent } from './suggestions/suggestions-tree/suggestions-tree.component';
import { SuggestionsTableComponent } from './suggestions/suggestions-table/suggestions-table.component';
import { TripsTicketClassComponent } from './trips/trips-ticket-class/trips-ticket-class.component';
import {DialogModule} from 'primeng/dialog';
import { PossibleServicesComponent } from './possible-services/possible-services.component';
import { PossibleServicesListComponent } from './possible-services/possible-services-list/possible-services-list.component';
import { PossibleServicesFormComponent } from './possible-services/possible-services-form/possible-services-form.component';
import { TripTransfer } from './trip-transfer';
import { SharedModule } from '../../shared/shared.module';
import {ShowMessageService} from "../admin/approver/shared/service/show-message.service";

@NgModule({
            declarations:
              [NavbarComponent,
               IndexComponent,
               TripsComponent,
               ServicesComponent,
               SuggestionsComponent,
               DashboardsComponent,
               CarrierComponent,
               SalesComponent,
               ViewsComponent,
               ServiceTableComponent,
               ServiceCrudComponent,
               ClarificationComponent,
               ArchiveComponent,
               ServiceFilterPipe,
               DiscountFormComponent,
               DiscountMainPageComponent,
               SuggestionComponent,
               TicketClassComponent,
               TripsTableComponent,
               TripsCrudComponent,
               TripFilterPipe,
               SuggestionsTreeComponent,
               SuggestionsTableComponent,
               TripsTicketClassComponent,
               PossibleServicesComponent,
               PossibleServicesListComponent,
               PossibleServicesFormComponent
              ],
            imports: [
              CommonModule,
              FormsModule,
              ReactiveFormsModule,
              CarrierRoutingModule,
              GrowlModule,
              NgxPaginationModule,
              ToastModule,
              MessageModule,
              MessagesModule,
              TreeModule,
              DialogModule,
              TabViewModule,
              SharedModule,
              CalendarModule
            ],
            providers: [
              MessageService,
              CarrierDiscountsService,
              TripTransfer,
              ShowMessageService,
              DatePipe,
            ]
          })
export class CarrierModule {
}
