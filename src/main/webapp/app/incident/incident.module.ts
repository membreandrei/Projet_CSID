import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetCsidSharedModule } from 'app/shared';
import { IncidentComponent } from 'app/incident/incident.component';
import { IncidentUpdateComponent } from './incident-update.component';
import { INCIDENT_ROUTE } from './incident.route';

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forRoot([INCIDENT_ROUTE])],
    entryComponents: [],
    declarations: [IncidentComponent, IncidentUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IncidentModule {}
