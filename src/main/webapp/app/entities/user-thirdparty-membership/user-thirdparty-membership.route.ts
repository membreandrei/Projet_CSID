import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { UserThirdpartyMembershipService } from './user-thirdparty-membership.service';
import { UserThirdpartyMembershipComponent } from './user-thirdparty-membership.component';
import { UserThirdpartyMembershipDetailComponent } from './user-thirdparty-membership-detail.component';
import { UserThirdpartyMembershipUpdateComponent } from './user-thirdparty-membership-update.component';
import { UserThirdpartyMembershipDeletePopupComponent } from './user-thirdparty-membership-delete-dialog.component';
import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

@Injectable({ providedIn: 'root' })
export class UserThirdpartyMembershipResolve implements Resolve<IUserThirdpartyMembership> {
    constructor(private service: UserThirdpartyMembershipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserThirdpartyMembership> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserThirdpartyMembership>) => response.ok),
                map((userThirdpartyMembership: HttpResponse<UserThirdpartyMembership>) => userThirdpartyMembership.body)
            );
        }
        return of(new UserThirdpartyMembership());
    }
}

export const userThirdpartyMembershipRoute: Routes = [
    {
        path: '',
        component: UserThirdpartyMembershipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userThirdpartyMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserThirdpartyMembershipDetailComponent,
        resolve: {
            userThirdpartyMembership: UserThirdpartyMembershipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userThirdpartyMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserThirdpartyMembershipUpdateComponent,
        resolve: {
            userThirdpartyMembership: UserThirdpartyMembershipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userThirdpartyMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserThirdpartyMembershipUpdateComponent,
        resolve: {
            userThirdpartyMembership: UserThirdpartyMembershipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userThirdpartyMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userThirdpartyMembershipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserThirdpartyMembershipDeletePopupComponent,
        resolve: {
            userThirdpartyMembership: UserThirdpartyMembershipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.userThirdpartyMembership.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
