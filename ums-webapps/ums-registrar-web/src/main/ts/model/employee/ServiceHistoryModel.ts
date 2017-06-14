module ums{
    export interface IServiceHistoryModel{
        employeeId: string;
        acrId: string;
        department: IDepartment;
        designation: IDesignation;
        employmentId: IEmploymentType;
        statedOn: string;
        endedOn: string;
    }
}