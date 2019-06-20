import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import { AdministrationComponent } from 'app/features/administration/administration.component';
import { AdministrationDetailComponent } from 'app/features/administration/administration-detail.component';
import { AdministrationUpdateComponent } from 'app/features/administration/administration-update.component';
import {
    AdministrationDeleteDialogComponent,
    AdministrationDeletePopupComponent
} from 'app/features/administration/administration-delete-dialog.component';
import { AdministrationAppRoute, AdministrationPopupRoute } from 'app/features/administration/administration.route';

const ENTITY_STATES = [...AdministrationAppRoute, ...AdministrationPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdministrationComponent,
        AdministrationDetailComponent,
        AdministrationUpdateComponent,
        AdministrationDeleteDialogComponent,
        AdministrationDeletePopupComponent
    ],
    entryComponents: [
        AdministrationComponent,
        AdministrationUpdateComponent,
        AdministrationDeleteDialogComponent,
        AdministrationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidAdministrationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
