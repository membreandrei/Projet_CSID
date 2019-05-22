import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetCsidSharedModule } from 'app/shared';
import { MemberShipComponent } from 'app/member-ship/member-ship.component';
import { ADMINISTRATION_ROUTE } from './member-ship.route';

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forRoot([ADMINISTRATION_ROUTE])],
    entryComponents: [],
    declarations: [MemberShipComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MemberShipModule {}
