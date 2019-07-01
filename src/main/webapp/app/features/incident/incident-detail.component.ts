import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncident } from 'app/shared/model/incident.model';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { UserAssignmentService } from 'app/features/user-assignment/user-assignment.service';
import { formatDate } from '@angular/common';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';
import { AccountService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-incident-detail',
    templateUrl: './incident-detail.component.html'
})
export class IncidentDetailComponent implements OnInit {
    incident: IIncident;
    isSaving: boolean;
    accountId: any;
    currentAccount: any;
    userAppsConnected: any;
    userIncidentAssigmentTab: IUserIncidentAssigment[];
    userIncidentAssigment: IUserIncidentAssigment;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        protected UserAssignmentService: UserAssignmentService,
        protected userAppService: UserAppService
    ) {}

    loadAll() {
        this.userAppService
            .query({
                'userId.equals': this.accountId
            })
            .pipe(
                filter((res: HttpResponse<IUserApp[]>) => res.ok),
                map((res: HttpResponse<IUserApp[]>) => res.body)
            )
            .subscribe(
                (res: IUserApp[]) => {
                    this.userAppsConnected = res[0];
                    console.log(this.userAppsConnected);
                    console.log(this.accountId);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.UserAssignmentService.query({ 'incidentId.equals': this.incident.id })
            .pipe(
                filter((res: HttpResponse<IUserIncidentAssigment[]>) => res.ok),
                map((res: HttpResponse<IUserIncidentAssigment[]>) => res.body)
            )
            .subscribe(
                (res: IUserIncidentAssigment[]) => {
                    this.userIncidentAssigmentTab = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
            this.userIncidentAssigment = {
                id: undefined,
                dateDebut: undefined,
                dateFin: undefined,
                commentaire: undefined,
                status: undefined,
                incident: undefined,
                userApp: undefined
            };
        });

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
            this.loadAll();
        });
    }

    save() {
        this.isSaving = true;
        this.userIncidentAssigment.incident = this.incident;
        if (this.userIncidentAssigment.id !== undefined) {
            this.subscribeToSaveResponse(this.UserAssignmentService.update(this.userIncidentAssigment));
        } else {
            this.userIncidentAssigment.userApp = this.userAppsConnected;
            this.userIncidentAssigment.dateDebut = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
            this.userIncidentAssigmentTab.push(this.userIncidentAssigment);
            this.subscribeToSaveResponse(this.UserAssignmentService.create(this.userIncidentAssigment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserIncidentAssigment>>) {
        this.userIncidentAssigment = {
            id: undefined,
            dateDebut: undefined,
            dateFin: undefined,
            commentaire: undefined,
            status: undefined,
            incident: undefined,
            userApp: undefined
        };
        result.subscribe(
            (res: HttpResponse<IUserIncidentAssigment>) => {
                this.onSaveSuccess();
                console.log(res);
            },
            (res: HttpErrorResponse) => {
                this.onSaveError();
                console.log(res);
            }
        );
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }

    previousState() {
        window.history.back();
    }
}
