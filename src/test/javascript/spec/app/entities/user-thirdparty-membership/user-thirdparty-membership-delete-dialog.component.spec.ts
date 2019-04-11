/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserThirdpartyMembershipDeleteDialogComponent } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership-delete-dialog.component';
import { UserThirdpartyMembershipService } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership.service';

describe('Component Tests', () => {
    describe('UserThirdpartyMembership Management Delete Component', () => {
        let comp: UserThirdpartyMembershipDeleteDialogComponent;
        let fixture: ComponentFixture<UserThirdpartyMembershipDeleteDialogComponent>;
        let service: UserThirdpartyMembershipService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserThirdpartyMembershipDeleteDialogComponent]
            })
                .overrideTemplate(UserThirdpartyMembershipDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserThirdpartyMembershipDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserThirdpartyMembershipService);
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
