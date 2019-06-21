import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Thirdparty } from 'app/shared/model/thirdparty.model';
import { UserAssignmentService } from './user-assignment.service';
import { UserAssignmentComponent } from './user-assignment.component';
import { IThirdparty } from 'app/shared/model/thirdparty.model';

@Injectable({ providedIn: 'root' })
export class ThirdpartyResolve implements Resolve<IThirdparty> {
    constructor(private service: UserAssignmentService) {}

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

export const UserAssignmentRoute: Routes = [
    {
        path: '',
        component: UserAssignmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetCsidApp.portailAdministration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
