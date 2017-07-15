module ums{
    export interface IServiceInformationModel{
        id: number;
        employeeId: string;
        department: ICommon;
        departmentId: string;
        designation: ICommon;
        designationId: number;
        employmentType: ICommon;
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
    }
}