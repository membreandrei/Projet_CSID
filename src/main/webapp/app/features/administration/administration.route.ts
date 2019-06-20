import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserApp } from 'app/shared/model/user-app.model';
import { AdministrationService } from './administration.service';
import { AdministrationComponent } from './administration.component';
import { AdministrationDetailComponent } from './administration-detail.component';
import { AdministrationUpdateComponent } from './administration-update.component';
import { AdministrationDeleteDialogComponent, AdministrationDeletePopupComponent } from './administration-delete-dialog.component';
import { IUserApp } from 'app/shared/model/user-app.model';

@Injectable({ providedIn: 'root' })
export class UserAppResolve implements Resolve<IUserApp> {
    constructor(private service: AdministrationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserApp> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserApp>) => response.ok),
                map((userApp: HttpResponse<UserApp>) => userApp.body)
            );
        }
        return of(new UserApp());
    }
}

export const AdministrationAppRoute: Routes = [
    {
        path: '',
        component: AdministrationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdministrationDetailComponent,
        resolve: {
            userApp: UserAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdministrationUpdateComponent,
        resolve: {
            userApp: UserAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdministrationUpdateComponent,
        resolve: {
            userApp: UserAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const AdministrationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdministrationDeletePopupComponent,
        resolve: {
            userApp: UserAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
