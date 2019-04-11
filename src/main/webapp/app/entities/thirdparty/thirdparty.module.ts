import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import {
    ThirdpartyComponent,
    ThirdpartyDetailComponent,
    ThirdpartyUpdateComponent,
    ThirdpartyDeletePopupComponent,
    ThirdpartyDeleteDialogComponent,
    thirdpartyRoute,
    thirdpartyPopupRoute
} from './';

const ENTITY_STATES = [...thirdpartyRoute, ...thirdpartyPopupRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ThirdpartyComponent,
        ThirdpartyDetailComponent,
        ThirdpartyUpdateComponent,
        ThirdpartyDeleteDialogComponent,
        ThirdpartyDeletePopupComponent
    ],
    entryComponents: [ThirdpartyComponent, ThirdpartyUpdateComponent, ThirdpartyDeleteDialogComponent, ThirdpartyDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidThirdpartyModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
