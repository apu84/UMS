module ums{
    export interface IAcademicInformationModel{
        id: number;
        employeeId: string;
        academicDegreeName: ICommon;
        academicInstitution: string;
        academicPassingYear: string;
        dbAction: string;
    }
}