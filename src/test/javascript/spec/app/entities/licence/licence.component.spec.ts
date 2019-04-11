/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProjetCsidTestModule } from '../../../test.module';
import { LicenceComponent } from 'app/entities/licence/licence.component';
import { LicenceService } from 'app/entities/licence/licence.service';
import { Licence } from 'app/shared/model/licence.model';

describe('Component Tests', () => {
    describe('Licence Management Component', () => {
        let comp: LicenceComponent;
        let fixture: ComponentFixture<LicenceComponent>;
        let service: LicenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [LicenceComponent],
                providers: []
            })
                .overrideTemplate(LicenceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LicenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Licence(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.licences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
