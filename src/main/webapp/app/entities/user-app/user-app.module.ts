import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    UserAppComponent,
    UserAppDetailComponent,
    UserAppUpdateComponent,
    UserAppDeletePopupComponent,
    UserAppDeleteDialogComponent,
    userAppRoute,
    userAppPopupRoute
} from './';

const ENTITY_STATES = [...userAppRoute, ...userAppPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserAppComponent,
        UserAppDetailComponent,
        UserAppUpdateComponent,
        UserAppDeleteDialogComponent,
        UserAppDeletePopupComponent
    ],
    entryComponents: [UserAppComponent, UserAppUpdateComponent, UserAppDeleteDialogComponent, UserAppDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidUserAppModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
