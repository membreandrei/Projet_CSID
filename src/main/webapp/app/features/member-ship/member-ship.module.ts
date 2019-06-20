import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import { MemberShipComponent } from 'app/features/member-ship/member-ship.component';
import { MemberShipDetailComponent } from 'app/features/member-ship/member-ship-detail.component';
import { MemberShipUpdateComponent } from 'app/features/member-ship/member-ship-update.component';
import {
    MemberShipDeleteDialogComponent,
    MembershipDeletePopupComponent
} from 'app/features/member-ship/member-ship-delete-dialog.component';
import { MembershipPopupRoute, MembershipRoute } from 'app/features/member-ship/member-ship.route';
const ENTITY_STATES = [...MembershipRoute, ...MembershipPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MemberShipComponent,
        MemberShipDetailComponent,
        MemberShipUpdateComponent,
        MemberShipDeleteDialogComponent,
        MembershipDeletePopupComponent
    ],
    entryComponents: [MemberShipComponent, MemberShipUpdateComponent, MemberShipDeleteDialogComponent, MembershipDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidMembershipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
