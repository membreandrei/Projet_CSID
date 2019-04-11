/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { UserThirdpartyMembershipDetailComponent } from 'app/entities/user-thirdparty-membership/user-thirdparty-membership-detail.component';
import { UserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

describe('Component Tests', () => {
    describe('UserThirdpartyMembership Management Detail Component', () => {
        let comp: UserThirdpartyMembershipDetailComponent;
        let fixture: ComponentFixture<UserThirdpartyMembershipDetailComponent>;
        const route = ({ data: of({ userThirdpartyMembership: new UserThirdpartyMembership(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [UserThirdpartyMembershipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserThirdpartyMembershipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserThirdpartyMembershipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userThirdpartyMembership).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
