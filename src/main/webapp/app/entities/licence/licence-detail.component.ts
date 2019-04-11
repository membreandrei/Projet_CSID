import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicence } from 'app/shared/model/licence.model';

@Component({
    selector: 'jhi-licence-detail',
    templateUrl: './licence-detail.component.html'
})
export class LicenceDetailComponent implements OnInit {
    licence: ILicence;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ licence }) => {
            this.licence = licence;
        });
    }

    previousState() {
        window.history.back();
    }
}
