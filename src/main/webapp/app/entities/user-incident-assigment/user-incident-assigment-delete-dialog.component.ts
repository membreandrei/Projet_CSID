import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { UserIncidentAssigmentService } from './user-incident-assigment.service';

@Component({
    selector: 'jhi-user-incident-assigment-delete-dialog',
    templateUrl: './user-incident-assigment-delete-dialog.component.html'
})
export class UserIncidentAssigmentDeleteDialogComponent {
    userIncidentAssigment: IUserIncidentAssigment;

    constructor(
        protected userIncidentAssigmentService: UserIncidentAssigmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userIncidentAssigmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userIncidentAssigmentListModification',
                content: 'Deleted an userIncidentAssigment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-incident-assigment-delete-popup',
    template: ''
})
export class UserIncidentAssigmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userIncidentAssigment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserIncidentAssigmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userIncidentAssigment = userIncidentAssigment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-incident-assigment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-incident-assigment', { outlets: { popup: null } }]);
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
