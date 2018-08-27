module ums{
    export interface IAcademicInformationModel{
        id: string;
        employeeId: string;
        degreeLevel: IDegreeLevel;
        degreeTitle: IDegreeTitle;
        board: string;
        institution: string;
        passingYear: number;
        result: string;
        major: string;
    }

    export interface IDegreeLevel{
        id: number;
        name: string;
    }

    export interface IDegreeTitle {
        id: number;
        title: string;
        degreeLevel: IDegreeLevel;
        degreeLevelId: number;
    }
}