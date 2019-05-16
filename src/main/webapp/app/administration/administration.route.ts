import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AdministrationComponent } from './administration.component';

export const ADMINISTRATION_ROUTE: Route = {
    path: 'portailAdministration',
    component: AdministrationComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'projetCsidApp.portailAdministration.home.title'
    },
    canActivate: [UserRouteAccessService]
};
