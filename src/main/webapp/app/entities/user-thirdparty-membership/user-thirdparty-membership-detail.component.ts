import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

@Component({
    selector: 'jhi-user-thirdparty-membership-detail',
    templateUrl: './user-thirdparty-membership-detail.component.html'
})
export class UserThirdpartyMembershipDetailComponent implements OnInit {
    userThirdpartyMembership: IUserThirdpartyMembership;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userThirdpartyMembership }) => {
            this.userThirdpartyMembership = userThirdpartyMembership;
        });
    }

    previousState() {
        window.history.back();
    }
}
