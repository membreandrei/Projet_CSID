import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Thirdparty } from 'app/shared/model/thirdparty.model';
import { MembershipService } from './member-ship.service';
import { MemberShipComponent } from './member-ship.component';
import { MemberShipDetailComponent } from './member-ship-detail.component';
import { MemberShipUpdateComponent } from './member-ship-update.component';
import { MembershipDeletePopupComponent } from './member-ship-delete-dialog.component';
import { IThirdparty } from 'app/shared/model/thirdparty.model';

@Injectable({ providedIn: 'root' })
export class ThirdpartyResolve implements Resolve<IThirdparty> {
    constructor(private service: MembershipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IThirdparty> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Thirdparty>) => response.ok),
                map((thirdparty: HttpResponse<Thirdparty>) => thirdparty.body)
            );
        }
        return of(new Thirdparty());
    }
}

export const MembershipRoute: Routes = [
    {
        path: '',
        component: MemberShipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MemberShipDetailComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MemberShipUpdateComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MemberShipUpdateComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const MembershipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MembershipDeletePopupComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
