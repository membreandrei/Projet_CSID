import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

@Component({
    selector: 'jhi-incident-delete-dialog',
    templateUrl: './incident-delete-dialog.component.html'
})
export class IncidentDeleteDialogComponent {
    incident: IIncident;
    id: String;
    userIncidentAssigmentTab: any[];

    constructor(
        protected userIncidentAssigmentService: UserIncidentAssigmentService,
        protected incidentService: IncidentService,
        public activeModal: NgbActiveModal,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(idString: String) {
        let x = '';
        for (let i = 0; i < idString.length; i++) {
            if (idString.charAt(i) !== ',') {
                x += idString.charAt(i);
            } else {
                this.incidentService.delete(Number(x)).subscribe(response => {
                    this.eventManager.broadcast({
                        name: 'incidentListModification',
                        content: 'Deleted an incident'
                    });
                    this.activeModal.dismiss(true);
                });
                x = '';
            }
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

@Component({
    selector: 'jhi-incident-delete-popup',
    template: ''
})
export class IncidentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incident }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncidentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incident = incident;
                this.ngbModalRef.componentInstance.id = this.activatedRoute.snapshot.paramMap.get('id');
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ticket']);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ticket']);
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
