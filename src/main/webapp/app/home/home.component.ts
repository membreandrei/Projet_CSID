import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
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
    title = 'app';
    public pieChartLabels: string[] = ['Pending', 'InProgress', 'OnHold', 'Complete', 'Cancelled'];
    public pieChartData: number[] = [21, 39, 10, 14, 16];
    public pieChartType = 'pie';
    public pieChartOptions: any = { backgroundColor: ['#FF6384', '#4BC0C0', '#FFCE56', '#E7E9ED', '#36A2EB'] };

    // events on slice click
    public chartClicked(e: any): void {
        console.log(e);
    }

    // event on pie chart slice hover
    public chartHovered(e: any): void {
        console.log(e);
    }
    constructor(
        private incidentService: IncidentService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.incidentService
            .query({})
            .pipe(
                filter((res: HttpResponse<IIncident[]>) => res.ok),
                map((res: HttpResponse<IIncident[]>) => res.body)
            )
            .subscribe((res: IIncident[]) => {
                this.allIncidents = res;
            });
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
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
