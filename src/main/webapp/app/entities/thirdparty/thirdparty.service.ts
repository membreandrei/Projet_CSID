import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IThirdparty } from 'app/shared/model/thirdparty.model';

type EntityResponseType = HttpResponse<IThirdparty>;
type EntityArrayResponseType = HttpResponse<IThirdparty[]>;

@Injectable({ providedIn: 'root' })
export class ThirdpartyService {
    public resourceUrl = SERVER_API_URL + 'api/thirdparties';

    constructor(protected http: HttpClient) {}

    create(thirdparty: IThirdparty): Observable<EntityResponseType> {
        return this.http.post<IThirdparty>(this.resourceUrl, thirdparty, { observe: 'response' });
    }

    update(thirdparty: IThirdparty): Observable<EntityResponseType> {
        return this.http.put<IThirdparty>(this.resourceUrl, thirdparty, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IThirdparty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IThirdparty[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
