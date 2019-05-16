import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetCsidSharedModule } from 'app/shared';
import { AdministrationComponent } from 'app/administration/administration.component';
import { ADMINISTRATION_ROUTE } from './administration.route';

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forRoot([ADMINISTRATION_ROUTE])],
    entryComponents: [],
    declarations: [AdministrationComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdministrationModule {}
