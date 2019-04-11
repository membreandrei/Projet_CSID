import { IThirdparty } from 'app/shared/model/thirdparty.model';
import { IUserApp } from 'app/shared/model/user-app.model';

export interface ILicence {
    id?: number;
    titre?: string;
    prix?: number;
    thirdparty?: IThirdparty;
    userLicence?: IUserApp;
}

export class Licence implements ILicence {
    constructor(
        public id?: number,
        public titre?: string,
        public prix?: number,
        public thirdparty?: IThirdparty,
        public userLicence?: IUserApp
    ) {}
}
