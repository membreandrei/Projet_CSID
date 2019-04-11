import { IUserApp } from 'app/shared/model/user-app.model';
import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';

export interface IIncident {
    id?: number;
    titre?: string;
    statut?: string;
    priorite?: string;
    sujet?: string;
    categorie?: string;
    description?: string;
    dateDebut?: string;
    dateFin?: string;
    userApp?: IUserApp;
    assigmentIncidents?: IUserIncidentAssigment[];
}

export class Incident implements IIncident {
    constructor(
        public id?: number,
        public titre?: string,
        public statut?: string,
        public priorite?: string,
        public sujet?: string,
        public categorie?: string,
        public description?: string,
        public dateDebut?: string,
        public dateFin?: string,
        public userApp?: IUserApp,
        public assigmentIncidents?: IUserIncidentAssigment[]
    ) {}
}
