module ums{
    export interface IAcademicInformationModel{
        id: number;
        employeeId: string;
        degree: ICommon;
        institution: string;
        passingYear: number;
        dbAction: string;
    }
}