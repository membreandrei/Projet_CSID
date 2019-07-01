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
    gridColumnApi;
    incidents: IIncident[];
    userAppId: any;
    accountAuthorities: any;
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
            width: 130,
            sortable: true,
            filter: true,
            checkboxSelection: true,
            resizable: true,
            headerCheckboxSelection: true,
            headerCheckboxSelectionFilteredOnly: true
        },
        { headerName: 'Titre', field: 'Titre', width: 110, sortable: true, filter: true, resizable: true },
        { headerName: 'Statut', field: 'Statut', width: 140, sortable: true, filter: true, resizable: true },
        { headerName: 'Priorite', field: 'Priorite', sortable: true, filter: true, resizable: true },
        { headerName: 'Sujet', field: 'Sujet', sortable: true, filter: true, resizable: true },
        { headerName: 'Categorie', field: 'Categorie', sortable: true, filter: true, resizable: true },
        { headerName: 'Description', field: 'Description', sortable: true, filter: true, resizable: true },
        {
            headerName: 'Date de début',
            field: 'DateDebut',
            width: 210,
            sortable: true,
            resizable: true,
            filter: 'agDateColumnFilter',
            filterParams: {
                comparator: function(filterLocalDateAtMidnight, cellValue) {
                    const dateAsString = cellValue;
                    const dateParts = dateAsString.split('-');
                    const cellDate = new Date(Number(dateParts[0]), Number(dateParts[1]) - 1, Number(dateParts[2]));
                    if (filterLocalDateAtMidnight.getTime() === cellDate.getTime()) {
                        return 0;
                    }
                    if (cellDate < filterLocalDateAtMidnight) {
                        return -1;
                    }
                    if (cellDate > filterLocalDateAtMidnight) {
                        return 1;
                    }
                }
            }
        },
        {
            headerName: 'Date de fin',
            field: 'DateFin',
            width: 210,
            sortable: true,
            resizable: true,
            filter: 'agDateColumnFilter',
            filterParams: {
                comparator: function(filterLocalDateAtMidnight, cellValue) {
                    const dateAsString = cellValue;
                    const dateParts = dateAsString.split('-');
                    const cellDate = new Date(Number(dateParts[0]), Number(dateParts[1]) - 1, Number(dateParts[2]));
                    if (filterLocalDateAtMidnight.getTime() === cellDate.getTime()) {
                        return 0;
                    }
                    if (cellDate < filterLocalDateAtMidnight) {
                        return -1;
                    }
                    if (cellDate > filterLocalDateAtMidnight) {
                        return 1;
                    }
                }
            }
        },
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
        for (const i of this.accountAuthorities) {
            if (i === 'ROLE_USER') {
                this.router.navigate(['ticket/' + event.node.data.ID + '/view']);
                break;
            } else {
                this.router.navigate(['ticket/' + event.node.data.ID + '/edit']);
            }
        }
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

                const tab = this.rowData;
                this.rowData = [];
                for (const incident of tab) {
                    if (incident.ID === Number(x)) {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: 'Resolu',
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: formatDate(new Date(), 'yyyy-MM-dd', 'fr'),
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: 'Resolu',
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: formatDate(new Date(), 'yyyy-MM-dd', 'fr'),
                                UserApp: incident.UserApp
                            });
                        }
                    } else {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
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
                const tab = this.rowData;
                this.rowData = [];
                for (const incident of tab) {
                    if (incident.ID === Number(x)) {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
                                    ID: incident.ID,
                                    Titre: incident.Titre,
                                    Statut: 'Non Resolu',
                                    Priorite: incident.Priorite,
                                    Sujet: incident.Sujet,
                                    Categorie: incident.Categorie,
                                    Description: incident.Description,
                                    DateDebut: incident.DateDebut,
                                    DateFin: formatDate(new Date(), 'yyyy-MM-dd', 'fr'),
                                    UserApp: incident.UserApp
                                }
                            ];
                        } else {
                            this.rowData.push({
                                ID: incident.ID,
                                Titre: incident.Titre,
                                Statut: 'Non Resolu',
                                Priorite: incident.Priorite,
                                Sujet: incident.Sujet,
                                Categorie: incident.Categorie,
                                Description: incident.Description,
                                DateDebut: incident.DateDebut,
                                DateFin: formatDate(new Date(), 'yyyy-MM-dd', 'fr'),
                                UserApp: incident.UserApp
                            });
                        }
                    } else {
                        if (this.rowData === undefined) {
                            this.rowData = [
                                {
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
        /*
        this.gridColumnApi = params.columnApi;
        var allColumnIds = [];
        this.gridColumnApi.getAllColumns().forEach(function(column) {
            allColumnIds.push(column.colId);
        });
        this.gridColumnApi.autoSizeColumns(allColumnIds);*/
    }

    loadAll() {
        for (const i of this.accountAuthorities) {
            if (i === 'ROLE_USER') {
                this.userAppService
                    .query({
                        'userId.equals': this.accountId
                    })
                    .pipe(
                        filter((res: HttpResponse<IUserApp[]>) => res.ok),
                        map((res: HttpResponse<IUserApp[]>) => res.body)
                    )
                    .subscribe(
                        (res: IUserApp[]) => {
                            this.incidentService
                                .query({ 'userAppId.equals': res[0].id })
                                .pipe(
                                    filter((res1: HttpResponse<IIncident[]>) => res1.ok),
                                    map((res1: HttpResponse<IIncident[]>) => res1.body)
                                )
                                .subscribe(
                                    (res1: IIncident[]) => {
                                        this.rowData = [];
                                        this.incidents = res1;

                                        for (const incident of this.incidents) {
                                            this.loadData(incident);
                                        }
                                    },
                                    (res1: HttpErrorResponse) => this.onError(res1.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );

                break;
            } else {
                this.incidentService
                    .query()
                    .pipe(
                        filter((res: HttpResponse<IIncident[]>) => res.ok),
                        map((res: HttpResponse<IIncident[]>) => res.body)
                    )
                    .subscribe(
                        (res: IIncident[]) => {
                            this.rowData = [];
                            this.incidents = res;

                            for (const incident of this.incidents) {
                                this.loadData(incident);
                            }
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        }
    }

    loadData(incident) {
        if (this.rowData === undefined) {
            this.rowData = [
                {
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
            this.accountAuthorities = account.authorities;
            this.loadAll();
        });
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
