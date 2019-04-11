import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    UserIncidentAssigmentComponent,
    UserIncidentAssigmentDetailComponent,
    UserIncidentAssigmentUpdateComponent,
    UserIncidentAssigmentDeletePopupComponent,
    UserIncidentAssigmentDeleteDialogComponent,
    userIncidentAssigmentRoute,
    userIncidentAssigmentPopupRoute
} from './';

const ENTITY_STATES = [...userIncidentAssigmentRoute, ...userIncidentAssigmentPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserIncidentAssigmentComponent,
        UserIncidentAssigmentDetailComponent,
        UserIncidentAssigmentUpdateComponent,
        UserIncidentAssigmentDeleteDialogComponent,
        UserIncidentAssigmentDeletePopupComponent
    ],
    entryComponents: [
        UserIncidentAssigmentComponent,
        UserIncidentAssigmentUpdateComponent,
        UserIncidentAssigmentDeleteDialogComponent,
        UserIncidentAssigmentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidUserIncidentAssigmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
