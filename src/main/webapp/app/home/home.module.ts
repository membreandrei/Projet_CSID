import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProjetCsidSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChartModule } from 'primeng/chart';
import { DialogModule } from 'primeng/dialog';

@NgModule({
    imports: [ProjetCsidSharedModule, BrowserAnimationsModule, ChartModule, DialogModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetCsidHomeModule {}
