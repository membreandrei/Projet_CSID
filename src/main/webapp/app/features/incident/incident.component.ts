import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { OrderPipe } from 'ngx-order-pipe';
import { IIncident } from 'app/shared/model/incident.model';
import { Account, AccountService } from 'app/core';
import { IncidentService } from './incident.service';
import { UserAppService } from 'app/entities/user-app';
import { IUserApp } from 'app/shared/model/user-app.model';
import { formatDate } from '@angular/common';
// @ts-ignore
import { AgGridNg2 } from 'ag-grid-angular';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-incident',
    templateUrl: './incident.component.html',
    styles: []
})
export class IncidentComponent implements OnInit, OnDestroy {
    @ViewChild('agGrid') agGrid: AgGridNg2;
    gridApi;
    incidents: IIncident[];
    userAppId: any;
    accountId: any;
    currentAccount: any;
    eventSubscriber: Subscription;
    Sujet: any;
    value: any;
    account: Account;
    user: any;
    show = true;
    incidentUpdate: IIncident;
    isSaving: boolean;
    incidentSuppr: any;
    columnDefs = [
        {
            headerName: 'ID',
            field: 'ID',
            sortable: true,
            filter: true,
            checkboxSelection: true,
            resizable: true,
            headerCheckboxSelection: true,
            headerCheckboxSelectionFilteredOnly: true
        },
        { headerName: 'Titre', field: 'Titre', sortable: true, filter: true, resizable: true },
        { headerName: 'Statut', field: 'Statut', sortable: true, filter: true, resizable: true },
        { headerName: 'Priorite', field: 'Priorite', sortable: true, filter: true, resizable: true },
        { headerName: 'Sujet', field: 'Sujet', sortable: true, filter: true, resizable: true },
        { headerName: 'Categorie', field: 'Categorie', sortable: true, filter: true, resizable: true },
        { headerName: 'Description', field: 'Description', sortable: true, filter: true, resizable: true },
        { headerName: 'Date de début', field: 'DateDebut', sortable: true, filter: true, resizable: true },
        { headerName: 'Date de fin', field: 'DateFin', sortable: true, filter: true, resizable: true },
        { headerName: 'Utilisateur', field: 'UserApp', sortable: true, filter: true, resizable: true }
    ];
    rowData: any[];

