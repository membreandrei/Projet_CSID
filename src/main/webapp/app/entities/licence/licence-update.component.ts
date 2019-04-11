import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILicence } from 'app/shared/model/licence.model';
import { LicenceService } from './licence.service';
import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { ThirdpartyService } from 'app/entities/thirdparty';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';

@Component({
    selector: 'jhi-licence-update',
    templateUrl: './licence-update.component.html'
})
export class LicenceUpdateComponent implements OnInit {
    licence: ILicence;
    isSaving: boolean;

    thirdparties: IThirdparty[];

    userlicences: IUserApp[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected licenceService: LicenceService,
        protected thirdpartyService: ThirdpartyService,
        protected userAppService: UserAppService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ licence }) => {
            this.licence = licence;
        });
        this.thirdpartyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IThirdparty[]>) => mayBeOk.ok),
                map((response: HttpResponse<IThirdparty[]>) => response.body)
            )
            .subscribe((res: IThirdparty[]) => (this.thirdparties = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userAppService
            .query({ filter: 'licence-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUserApp[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserApp[]>) => response.body)
            )
            .subscribe(
                (res: IUserApp[]) => {
                    if (!this.licence.userLicence || !this.licence.userLicence.id) {
                        this.userlicences = res;
                    } else {
                        this.userAppService
                            .find(this.licence.userLicence.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUserApp>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUserApp>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUserApp) => (this.userlicences = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.licence.id !== undefined) {
            this.subscribeToSaveResponse(this.licenceService.update(this.licence));
        } else {
            this.subscribeToSaveResponse(this.licenceService.create(this.licence));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicence>>) {
        result.subscribe((res: HttpResponse<ILicence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserAppById(index: number, item: IUserApp) {
        return item.id;
    }
}
