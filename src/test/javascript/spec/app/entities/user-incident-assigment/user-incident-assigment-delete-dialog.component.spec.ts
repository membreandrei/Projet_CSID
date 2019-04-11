/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserIncidentAssigmentDeleteDialogComponent } from 'app/entities/user-incident-assigment/user-incident-assigment-delete-dialog.component';
import { UserIncidentAssigmentService } from 'app/entities/user-incident-assigment/user-incident-assigment.service';

describe('Component Tests', () => {
    describe('UserIncidentAssigment Management Delete Component', () => {
        let comp: UserIncidentAssigmentDeleteDialogComponent;
        let fixture: ComponentFixture<UserIncidentAssigmentDeleteDialogComponent>;
        let service: UserIncidentAssigmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserIncidentAssigmentDeleteDialogComponent]
            })
                .overrideTemplate(UserIncidentAssigmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserIncidentAssigmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserIncidentAssigmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
