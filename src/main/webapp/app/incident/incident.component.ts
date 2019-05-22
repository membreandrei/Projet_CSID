import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { OrderPipe } from 'ngx-order-pipe';
import { IIncident } from 'app/shared/model/incident.model';
import { Account, AccountService } from 'app/core';
import { IncidentService } from './incident.service';
import { UserAppService } from 'app/entities/user-app';
import { IUserApp } from 'app/shared/model/user-app.model';
import { formatDate } from '@angular/common';

@Component({
    selector: 'jhi-incident',
    templateUrl: './incident.component.html',
    styles: []
})
export class IncidentComponent implements OnInit, OnDestroy {
    incidents: IIncident[];
    userAppId: any;
    accountId: any;
    currentAccount: any;
    eventSubscriber: Subscription;
    Sujet: any;
    value: any;
    account: Account;
    user: any;
    show = true;
    incidentUpdate: IIncident;
    isSaving: boolean;
    constructor(
        protected userAppService: UserAppService,
        private incidentService: IncidentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    incident(event: any) {
        this.user = event.target.value;
        console.log(event);
        if (this.user !== 'all') {
            switch (this.user) {
                case 'Majeure': {
                    this.subscribePriorite('Majeure');
                    break;
                }
                case 'Elevee': {
                    this.subscribePriorite('Elevee');
                    break;
                }
                case 'Normale': {
                    this.subscribePriorite('Normale');
                    break;
                }
                case 'Basse': {
                    this.subscribePriorite('Basse');
                    break;
                }
            }
        } else {
            this.loadAll();
        }
    }
    saveResolu(val: any) {
        this.incidentUpdate = val;
        this.isSaving = true;
        this.incidentUpdate.statut = 'Resolu';
        this.incidentUpdate.dateFin = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
        this.subscribeToSaveResponse(this.incidentService.update(this.incidentUpdate));
    }
    saveNonResolu(val: any) {
        this.incidentUpdate = val;
        this.isSaving = true;
        this.incidentUpdate.statut = 'Non Resolu';
        this.incidentUpdate.dateFin = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
        this.subscribeToSaveResponse(this.incidentService.update(this.incidentUpdate));
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe((res: HttpResponse<IIncident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveError() {
        this.isSaving = false;
    }
    subscribePriorite(req: any) {
        this.incidentService
            .query({
                'priorite.equals': req
            })
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    getSearchIncident(value) {
        this.value = value;

        this.incidentService
            .query({
                'titre.contains': this.value
            })
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadAll() {
        this.incidentService
            .query()
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    incidentUser(event: any) {
        this.user = event.target.value;
        console.log(event);
        if (this.user !== 'all') {
            switch (this.user) {
                case 'mesIncidents': {
                    this.subscribeMesIncident();
                    break;
                }
                case 'mesIncidentsResolu': {
                    this.subscribeIncident('Resolu');
                    break;
                }
                case 'mesIncidentsNonResolu': {
                    this.subscribeIncident('Non Resolu');
                    break;
                }
                case 'mesIncidentsCours': {
                    this.subscribeIncident('En cours');
                    break;
                }
            }
        } else {
            this.loadAll();
        }
    }
    subscribeMesIncident() {
        this.incidentService
            .query({
                'userAppId.equals': this.userAppId
            })
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    subscribeIncident(req: any) {
        this.incidentService
            .query({
                'userAppId.equals': this.userAppId,
                'statut.equals': req
            })
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;
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
                    this.incidentService
                        .query({
                            'userAppId.equals': this.userAppId
                        })
                        .pipe(
                            filter((res1: HttpResponse<IIncident[]>) => res1.ok),
                            map((res1: HttpResponse<IIncident[]>) => res1.body)
                        )
                        .subscribe(
                            (res1: IIncident[]) => {
                                this.incidents = res;
                            },
                            (res1: HttpErrorResponse) => this.onError(res1.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    ngOnInit() {
        this.isSaving = false;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
        });
        this.registerChangeInIncidents();
        this.getUserAppId();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncident) {
        return item.id;
    }

    registerChangeInIncidents() {
        this.eventSubscriber = this.eventManager.subscribe('incidentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
