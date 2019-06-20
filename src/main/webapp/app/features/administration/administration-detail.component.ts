import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserApp } from 'app/shared/model/user-app.model';

@Component({
    selector: 'jhi-user-app-detail',
    templateUrl: './administration-detail.component.html'
})
export class AdministrationDetailComponent implements OnInit {
    userApp: IUserApp;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userApp }) => {
            this.userApp = userApp;
        });
    }

    previousState() {
        window.history.back();
    }
}
