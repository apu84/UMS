module ums{
    export interface IServiceInformationModel{
        id: string;
        employeeId: string;
        department: ICommon;
        departmentId: string;
        designation: ICommon;
        designationId: number;
        employmentType: ICommon;
        employmentId: number;
        joiningDate: string;
        resignDate: string;
        dbAction: string;
        intervalDetails: Array<IServiceDetailsModel>;
    }

    export interface IServiceDetailsModel{
        id: number;
        interval: ICommon;
        intervalId: number;
        startDate: string;
        endDate: string;
        serviceId: string;
    }
}