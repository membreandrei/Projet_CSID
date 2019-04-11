/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserIncidentAssigmentComponent } from 'app/entities/user-incident-assigment/user-incident-assigment.component';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment/user-incident-assigment.service';
import { UserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

describe('Component Tests', () => {
    describe('UserIncidentAssigment Management Component', () => {
        let comp: UserIncidentAssigmentComponent;
        let fixture: ComponentFixture<UserIncidentAssigmentComponent>;
        let service: UserIncidentAssigmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserIncidentAssigmentComponent],
                providers: []
            })
                .overrideTemplate(UserIncidentAssigmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserIncidentAssigmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserIncidentAssigmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UserIncidentAssigment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.userIncidentAssigments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
