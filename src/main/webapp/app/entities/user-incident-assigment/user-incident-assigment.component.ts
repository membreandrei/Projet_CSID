import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { AccountService } from 'app/core';
import { UserIncidentAssigmentService } from './user-incident-assigment.service';

@Component({
    selector: 'jhi-user-incident-assigment',
    templateUrl: './user-incident-assigment.component.html'
})
export class UserIncidentAssigmentComponent implements OnInit, OnDestroy {
    userIncidentAssigments: IUserIncidentAssigment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
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

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserIncidentAssigments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
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
