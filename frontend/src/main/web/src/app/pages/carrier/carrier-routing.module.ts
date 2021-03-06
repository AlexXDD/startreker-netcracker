import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CarrierComponent} from './carrier.component';
import {IndexComponent} from './index/index.component';
import {TripsComponent} from './trips/trips.component';
import {ServicesComponent} from './services/services.component';
import {DashboardsComponent} from './dashboard/dashboards/dashboards.component';
import {SalesComponent} from './dashboard/sales/sales.component';
import {ViewsComponent} from './dashboard/views/views.component';
import {ServiceCrudComponent} from './services/service-crud/service-crud.component';
import {ServiceTableComponent} from './services/service-table/service-table.component';
import {ClarificationComponent} from './services/clarification/clarification.component';
import {ArchiveComponent} from './services/archive/archive.component';
import {DiscountMainPageComponent} from "./discounts/discount-main-page/discount-main-page.component";
import { PossibleServicesComponent } from './possible-services/possible-services.component';

const routes: Routes = [
  {
    path: '',
    component: CarrierComponent,
    children: [
      {
        path: '',
        redirectTo: 'dashboards',
      },
      {
        path: 'trips',
        component: TripsComponent
      },
      {
        path: 'services',
        component: ServicesComponent,
        children: [
          {
            path: 'new-service',
            component: ServiceCrudComponent
          },
          {
            path: 'approved-services',
            component: ServiceTableComponent
          },
          {
            path: 'clarification',
            component: ClarificationComponent
          },
          {
            path: 'archive',
            component: ArchiveComponent
          }
        ]
      },
      {
        path: 'possible-services/:tripId',
        component: PossibleServicesComponent},
      {
        path: 'discounts',
        component: DiscountMainPageComponent
      },
      {
        path: 'dashboards',
        component: DashboardsComponent,
        children: [
          {
            path: '',
            redirectTo: 'sales',
          },
          {
            path: 'sales',
            component: SalesComponent
          },
          {
            path: 'views',
            component: ViewsComponent
          }
        ]
      },
    ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarrierRoutingModule {
}