    constructor(
        protected userAppService: UserAppService,
        private incidentService: IncidentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    onRowSelected(event) {
        this.router.navigate(['ticket/' + event.node.data.ID + '/edit']);
    }

    getSelectedRows() {
        const selectedNodes = this.agGrid.api.getSelectedNodes();
        const selectedData = selectedNodes.map(node => node.data);
        const selectedDataStringPresentation = selectedData.map(node => node.ID + ',').join('');
        this.incidentSuppr = selectedDataStringPresentation;
    }

    saveResolu() {
        const selectedNodes = this.agGrid.api.getSelectedNodes();
        const selectedData = selectedNodes.map(node => node.data);
        const selectedDataStringPresentation = selectedData.map(node => node.ID + ',').join('');
        let x = '';
        for (let i = 0; i < selectedDataStringPresentation.length; i++) {
            if (selectedDataStringPresentation.charAt(i) !== ',') {
                x += selectedDataStringPresentation.charAt(i);
            } else {
                this.incidentService
                    .find(Number(x))
                    .pipe(
                        filter((res: HttpResponse<IIncident>) => res.ok),
                        map((res: HttpResponse<IIncident>) => res.body)
                    )
                    .subscribe(
                        (res: IIncident) => {
                            this.incidentUpdate = res;
                            this.isSaving = true;
                            this.incidentUpdate.statut = 'Resolu';
                            this.incidentUpdate.dateFin = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
                            this.subscribeToSaveResponse(this.incidentService.update(this.incidentUpdate));
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                let tab = this.rowData;
                this.rowData = [];
                for (const incident of tab) {
                    if (incident.id === Number(x)) {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    id: incident.id,
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: 'Resolu',
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: incident.DateFin,
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                id: incident.id,
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: 'Resolu',
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: incident.DateFin,
                                UserApp: incident.UserApp
                            });
                        }
                    } else {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    id: incident.id,
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: incident.Statut,
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: incident.DateFin,
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                id: incident.id,
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: incident.Statut,
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: incident.DateFin,
                                UserApp: incident.UserApp
                            });
                        }
                    }
                }
                x = '';
            }
        }
    }
    saveNonResolu() {
        const selectedNodes = this.agGrid.api.getSelectedNodes();
        const selectedData = selectedNodes.map(node => node.data);
        const selectedDataStringPresentation = selectedData.map(node => node.ID + ',').join('');
        let x = '';
        for (let i = 0; i < selectedDataStringPresentation.length; i++) {
            if (selectedDataStringPresentation.charAt(i) !== ',') {
                x += selectedDataStringPresentation.charAt(i);
            } else {
                this.incidentService
                    .find(Number(x))
                    .pipe(
                        filter((res: HttpResponse<IIncident>) => res.ok),
                        map((res: HttpResponse<IIncident>) => res.body)
                    )
                    .subscribe(
                        (res: IIncident) => {
                            this.incidentUpdate = res;
                            this.isSaving = true;
                            this.incidentUpdate.statut = 'Non Resolu';
                            this.incidentUpdate.dateFin = formatDate(new Date(), 'yyyy-MM-dd', 'fr');
                            this.subscribeToSaveResponse(this.incidentService.update(this.incidentUpdate));
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                let tab = this.rowData;
                this.rowData = [];
                for (const incident of tab) {
                    if (incident.id === Number(x)) {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    id: incident.id,
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: 'Non Resolu',
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: incident.DateFin,
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                id: incident.id,
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: 'Non Resolu',
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: incident.DateFin,
                                UserApp: incident.UserApp
                            });
                        }
                    } else {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    id: incident.id,
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: incident.Statut,
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: incident.DateFin,
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                id: incident.id,
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: incident.Statut,
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: incident.DateFin,
                                UserApp: incident.UserApp
                            });
                        }
                    }
                }
                x = '';
            }
        }
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe((res: HttpResponse<IIncident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    gridReady(params) {
        this.gridApi = params.api;

        this.gridApi.sizeColumnsToFit();
    }

    loadAll() {
        this.rowData = [];
        this.incidentService
            .query()
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.incidents = res;

                    for (const incident of this.incidents) {
                        this.loadData(incident);
                    }

                    console.log(this.rowData);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadData(incident) {
        if (this.rowData === undefined) {
            this.rowData = [
                {
                    id: incident.id,
                    ID: incident.id,
                    Titre: incident.titre,
                    Statut: incident.statut,
                    Priorite: incident.priorite,
                    Sujet: incident.sujet,
                    Categorie: incident.categorie,
                    Description: incident.description,
                    DateDebut: incident.dateDebut,
                    DateFin: incident.dateFin,
                    UserApp: incident.userApp.user.firstName + ' - ' + incident.userApp.user.lastName
                }
            ];
        } else {
            this.rowData.push({
                id: incident.id,
                ID: incident.id,
                Titre: incident.titre,
                Statut: incident.statut,
                Priorite: incident.priorite,
                Sujet: incident.sujet,
                Categorie: incident.categorie,
                Description: incident.description,
                DateDebut: incident.dateDebut,
                DateFin: incident.dateFin,
                UserApp: incident.userApp.user.firstName + ' - ' + incident.userApp.user.lastName
            });
        }

        this.agGrid.api.setRowData(this.rowData);
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    ngOnInit() {
        this.isSaving = false;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.accountId = account.id;
        });
        this.loadAll();
        this.registerChangeInIncidents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncident) {
        return item.id;
    }

    registerChangeInIncidents() {
        this.eventSubscriber = this.eventManager.subscribe('incidentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
