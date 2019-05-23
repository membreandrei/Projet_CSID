import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/incident/incident.service';
import { LoginModalService, AccountService, Account } from 'app/core';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    allIncidents: IIncident[];
    numberResolu: any;
    pourcentageResolu: any;
    nombreIncident: any;
    numberNonResolu: any;
    numberEnCours: any;
    constructor(
        private incidentService: IncidentService,
        private accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {}

    public incidentResolu() {
        this.incidentService
            .queryCount({ 'statut.equals': 'Resolu' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberResolu = res;
                    this.incidentService
                        .queryCount()
                        .pipe(
                            filter((res1: HttpResponse<any>) => res1.ok),
                            map((res1: HttpResponse<any>) => res1.body)
                        )
                        .subscribe(
                            (res1: IIncident[]) => {
                                this.nombreIncident = res1;
                                this.pourcentageResolu = Math.round((this.numberResolu / this.nombreIncident) * 100);
                            },
                            (res1: HttpErrorResponse) => this.onError(res1.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    public incidentNonResolu() {
        this.incidentService
            .queryCount({ 'statut.equals': 'Non Resolu' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberNonResolu = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    public incidentEnCours() {
        this.incidentService
            .queryCount({ 'statut.equals': 'En Cours' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberEnCours = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    ngOnInit() {
        this.incidentResolu();
        this.incidentNonResolu();
        this.incidentEnCours();
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }
    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
