/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserThirdpartyMembershipComponent } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership.component';
import { UserThirdpartyMembershipService } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership.service';
import { UserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

describe('Component Tests', () => {
    describe('UserThirdpartyMembership Management Component', () => {
        let comp: UserThirdpartyMembershipComponent;
        let fixture: ComponentFixture<UserThirdpartyMembershipComponent>;
        let service: UserThirdpartyMembershipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserThirdpartyMembershipComponent],
                providers: []
            })
                .overrideTemplate(UserThirdpartyMembershipComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserThirdpartyMembershipComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserThirdpartyMembershipService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UserThirdpartyMembership(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.userThirdpartyMemberships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
