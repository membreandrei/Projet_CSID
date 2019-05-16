import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncident } from 'app/shared/model/incident.model';

type EntityResponseType = HttpResponse<IIncident>;
type EntityArrayResponseType = HttpResponse<IIncident[]>;

@Injectable({ providedIn: 'root' })
export class IncidentService {
    public resourceUrl = SERVER_API_URL + 'api/incidents';

    constructor(protected http: HttpClient) {}

    create(incident: IIncident): Observable<EntityResponseType> {
        return this.http.post<IIncident>(this.resourceUrl, incident, { observe: 'response' });
    }

    update(incident: IIncident): Observable<EntityResponseType> {
        return this.http.put<IIncident>(this.resourceUrl, incident, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIncident>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncident[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
