module ums{
    export interface IServiceInformationModel{
        id: string;
        employeeId: string;
        department: ICommon;
        designation: ICommon;
        employmentType: ICommon;
        joiningDate: string;
        resignDate: string;
        intervalDetails: IServiceDetailsModel[];
    }

    export interface IServiceDetailsModel{
        id: string;
        interval: ICommon;
        startDate: string;
        endDate: string;
        comment: string;
        serviceId: string;
    }

    export interface IDepartment {
        id: string;
        shortName: string;
        longName: string;
        type: number;
    }

    export interface IDesignation {
        id: number;
        name: string;
        employeeType: ICommon;
    }
}