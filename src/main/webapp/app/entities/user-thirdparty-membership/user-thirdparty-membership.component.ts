import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { AccountService } from 'app/core';
import { UserThirdpartyMembershipService } from './user-thirdparty-membership.service';

@Component({
    selector: 'jhi-user-thirdparty-membership',
    templateUrl: './user-thirdparty-membership.component.html'
})
export class UserThirdpartyMembershipComponent implements OnInit, OnDestroy {
    userThirdpartyMemberships: IUserThirdpartyMembership[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected userThirdpartyMembershipService: UserThirdpartyMembershipService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.userThirdpartyMembershipService
            .query()
            .pipe(
                filter((res: HttpResponse<IUserThirdpartyMembership[]>) => res.ok),
                map((res: HttpResponse<IUserThirdpartyMembership[]>) => res.body)
            )
            .subscribe(
                (res: IUserThirdpartyMembership[]) => {
                    this.userThirdpartyMemberships = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserThirdpartyMemberships();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserThirdpartyMembership) {
        return item.id;
    }

    registerChangeInUserThirdpartyMemberships() {
        this.eventSubscriber = this.eventManager.subscribe('userThirdpartyMembershipListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
