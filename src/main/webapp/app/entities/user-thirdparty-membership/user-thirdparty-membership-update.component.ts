import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { UserThirdpartyMembershipService } from './user-thirdparty-membership.service';
import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { ThirdpartyService } from 'app/entities/thirdparty';

@Component({
    selector: 'jhi-user-thirdparty-membership-update',
    templateUrl: './user-thirdparty-membership-update.component.html'
})
export class UserThirdpartyMembershipUpdateComponent implements OnInit {
    userThirdpartyMembership: IUserThirdpartyMembership;
    isSaving: boolean;

    thirdparties: IThirdparty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userThirdpartyMembershipService: UserThirdpartyMembershipService,
        protected thirdpartyService: ThirdpartyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userThirdpartyMembership }) => {
            this.userThirdpartyMembership = userThirdpartyMembership;
        });
        this.thirdpartyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IThirdparty[]>) => mayBeOk.ok),
                map((response: HttpResponse<IThirdparty[]>) => response.body)
            )
            .subscribe((res: IThirdparty[]) => (this.thirdparties = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userThirdpartyMembership.id !== undefined) {
            this.subscribeToSaveResponse(this.userThirdpartyMembershipService.update(this.userThirdpartyMembership));
        } else {
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
