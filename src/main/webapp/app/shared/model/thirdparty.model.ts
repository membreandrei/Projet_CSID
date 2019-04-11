import { IUserThirdpartyMembership } from 'app/shared/model/user-thirdparty-membership.model';
import { ILicence } from 'app/shared/model/licence.model';

export interface IThirdparty {
    id?: number;
    denomination?: string;
    siret?: number;
    thirdpartyMemberShips?: IUserThirdpartyMembership[];
    thirdpartyLicences?: ILicence[];
}

export class Thirdparty implements IThirdparty {
    constructor(
        public id?: number,
        public denomination?: string,
        public siret?: number,
        public thirdpartyMemberShips?: IUserThirdpartyMembership[],
        public thirdpartyLicences?: ILicence[]
    ) {}
}
