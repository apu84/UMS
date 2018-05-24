module ums{
    export interface IProgramType{
        id: number;
        name: string;
        lastModified: string;
    }

    export class ProgramTypeService{

    }

    UMS.service("ProgramTypeService", ProgramTypeService);
}