module ums{
    export interface IServiceInformationModel{
        id: string;
        employeeId: string;
        department: ICommon;
        designation: ICommon;
        employmentType: ICommon;
        joiningDate: string;
        resignDate: string;
        dbAction: string;
        intervalDetails: Array<IServiceDetailsModel>;
    }

    export interface IServiceDetailsModel{
        id: number;
        interval: ICommon;
        startDate: string;
        endDate: string;
        serviceId: string;
        dbAction: string;
    }
}