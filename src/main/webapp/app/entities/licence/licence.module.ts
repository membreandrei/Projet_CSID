import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    LicenceComponent,
    LicenceDetailComponent,
    LicenceUpdateComponent,
    LicenceDeletePopupComponent,
    LicenceDeleteDialogComponent,
    licenceRoute,
    licencePopupRoute
} from './';

const ENTITY_STATES = [...licenceRoute, ...licencePopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LicenceComponent,
        LicenceDetailComponent,
        LicenceUpdateComponent,
        LicenceDeleteDialogComponent,
        LicenceDeletePopupComponent
    ],
    entryComponents: [LicenceComponent, LicenceUpdateComponent, LicenceDeleteDialogComponent, LicenceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidLicenceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
