import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
import { UserAssignmentService } from 'app/features/user-assignment/user-assignment.service';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

@Component({
    selector: 'jhi-incident-update',
    templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
    index: number = 0;
    incident: IIncident;
    idIncident: any;
    isSavingIncident: boolean;
    isSaving: boolean;
    userapps: IUserApp[];
    userAppsConnected: any;
    currentAccount: any;
    accountId: any;
    userIncidentAssigment: IUserIncidentAssigment;
    userIncidentAssigmentTab: IUserIncidentAssigment[];
    show: boolean = true;

    constructor(
        protected accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        protected incidentService: IncidentService,
        protected userAppService: UserAppService,
        protected UserAssignmentService: UserAssignmentService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
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

        if (this.show === false) {
            this.UserAssignmentService.query({ 'incidentId.equals': this.incident.id })
                .pipe(
                    filter((res: HttpResponse<IUserIncidentAssigment[]>) => res.ok),
                    map((res: HttpResponse<IUserIncidentAssigment[]>) => res.body)
                )
                .subscribe(
                    (res: IUserIncidentAssigment[]) => {
                        this.userIncidentAssigmentTab = res;
                        console.log(this.userIncidentAssigmentTab);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }
    ngOnInit() {
        this.isSavingIncident = false;
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
            if (incident.id !== undefined) {
                this.show = false;
            }

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

    previousState() {
        this.router.navigate(['ticket']);
    }

    saveIncident() {
        this.isSaving = true;
        this.idIncident = this.incident.id;
        if (this.incident.id !== undefined) {
            this.subscribeToSaveResponseIncidentUpdate(this.incidentService.update(this.incident));
        } else {
            this.incident.userApp = this.userAppsConnected;
            this.incident.dateDebut = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
            this.subscribeToSaveResponseIncident(this.incidentService.create(this.incident));
        }
    }

    save() {
        this.isSaving = true;
        this.userIncidentAssigment.incident = this.incident;
        if (this.userIncidentAssigment.id !== undefined) {
            this.subscribeToSaveResponse(this.UserAssignmentService.update(this.userIncidentAssigment));
        } else {
            this.userIncidentAssigment.dateDebut = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
            this.userIncidentAssigmentTab.push(this.userIncidentAssigment);
            this.subscribeToSaveResponse(this.UserAssignmentService.create(this.userIncidentAssigment));
        }
    }

    protected subscribeToSaveResponseIncidentUpdate(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe(
            (res: HttpResponse<IIncident>) => this.onSaveSuccessIncidentUpdate(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected subscribeToSaveResponseIncident(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe(
            (res: HttpResponse<IIncident>) => this.onSaveSuccessIncident(res.body.id),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
            (res: HttpResponse<IUserIncidentAssigment>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    openNext() {
        this.index = this.index === 2 ? 0 : this.index + 1;
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }

    protected onSaveSuccessIncidentUpdate() {
        this.isSaving = false;
    }

    protected onSaveSuccessIncident(id) {
        this.isSaving = false;
        this.router.navigate(['ticket/' + id + '/edit']);
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
