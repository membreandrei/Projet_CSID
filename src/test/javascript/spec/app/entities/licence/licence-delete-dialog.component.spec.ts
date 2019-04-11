/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetCsidTestModule } from '../../../test.module';
import { LicenceDeleteDialogComponent } from 'app/entities/licence/licence-delete-dialog.component';
import { LicenceService } from 'app/entities/licence/licence.service';

describe('Component Tests', () => {
    describe('Licence Management Delete Component', () => {
        let comp: LicenceDeleteDialogComponent;
        let fixture: ComponentFixture<LicenceDeleteDialogComponent>;
        let service: LicenceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [LicenceDeleteDialogComponent]
            })
                .overrideTemplate(LicenceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LicenceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicenceService);
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
