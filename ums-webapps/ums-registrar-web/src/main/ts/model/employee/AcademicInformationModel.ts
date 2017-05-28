module ums{
    export interface IAcademicInformationModel{
        id: number;
        employeeId: string;
        academicDegreeName: IDegreeType;
        academicInstitution: string;
        academicPassingYear: string;
    }
}