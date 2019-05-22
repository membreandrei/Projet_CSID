import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { UserThirdpartyMembershipService } from './user-thirdparty-membership.service';
import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { AccountService } from 'app/core';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';

@Component({
    selector: 'jhi-user-thirdparty-membership-update',
    templateUrl: './user-thirdparty-membership-update.component.html'
})
export class UserThirdpartyMembershipUpdateComponent implements OnInit {
    userThirdpartyMembership: IUserThirdpartyMembership;
    isSaving: boolean;
    currentAccount: any;
    accountId: any;
    thirdparties: IThirdparty[];
    userapps: any;
    userAppsEntreprise: any;

    constructor(
        protected userAppService: UserAppService,
        protected accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        protected userThirdpartyMembershipService: UserThirdpartyMembershipService,
        protected activatedRoute: ActivatedRoute
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
                    this.userAppsEntreprise = this.userapps[0].userThirdpartyMembership.thirdparty;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userThirdpartyMembership }) => {
            this.userThirdpartyMembership = userThirdpartyMembership;
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
            this.loadAll();
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userThirdpartyMembership.id !== undefined) {
            this.subscribeToSaveResponse(this.userThirdpartyMembershipService.update(this.userThirdpartyMembership));
        } else {
            this.userThirdpartyMembership.thirdparty = this.userAppsEntreprise;
            this.subscribeToSaveResponse(this.userThirdpartyMembershipService.create(this.userThirdpartyMembership));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserThirdpartyMembership>>) {
        result.subscribe(
            (res: HttpResponse<IUserThirdpartyMembership>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackThirdpartyById(index: number, item: IThirdparty) {
        return item.id;
    }
}
