import { IUserIncidentAssigment } from 'app/shared/model/user-incident-assigment.model';
import { IIncident } from 'app/shared/model/incident.model';
import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { IUser } from 'app/core/user/user.model';

export interface IUserApp {
    id?: number;
    assigmentUsers?: IUserIncidentAssigment[];
    incidentClients?: IIncident[];
    userThirdpartyMembership?: IUserThirdpartyMembership;
    user?: IUser;
}

export class UserApp implements IUserApp {
    constructor(
        public id?: number,
        public assigmentUsers?: IUserIncidentAssigment[],
        public incidentClients?: IIncident[],
        public userThirdpartyMembership?: IUserThirdpartyMembership,
        public user?: IUser
    ) {}
}
