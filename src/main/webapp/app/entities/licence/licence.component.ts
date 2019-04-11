import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILicence } from 'app/shared/model/licence.model';
import { AccountService } from 'app/core';
import { LicenceService } from './licence.service';

@Component({
    selector: 'jhi-licence',
    templateUrl: './licence.component.html'
})
export class LicenceComponent implements OnInit, OnDestroy {
    licences: ILicence[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected licenceService: LicenceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.licenceService
            .query()
            .pipe(
                filter((res: HttpResponse<ILicence[]>) => res.ok),
                map((res: HttpResponse<ILicence[]>) => res.body)
            )
            .subscribe(
                (res: ILicence[]) => {
                    this.licences = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLicences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILicence) {
        return item.id;
    }

    registerChangeInLicences() {
        this.eventSubscriber = this.eventManager.subscribe('licenceListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
