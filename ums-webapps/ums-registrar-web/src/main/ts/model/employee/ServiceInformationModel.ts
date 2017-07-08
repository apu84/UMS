module ums{
    export interface IServiceInformationModel{
        employeeId: string;
        designation: ICommon;
        employmentType: ICommon;
        designationId: number;
        employmentTypeId: number;
        joiningDate: string;
        resignDate: string;
        roomNo: string;
        extNo: string;
        academicInitial: string;
        intervalDetails: Array<IServiceDetailsModel>;
    }

    export interface IServiceDetailsModel{
        interval: ICommon;
        intervalId: number;
        startDate: string;
        endDate: string;
        expEndDate: string;
    }
}