module ums{
    export interface IExperienceInformationModel{
        id: string;
        employeeId: string;
        experienceInstitution: string;
        experienceDesignation: string;
        experienceFrom: string;
        experienceTo: string;
        experienceDuration: number;
        experienceDurationString: string;
        experienceCategory: ICommon;
    }
}