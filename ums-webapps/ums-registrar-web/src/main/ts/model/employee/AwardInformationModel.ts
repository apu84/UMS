module ums{
    export interface IAwardInformationModel{
        id: number;
        employeeId: string;
        awardName: string;
        awardInstitute: string;
        awardedYear: string;
        awardShortDescription: string;
    }
}