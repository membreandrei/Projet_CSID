import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    IncidentComponent,
    IncidentDetailComponent,
    IncidentUpdateComponent,
    IncidentDeletePopupComponent,
    IncidentDeleteDialogComponent,
    incidentRoute,
    incidentPopupRoute
} from './';

const ENTITY_STATES = [...incidentRoute, ...incidentPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncidentComponent,
        IncidentDetailComponent,
        IncidentUpdateComponent,
        IncidentDeleteDialogComponent,
        IncidentDeletePopupComponent
    ],
    entryComponents: [IncidentComponent, IncidentUpdateComponent, IncidentDeleteDialogComponent, IncidentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidIncidentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
