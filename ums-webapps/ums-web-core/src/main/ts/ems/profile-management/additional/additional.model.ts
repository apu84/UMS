module ums{
    export interface IAdditionalInformationModel{
        employeeId: string;
        roomNo: string;
        extNo: string;
        academicInitial: string;
    }

    export interface IAreaOfInterestInformationModel {
        employeeId: string;
        areaOfInterest: string;
    }
}