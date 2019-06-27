import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { UserIncidentAssigmentService } from './user-incident-assigment.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident';
import { formatDate } from '@angular/common';

@Component({
    selector: 'jhi-user-incident-assigment-update',
    templateUrl: './user-incident-assigment-update.component.html'
})
export class UserIncidentAssigmentUpdateComponent implements OnInit {
    userIncidentAssigment: IUserIncidentAssigment;
    isSaving: boolean;

    userapps: IUserApp[];

    incidents: IIncident[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userIncidentAssigmentService: UserIncidentAssigmentService,
        protected userAppService: UserAppService,
        protected incidentService: IncidentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userIncidentAssigment }) => {
            this.userIncidentAssigment = userIncidentAssigment;
        });
        this.userAppService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserApp[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserApp[]>) => response.body)
            )
            .subscribe((res: IUserApp[]) => (this.userapps = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.incidentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIncident[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncident[]>) => response.body)
            )
            .subscribe((res: IIncident[]) => (this.incidents = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userIncidentAssigment.id !== undefined) {
            this.subscribeToSaveResponse(this.userIncidentAssigmentService.update(this.userIncidentAssigment));
        } else {
            this.userIncidentAssigment.dateDebut = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
            this.subscribeToSaveResponse(this.userIncidentAssigmentService.create(this.userIncidentAssigment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserIncidentAssigment>>) {
        result.subscribe(
            (res: HttpResponse<IUserIncidentAssigment>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.nextState();
    }
    nextState() {
        window.location.href = 'http://localhost:9000/#/assignement';
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

    trackIncidentById(index: number, item: IIncident) {
        return item.id;
    }
}
