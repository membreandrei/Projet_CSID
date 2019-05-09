import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { IncidentComponent } from './incident.component';

export const INCIDENT_ROUTE: Route = {
    path: 'ticket',
    component: IncidentComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'projetCsidApp.incident.home.title'
    },
    canActivate: [UserRouteAccessService]
};
