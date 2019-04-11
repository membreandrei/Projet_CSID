import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { ThirdpartyService } from './thirdparty.service';

@Component({
    selector: 'jhi-thirdparty-update',
    templateUrl: './thirdparty-update.component.html'
})
export class ThirdpartyUpdateComponent implements OnInit {
    thirdparty: IThirdparty;
    isSaving: boolean;

    constructor(protected thirdpartyService: ThirdpartyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ thirdparty }) => {
            this.thirdparty = thirdparty;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.thirdparty.id !== undefined) {
            this.subscribeToSaveResponse(this.thirdpartyService.update(this.thirdparty));
        } else {
            this.subscribeToSaveResponse(this.thirdpartyService.create(this.thirdparty));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IThirdparty>>) {
        result.subscribe((res: HttpResponse<IThirdparty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
