import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThirdparty } from 'app/shared/model/thirdparty.model';

@Component({
    selector: 'jhi-thirdparty-detail',
    templateUrl: './member-ship-detail.component.html'
})
export class MemberShipDetailComponent implements OnInit {
    thirdparty: IThirdparty;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ thirdparty }) => {
            this.thirdparty = thirdparty;
        });
    }

    previousState() {
        window.history.back();
    }
}
