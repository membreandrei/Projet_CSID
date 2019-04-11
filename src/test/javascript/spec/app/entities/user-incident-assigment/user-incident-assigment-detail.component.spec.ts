/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserIncidentAssigmentDetailComponent } from 'app/entities/user-incident-assigment/user-incident-assigment-detail.component';
import { UserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

describe('Component Tests', () => {
    describe('UserIncidentAssigment Management Detail Component', () => {
        let comp: UserIncidentAssigmentDetailComponent;
        let fixture: ComponentFixture<UserIncidentAssigmentDetailComponent>;
        const route = ({ data: of({ userIncidentAssigment: new UserIncidentAssigment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserIncidentAssigmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserIncidentAssigmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserIncidentAssigmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userIncidentAssigment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
