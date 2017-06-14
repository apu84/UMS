module ums{
    export interface IServiceInformationModel{
        employeeId: string;
        designation: IDesignation;
        department: IDepartment;
        academicInitial: string;
        roomNo: IRoomNo;
        extNo: IExtNo;
        areaOfInterest: IAreaOfInterest;
        employmentType: IEmploymentType;
        contractualStartDate: string;
        contractualEndDate: string;
        probationStartDate: string;
        probationEndDate: string;
        permanentStartDate: string;
        resignDate: string;
        currentStatus: string;
    }
}