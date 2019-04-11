/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserIncidentAssigmentUpdateComponent } from 'app/entities/user-incident-assigment/user-incident-assigment-update.component';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment/user-incident-assigment.service';
import { UserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

describe('Component Tests', () => {
    describe('UserIncidentAssigment Management Update Component', () => {
        let comp: UserIncidentAssigmentUpdateComponent;
        let fixture: ComponentFixture<UserIncidentAssigmentUpdateComponent>;
        let service: UserIncidentAssigmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserIncidentAssigmentUpdateComponent]
            })
                .overrideTemplate(UserIncidentAssigmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserIncidentAssigmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserIncidentAssigmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserIncidentAssigment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userIncidentAssigment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserIncidentAssigment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userIncidentAssigment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
