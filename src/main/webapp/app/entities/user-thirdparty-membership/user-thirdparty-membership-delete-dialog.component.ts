import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { UserThirdpartyMembershipService } from './user-thirdparty-membership.service';

@Component({
    selector: 'jhi-user-thirdparty-membership-delete-dialog',
    templateUrl: './user-thirdparty-membership-delete-dialog.component.html'
})
export class UserThirdpartyMembershipDeleteDialogComponent {
    userThirdpartyMembership: IUserThirdpartyMembership;

    constructor(
        protected userThirdpartyMembershipService: UserThirdpartyMembershipService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userThirdpartyMembershipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userThirdpartyMembershipListModification',
                content: 'Deleted an userThirdpartyMembership'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-thirdparty-membership-delete-popup',
    template: ''
})
export class UserThirdpartyMembershipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userThirdpartyMembership }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserThirdpartyMembershipDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userThirdpartyMembership = userThirdpartyMembership;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-thirdparty-membership', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-thirdparty-membership', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
