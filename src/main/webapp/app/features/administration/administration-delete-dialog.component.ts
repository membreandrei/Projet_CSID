import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserApp } from 'app/shared/model/user-app.model';
import { AdministrationService } from './administration.service';

@Component({
    selector: 'jhi-user-app-delete-dialog',
    templateUrl: './administration-delete-dialog.component.html'
})
export class AdministrationDeleteDialogComponent {
    userApp: IUserApp;

    constructor(
        protected userAppService: AdministrationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAppService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userAppListModification',
                content: 'Deleted an userApp'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-app-delete-popup',
    template: ''
})
export class AdministrationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userApp }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdministrationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userApp = userApp;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/portailAdministration/mesUtilisateurs', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/portailAdministration/mesUtilisateurs', { outlets: { popup: null } }]);
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
