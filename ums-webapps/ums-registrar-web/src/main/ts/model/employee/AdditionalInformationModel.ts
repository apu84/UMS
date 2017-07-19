module ums{
    export interface IAdditionalInformationModel{
        employeeId: string;
        roomNo: string;
        extNo: number;
        academicInitial: string;
        areaOfInterestInformation: Array<IAreaOfInterestInformationModel>;
    }
}