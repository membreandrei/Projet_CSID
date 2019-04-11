/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProjetCsidTestModule } from '../../../test.module';
import { ThirdpartyComponent } from 'app/entities/thirdparty/thirdparty.component';
import { ThirdpartyService } from 'app/entities/thirdparty/thirdparty.service';
import { Thirdparty } from 'app/shared/model/thirdparty.model';

describe('Component Tests', () => {
    describe('Thirdparty Management Component', () => {
        let comp: ThirdpartyComponent;
        let fixture: ComponentFixture<ThirdpartyComponent>;
        let service: ThirdpartyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [ThirdpartyComponent],
                providers: []
            })
                .overrideTemplate(ThirdpartyComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThirdpartyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdpartyService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Thirdparty(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.thirdparties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
