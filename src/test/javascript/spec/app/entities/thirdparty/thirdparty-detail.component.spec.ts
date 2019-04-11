/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { ThirdpartyDetailComponent } from 'app/entities/thirdparty/thirdparty-detail.component';
import { Thirdparty } from 'app/shared/model/thirdparty.model';

describe('Component Tests', () => {
    describe('Thirdparty Management Detail Component', () => {
        let comp: ThirdpartyDetailComponent;
        let fixture: ComponentFixture<ThirdpartyDetailComponent>;
        const route = ({ data: of({ thirdparty: new Thirdparty(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [ThirdpartyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ThirdpartyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThirdpartyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.thirdparty).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
