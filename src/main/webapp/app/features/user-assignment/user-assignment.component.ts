import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { AccountService } from 'app/core';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { IUserApp } from 'app/shared/model/user-app.model';
import { IIncident } from 'app/shared/model/incident.model';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment';
import { UserAppService } from 'app/entities/user-app';
import { IncidentService } from 'app/entities/incident';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-user-assignment',
    templateUrl: './user-assignment.component.html',
    styles: []
})
export class UserAssignmentComponent implements OnInit {
    userIncidentAssigments: IUserIncidentAssigment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    private accountId: any;
    private userAppId: number;

    constructor(
        protected userAppService: UserAppService,
        protected userIncidentAssigmentService: UserIncidentAssigmentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.userIncidentAssigmentService
            .query()
            .pipe(
                filter((res: HttpResponse<IUserIncidentAssigment[]>) => res.ok),
                map((res: HttpResponse<IUserIncidentAssigment[]>) => res.body)
            )
            .subscribe(
                (res: IUserIncidentAssigment[]) => {
                    this.userIncidentAssigments = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    getUserAppId() {
        this.userAppService
            .query()
            .pipe(
                filter((res: HttpResponse<IUserApp[]>) => res.ok),
                map((res: HttpResponse<IUserApp[]>) => res.body)
            )
            .subscribe(
                (res: IUserApp[]) => {
                    const userApps = res;
                    for (const userApp of userApps) {
                        if (userApp.user.id === this.accountId) {
                            this.userAppId = userApp.id;
                        }
                    }
                    console.log('userrapp++++++++++++++++++++++++++ : ' + this.userAppId);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
        });
        this.registerChangeInUserIncidentAssigments();
    }

    trackId(index: number, item: IUserIncidentAssigment) {
        return item.id;
    }

    registerChangeInUserIncidentAssigments() {
        this.eventSubscriber = this.eventManager.subscribe('userIncidentAssigmentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
