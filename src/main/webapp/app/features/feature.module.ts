import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProjetCsidIncidentModule } from 'app/features/incident/incident.module';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'ticket',
                loadChildren: './incident/incident.module#ProjetCsidIncidentModule'
            },
            {
                path: 'portailAdministration/mesUtilisateurs',
                loadChildren: './administration/administration.module#ProjetCsidAdministrationModule'
            },
            {
                path: 'portailAdministration/memberShip',
                loadChildren: './member-ship/member-ship.module#ProjetCsidMembershipModule'
            },
            {
                path: 'assignement',
                loadChildren: './user-assignment/user-assignment.module#ProjetCsidUserAssignmentModule'
            }
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidFeatureModule {}
