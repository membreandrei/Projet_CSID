import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserApp } from 'app/shared/model/user-app.model';
import { AccountService, User, UserService } from 'app/core';
import { UserAppService } from 'app/entities/user-app/user-app.service';

@Component({
    selector: 'jhi-administration',
    templateUrl: './administration.component.html',
    styles: []
})
export class AdministrationComponent implements OnInit, OnDestroy {
    userApps: IUserApp[];
    currentAccount: any;
    eventSubscriber: Subscription;
    entreprise: any;
    accountId: any;
    users: User[];
    entrepiseId: any;

    constructor(
        protected userAppService: UserAppService,
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
                    this.userApps = res;
                    for (const userApp of this.userApps) {
                        if (userApp.user.id === this.accountId) {
                            this.entreprise = userApp.userThirdpartyMembership.thirdparty.denomination;
                            this.entrepiseId = userApp.userThirdpartyMembership.thirdparty.id;
                        }
                    }

                    for (let i = 0; i < this.userApps.length; i++) {
                        if (
                            this.userApps[i].userThirdpartyMembership === null ||
                            this.userApps[i].userThirdpartyMembership.thirdparty.id !== this.entrepiseId
                        ) {
                            this.userApps.splice(i, 1);

                            i--;
                        }
                    }
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
        this.registerChangeInUserApps();
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserApp) {
        return item.id;
    }

    registerChangeInUserApps() {
        this.eventSubscriber = this.eventManager.subscribe('userAppListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
