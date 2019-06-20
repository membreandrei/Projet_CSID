import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { MembershipService } from './member-ship.service';

@Component({
    selector: 'jhi-thirdparty-delete-dialog',
    templateUrl: './member-ship-delete-dialog.component.html'
})
export class MemberShipDeleteDialogComponent {
    thirdparty: IThirdparty;

    constructor(
        protected thirdpartyService: MembershipService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.thirdpartyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'thirdpartyListModification',
                content: 'Deleted an thirdparty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-thirdparty-delete-popup',
    template: ''
})
export class MembershipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ thirdparty }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MemberShipDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.thirdparty = thirdparty;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/portailAdministration/memberShip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/portailAdministration/memberShip', { outlets: { popup: null } }]);
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
