import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { ThirdpartyService } from './thirdparty.service';

@Component({
    selector: 'jhi-thirdparty-delete-dialog',
    templateUrl: './thirdparty-delete-dialog.component.html'
})
export class ThirdpartyDeleteDialogComponent {
    thirdparty: IThirdparty;

    constructor(
        protected thirdpartyService: ThirdpartyService,
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
export class ThirdpartyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ thirdparty }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ThirdpartyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.thirdparty = thirdparty;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/thirdparty', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/thirdparty', { outlets: { popup: null } }]);
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
