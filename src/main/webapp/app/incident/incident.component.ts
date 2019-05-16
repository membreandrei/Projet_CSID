import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { OrderPipe } from 'ngx-order-pipe';
import { IIncident } from 'app/shared/model/incident.model';
import { Account, AccountService } from 'app/core';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident',
    templateUrl: './incident.component.html',
    styles: []
})
export class IncidentComponent implements OnInit, OnDestroy {
    incidents: IIncident[];
    allIncidents: IIncident[];
    currentAccount: any;
    eventSubscriber: Subscription;
    account: Account;
    user: any;
    show = true;
    constructor(
        protected incidentService: IncidentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

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
                    this.allIncidents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    incident(event: any) {
        this.user = event.target.value;
        if (this.user !== 'all') {
            this.incidentService
                .query({
                    'userAppId.equals': this.user
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
        } else {
            this.loadAll();
        }
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }
    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then((account: Account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIncidents();
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
