/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserThirdpartyMembershipUpdateComponent } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership-update.component';
import { UserThirdpartyMembershipService } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership.service';
import { UserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

describe('Component Tests', () => {
    describe('UserThirdpartyMembership Management Update Component', () => {
        let comp: UserThirdpartyMembershipUpdateComponent;
        let fixture: ComponentFixture<UserThirdpartyMembershipUpdateComponent>;
        let service: UserThirdpartyMembershipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserThirdpartyMembershipUpdateComponent]
            })
                .overrideTemplate(UserThirdpartyMembershipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserThirdpartyMembershipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserThirdpartyMembershipService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserThirdpartyMembership(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userThirdpartyMembership = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserThirdpartyMembership();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userThirdpartyMembership = entity;
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
