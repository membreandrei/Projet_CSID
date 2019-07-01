import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Thirdparty } from 'app/shared/model/thirdparty.model';
import { ThirdpartyService } from './thirdparty.service';
import { ThirdpartyComponent } from './thirdparty.component';
import { ThirdpartyDetailComponent } from './thirdparty-detail.component';
import { ThirdpartyUpdateComponent } from './thirdparty-update.component';
import { ThirdpartyDeletePopupComponent } from './thirdparty-delete-dialog.component';
import { IThirdparty } from 'app/shared/model/thirdparty.model';

@Injectable({ providedIn: 'root' })
export class ThirdpartyResolve implements Resolve<IThirdparty> {
    constructor(private service: ThirdpartyService) {}

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

export const thirdpartyRoute: Routes = [
    {
        path: '',
        component: ThirdpartyComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'projetCsidApp.thirdparty.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ThirdpartyDetailComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'projetCsidApp.thirdparty.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ThirdpartyUpdateComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'projetCsidApp.thirdparty.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ThirdpartyUpdateComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'projetCsidApp.thirdparty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const thirdpartyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ThirdpartyDeletePopupComponent,
        resolve: {
            thirdparty: ThirdpartyResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'projetCsidApp.thirdparty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
