module ums{
    export interface IAcademicInformationModel{
        id: number;
        employeeId: string;
        degree: ICommon;
        degreeId: number
        institution: string;
        passingYear: number;
        dbAction: string;
    }
}