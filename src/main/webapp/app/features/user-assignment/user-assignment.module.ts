import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetCsidSharedModule } from 'app/shared';
import { UserAssignmentComponent } from 'app/features/user-assignment/user-assignment.component';
import { UserAssignmentRoute } from 'app/features/user-assignment/user-assignment.route';
const ENTITY_STATES = [...UserAssignmentRoute];

@NgModule({
    imports: [ProjetCsidSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [UserAssignmentComponent],
    entryComponents: [UserAssignmentComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidUserAssignmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
