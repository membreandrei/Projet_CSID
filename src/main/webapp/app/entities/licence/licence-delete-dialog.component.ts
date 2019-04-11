import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicence } from 'app/shared/model/licence.model';
import { LicenceService } from './licence.service';

@Component({
    selector: 'jhi-licence-delete-dialog',
    templateUrl: './licence-delete-dialog.component.html'
})
export class LicenceDeleteDialogComponent {
    licence: ILicence;

    constructor(protected licenceService: LicenceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.licenceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'licenceListModification',
                content: 'Deleted an licence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-licence-delete-popup',
    template: ''
})
export class LicenceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ licence }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LicenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.licence = licence;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/licence', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/licence', { outlets: { popup: null } }]);
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
