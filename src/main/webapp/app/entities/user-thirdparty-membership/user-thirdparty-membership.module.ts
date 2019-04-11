import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    UserThirdpartyMembershipComponent,
    UserThirdpartyMembershipDetailComponent,
    UserThirdpartyMembershipUpdateComponent,
    UserThirdpartyMembershipDeletePopupComponent,
    UserThirdpartyMembershipDeleteDialogComponent,
    userThirdpartyMembershipRoute,
    userThirdpartyMembershipPopupRoute
} from './';

const ENTITY_STATES = [...userThirdpartyMembershipRoute, ...userThirdpartyMembershipPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserThirdpartyMembershipComponent,
        UserThirdpartyMembershipDetailComponent,
        UserThirdpartyMembershipUpdateComponent,
        UserThirdpartyMembershipDeleteDialogComponent,
        UserThirdpartyMembershipDeletePopupComponent
    ],
    entryComponents: [
        UserThirdpartyMembershipComponent,
        UserThirdpartyMembershipUpdateComponent,
        UserThirdpartyMembershipDeleteDialogComponent,
        UserThirdpartyMembershipDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidUserThirdpartyMembershipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
