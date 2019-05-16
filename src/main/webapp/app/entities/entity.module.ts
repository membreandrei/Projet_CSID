import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'user-app',
                loadChildren: './user-app/user-app.module#ProjetCsidUserAppModule'
            },
            {
                path: 'user-thirdparty-membership',
                loadChildren: './user-thirdparty-membership/user-thirdparty-membership.module#ProjetCsidUserThirdpartyMembershipModule'
            },
            {
                path: 'thirdparty',
                loadChildren: './thirdparty/thirdparty.module#ProjetCsidThirdpartyModule'
            },
            {
                path: 'licence',
                loadChildren: './licence/licence.module#ProjetCsidLicenceModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'user-incident-assigment',
                loadChildren: './user-incident-assigment/user-incident-assigment.module#ProjetCsidUserIncidentAssigmentModule'
            },
            {
                path: 'user-app',
                loadChildren: './user-app/user-app.module#ProjetCsidUserAppModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'user-app',
                loadChildren: './user-app/user-app.module#ProjetCsidUserAppModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidEntityModule {}
