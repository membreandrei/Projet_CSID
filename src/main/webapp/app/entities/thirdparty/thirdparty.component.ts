import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { AccountService } from 'app/core';
import { ThirdpartyService } from './thirdparty.service';

@Component({
    selector: 'jhi-thirdparty',
    templateUrl: './thirdparty.component.html'
})
export class ThirdpartyComponent implements OnInit, OnDestroy {
    thirdparties: IThirdparty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected thirdpartyService: ThirdpartyService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.thirdpartyService
            .query()
            .pipe(
                filter((res: HttpResponse<IThirdparty[]>) => res.ok),
                map((res: HttpResponse<IThirdparty[]>) => res.body)
            )
            .subscribe(
                (res: IThirdparty[]) => {
                    this.thirdparties = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInThirdparties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IThirdparty) {
        return item.id;
    }

    registerChangeInThirdparties() {
        this.eventSubscriber = this.eventManager.subscribe('thirdpartyListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
