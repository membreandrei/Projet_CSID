/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetCsidTestModule } from '../../../test.module';
import { ThirdpartyDeleteDialogComponent } from 'app/entities/thirdparty/thirdparty-delete-dialog.component';
import { ThirdpartyService } from 'app/entities/thirdparty/thirdparty.service';

describe('Component Tests', () => {
    describe('Thirdparty Management Delete Component', () => {
        let comp: ThirdpartyDeleteDialogComponent;
        let fixture: ComponentFixture<ThirdpartyDeleteDialogComponent>;
        let service: ThirdpartyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [ThirdpartyDeleteDialogComponent]
            })
                .overrideTemplate(ThirdpartyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThirdpartyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdpartyService);
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
