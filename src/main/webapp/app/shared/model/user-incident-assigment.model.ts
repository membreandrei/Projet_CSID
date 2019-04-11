import { IUserApp } from 'app/shared/model/user-app.model';
import { IIncident } from 'app/shared/model/incident.model';

export interface IUserIncidentAssigment {
    id?: number;
    dateDebut?: string;
    dateFin?: string;
    commentaire?: string;
    status?: string;
    userApp?: IUserApp;
    incident?: IIncident;
}

export class UserIncidentAssigment implements IUserIncidentAssigment {
    constructor(
        public id?: number,
        public dateDebut?: string,
        public dateFin?: string,
        public commentaire?: string,
        public status?: string,
        public userApp?: IUserApp,
        public incident?: IIncident
    ) {}
}
