import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/features/incident/incident.service';
import { LoginModalService, AccountService, Account } from 'app/core';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Chart } from 'chart.js';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: any;
    modalRef: NgbModalRef;
    allIncidents: IIncident[];
    numberResolu: any;
    Resolu: any;
    pourcentageResolu: any;
    nombreIncident: any;
    numberNonResolu: any;
    NonResolu: any;
    numberEnCours: any;
    EnCours: any;
    dataArea: any;
    optionsArea: any;
    dataDonut: any;
    optionsDonut: any;
    dataBar: any;
    optionsBar: any;
    displayNonResolu: boolean;
    displayEnCours: boolean;
    displayResolu: boolean;
    accountId: any;
    userAppId: any;
    private incidents: IIncident[];

    constructor(
        protected userAppService: UserAppService,
        private incidentService: IncidentService,
        private accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {}

    showDialogNonResolu() {
        this.displayNonResolu = true;
    }

    showDialogEnCours() {
        this.displayEnCours = true;
    }

    showDialogResolu() {
        this.displayResolu = true;
    }

    chartArea() {
        this.dataArea = {
            labels: ['Janv', 'Fév', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
            datasets: [
                {
                    label: 'Demande',
                    lineTension: 0.3,
                    backgroundColor: 'rgba(78, 115, 223, 0.30)',
                    borderColor: 'rgba(78, 115, 223, 1)',
                    pointRadius: 3,
                    pointBackgroundColor: 'rgba(78, 115, 223, 1)',
                    pointBorderColor: 'rgba(78, 115, 223, 1)',
                    pointHoverRadius: 3,
                    pointHoverBackgroundColor: 'rgba(78, 115, 223, 1)',
                    pointHoverBorderColor: 'rgba(78, 115, 223, 1)',
                    pointHitRadius: 10,
                    pointBorderWidth: 1,
                    data: [3, 5, 2, 7, 4, 6, 3]
                }
            ]
        };
        this.optionsArea = {
            scales: {
                xAxes: [
                    {
                        time: {
                            unit: 'date'
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false
                        },
                        ticks: {
                            maxTicksLimit: 15
                        }
                    }
                ],
                yAxes: [
                    {
                        ticks: {
                            maxTicksLimit: 10,
                            padding: 10
                        },
                        gridLines: {
                            color: 'rgb(234, 236, 244)',
                            zeroLineColor: 'rgb(234, 236, 244)',
                            drawBorder: false,
                            borderDash: [2],
                            zeroLineBorderDash: [2]
                        }
                    }
                ]
            },
            legend: {
                display: false
            }
        };
    }

    chartDonut() {
        this.dataDonut = {
            labels: ['Incident Non résolu', 'Incident En Cours', 'Incident résolu'],
            datasets: [
                {
                    data: [55, 30, 15],
                    backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
                    hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
                    hoverBorderColor: 'rgba(234, 236, 244, 1)'
                }
            ]
        };

        this.optionsDonut = {
            tooltips: {
                bodyFontColor: '#858796',
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                caretPadding: 10
            },
            legend: {
                display: false
            },
            cutoutPercentage: 80
        };
    }

    chartBar() {
        this.dataBar = {
            labels: ['Janv', 'Fév', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
            datasets: [
                {
                    label: 'Incident Résolu',
                    backgroundColor: '#4e73df',
                    borderColor: '#1E88E5',
                    data: [65, 59, 80, 81, 56, 55, 40]
                },
                {
                    label: 'Incindent Non Résolu',
                    backgroundColor: '#1cc88a',
                    borderColor: '#7CB342',
                    data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };

        this.optionsBar = {
            scales: {
                xAxes: [
                    {
                        gridLines: {
                            display: false,
                            drawBorder: false
                        }
                    }
                ],
                yAxes: [
                    {
                        ticks: {
                            maxTicksLimit: 10,
                            padding: 10
                        },
                        gridLines: {
                            color: 'rgb(234, 236, 244)',
                            zeroLineColor: 'rgb(234, 236, 244)',
                            drawBorder: false,
                            borderDash: [2],
                            zeroLineBorderDash: [2]
                        }
                    }
                ]
            },
            legend: {
                display: false
            },
            cutoutPercentage: 80
        };
    }

    public incidentResolu(val: any) {
        this.incidentService
            .queryCount({ 'statut.equals': 'Resolu', 'userAppId.equals': val })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberResolu = res;
                    this.incidentService
                        .queryCount({ 'userAppId.equals': val })
                        .pipe(
                            filter((res1: HttpResponse<any>) => res1.ok),
                            map((res1: HttpResponse<any>) => res1.body)
                        )
                        .subscribe(
                            (res1: IIncident[]) => {
                                this.nombreIncident = res1;
                                this.dataDonut.datasets[0].data[2] = this.numberResolu;
                                this.pourcentageResolu = Math.round((this.numberResolu / this.nombreIncident) * 100);
                            },
                            (res1: HttpErrorResponse) => this.onError(res1.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.incidentService
            .query({ 'statut.equals': 'Resolu' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.Resolu = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    public incidentNonResolu(val: any) {
        this.incidentService
            .queryCount({ 'statut.equals': 'Non Resolu', 'userAppId.equals': val })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberNonResolu = res;
                    this.dataDonut.datasets[0].data[0] = this.numberNonResolu;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.incidentService
            .query({ 'statut.equals': 'Non Resolu' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.NonResolu = res;
                    this.dataDonut.datasets[0].data[0] = this.numberNonResolu;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    public incidentEnCours(val: any) {
        this.incidentService
            .queryCount({ 'statut.equals': 'En Cours', 'userAppId.equals': val })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.numberEnCours = res;
                    this.dataDonut.datasets[0].data[1] = this.numberEnCours;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.incidentService
            .query({ 'statut.equals': 'En Cours' })
            .pipe(
                filter((res: HttpResponse<any>) => res.ok),
                map((res: HttpResponse<any>) => res.body)
            )
            .subscribe(
                (res: IIncident[]) => {
                    this.EnCours = res;
                    this.dataDonut.datasets[0].data[1] = this.numberEnCours;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    getUserAppId() {
        this.userAppService
            .query()
            .pipe(
                filter((res: HttpResponse<IUserApp[]>) => res.ok),
                map((res: HttpResponse<IUserApp[]>) => res.body)
            )
            .subscribe(
                (res: IUserApp[]) => {
                    const userApps = res;
                    for (const userApp of userApps) {
                        if (userApp.user.id === this.accountId) {
                            this.userAppId = userApp.id;
                        }
                    }
                    this.incidentResolu(this.userAppId);
                    this.incidentEnCours(this.userAppId);
                    this.incidentNonResolu(this.userAppId);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    ngOnInit() {
        this.accountService.identity().then(account => {
            this.account = account;
            this.accountId = account.id;
        });
        this.getUserAppId();
        this.registerAuthenticationSuccess();
        this.chartArea();
        this.chartDonut();
        this.chartBar();
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
