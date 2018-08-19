module ums{
    export interface IAwardInformationModel{
        id: string;
        employeeId: string;
        awardName: string;
        awardInstitute: string;
        awardedYear: number;
        awardShortDescription: string;
    }
}