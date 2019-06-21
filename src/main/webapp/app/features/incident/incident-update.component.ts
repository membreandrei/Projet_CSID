import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';
import { AccountService } from 'app/core';
import { formatDate } from '@angular/common';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

@Component({
    selector: 'jhi-incident-update',
    templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
    incident: IIncident;
    isSaving: boolean;
    userapps: IUserApp[];
    userAppsConnected: any;
    currentAccount: any;
    accountId: any;

    constructor(
        protected accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        protected incidentService: IncidentService,
        protected userAppService: UserAppService,
        protected userIncidentAssigmentService: UserIncidentAssigmentService,
        protected activatedRoute: ActivatedRoute
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
                    this.userapps = res;
                    this.userAppsConnected = this.userapps[0];
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
        });

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
            this.loadAll();
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.incident.id !== undefined) {
            this.subscribeToSaveResponse(this.incidentService.update(this.incident));
        } else {
            this.incident.userApp = this.userAppsConnected;
            this.incident.dateDebut = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
            this.subscribeToSaveResponse(this.incidentService.create(this.incident));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe((res: HttpResponse<IIncident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserAppById(index: number, item: IUserApp) {
        return item.id;
    }
}
