import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { UserIncidentAssigmentService } from './user-incident-assigment.service';
import { UserIncidentAssigmentComponent } from './user-incident-assigment.component';
import { UserIncidentAssigmentDetailComponent } from './user-incident-assigment-detail.component';
import { UserIncidentAssigmentUpdateComponent } from './user-incident-assigment-update.component';
import { UserIncidentAssigmentDeletePopupComponent } from './user-incident-assigment-delete-dialog.component';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

@Injectable({ providedIn: 'root' })
export class UserIncidentAssigmentResolve implements Resolve<IUserIncidentAssigment> {
    constructor(private service: UserIncidentAssigmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserIncidentAssigment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserIncidentAssigment>) => response.ok),
                map((userIncidentAssigment: HttpResponse<UserIncidentAssigment>) => userIncidentAssigment.body)
            );
        }
        return of(new UserIncidentAssigment());
    }
}

export const userIncidentAssigmentRoute: Routes = [
    {
        path: '',
        component: UserIncidentAssigmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userIncidentAssigment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserIncidentAssigmentDetailComponent,
        resolve: {
            userIncidentAssigment: UserIncidentAssigmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userIncidentAssigment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserIncidentAssigmentUpdateComponent,
        resolve: {
            userIncidentAssigment: UserIncidentAssigmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userIncidentAssigment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserIncidentAssigmentUpdateComponent,
        resolve: {
            userIncidentAssigment: UserIncidentAssigmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userIncidentAssigment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userIncidentAssigmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserIncidentAssigmentDeletePopupComponent,
        resolve: {
            userIncidentAssigment: UserIncidentAssigmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userIncidentAssigment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
