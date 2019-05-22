import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';

type EntityResponseType = HttpResponse<IUserThirdpartyMembership>;
type EntityArrayResponseType = HttpResponse<IUserThirdpartyMembership[]>;

@Injectable({ providedIn: 'root' })
export class MembershipService {
    public resourceUrl = SERVER_API_URL + 'api/user-thirdparty-memberships';

    constructor(protected http: HttpClient) {}

    create(userThirdpartyMembership: IUserThirdpartyMembership): Observable<EntityResponseType> {
        return this.http.post<IUserThirdpartyMembership>(this.resourceUrl, userThirdpartyMembership, { observe: 'response' });
    }

    update(userThirdpartyMembership: IUserThirdpartyMembership): Observable<EntityResponseType> {
        return this.http.put<IUserThirdpartyMembership>(this.resourceUrl, userThirdpartyMembership, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserThirdpartyMembership>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserThirdpartyMembership[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
