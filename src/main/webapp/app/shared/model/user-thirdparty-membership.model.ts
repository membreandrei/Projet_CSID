import { IUserApp } from 'app/shared/model/user-app.model';
import { IThirdparty } from 'app/shared/model/thirdparty.model';

export interface IUserThirdpartyMembership {
    id?: number;
    fonction?: string;
    specialite?: string;
    userMemberShips?: IUserApp[];
    thirdparty?: IThirdparty;
}

export class UserThirdpartyMembership implements IUserThirdpartyMembership {
    constructor(
        public id?: number,
        public fonction?: string,
        public specialite?: string,
        public userMemberShips?: IUserApp[],
        public thirdparty?: IThirdparty
    ) {}
}
