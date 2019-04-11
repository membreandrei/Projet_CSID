import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

@Component({
    selector: 'jhi-user-incident-assigment-detail',
    templateUrl: './user-incident-assigment-detail.component.html'
})
export class UserIncidentAssigmentDetailComponent implements OnInit {
    userIncidentAssigment: IUserIncidentAssigment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userIncidentAssigment }) => {
            this.userIncidentAssigment = userIncidentAssigment;
        });
    }

    previousState() {
        window.history.back();
    }
}
