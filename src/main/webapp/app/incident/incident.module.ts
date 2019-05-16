import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetCsidSharedModule } from 'app/shared';
import { IncidentComponent } from 'app/incident/incident.component';
import { INCIDENT_ROUTE } from './incident.route';
import { OrderModule } from 'ngx-order-pipe';

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forRoot([INCIDENT_ROUTE]), OrderModule],
    entryComponents: [],
    declarations: [IncidentComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IncidentModule {}
