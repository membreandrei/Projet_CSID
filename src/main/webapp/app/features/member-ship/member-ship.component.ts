import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { AccountService } from 'app/core';
import { MembershipService } from './member-ship.service';
import { UserAppService } from 'app/entities/user-app';
import { IUserApp } from 'app/shared/model/user-app.model';

@Component({
    selector: 'jhi-user-thirdparty-membership',
    templateUrl: './member-ship.component.html'
})
export class MemberShipComponent implements OnInit, OnDestroy {
    userThirdpartyMemberships: IUserThirdpartyMembership[];
    currentAccount: any;
    accountId: any;
    userAppEntrepriseId: any;
    eventSubscriber: Subscription;

    constructor(
        protected userAppService: UserAppService,
        protected membershipService: MembershipService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
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
                            this.userAppEntrepriseId = userApp.userThirdpartyMembership.thirdparty.id;
                        }
                    }
                    this.membershipService
                        .query({
                            'thirdpartyId.equals': this.userAppEntrepriseId
                        })
                        .pipe(
                            filter((res1: HttpResponse<IUserThirdpartyMembership[]>) => res1.ok),
                            map((res1: HttpResponse<IUserThirdpartyMembership[]>) => res1.body)
                        )
                        .subscribe(
                            (res1: IUserThirdpartyMembership[]) => {
                                this.userThirdpartyMemberships = res1;
                            },
                            (res1: HttpErrorResponse) => this.onError(res1.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
        });

        this.loadAll();
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
