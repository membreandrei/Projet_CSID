/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetCsidTestModule } from '../../../test.module';
import { ThirdpartyUpdateComponent } from 'app/entities/thirdparty/thirdparty-update.component';
import { ThirdpartyService } from 'app/entities/thirdparty/thirdparty.service';
import { Thirdparty } from 'app/shared/model/thirdparty.model';

describe('Component Tests', () => {
    describe('Thirdparty Management Update Component', () => {
        let comp: ThirdpartyUpdateComponent;
        let fixture: ComponentFixture<ThirdpartyUpdateComponent>;
        let service: ThirdpartyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetCsidTestModule],
                declarations: [ThirdpartyUpdateComponent]
            })
                .overrideTemplate(ThirdpartyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThirdpartyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdpartyService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Thirdparty(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.thirdparty = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Thirdparty();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.thirdparty = entity;
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
