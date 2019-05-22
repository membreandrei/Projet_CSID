import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { MemberShipComponent } from './member-ship.component';

export const ADMINISTRATION_ROUTE: Route = {
    path: 'portailAdministration/memberShip',
    component: MemberShipComponent,
    data: {
        authorities: ['ROLE_USER_ADMIN'],
        pageTitle: 'projetCsidApp.portailAdministration.home.title'
    },
    canActivate: [UserRouteAccessService]
};
