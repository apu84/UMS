module ums{
    export interface IServiceBasicInformationModel{
        employeeId: string;
        academicInitial: string;
        roomNo: string;
        extNo: string;
        joiningDate: string;
        employmentType: IEmploymentType;
        resignDate: string;
    }

    export interface IContractModel{
        designation: IDesignation;
        department: IDepartment;
        contractStartDate: string;
        contractEndDate: string;
        currentStatus: string;
    }

    export interface IContractualModel{
        designation: IDesignation;
        department: IDepartment;
        contractualStartDate: string;
        contractualEndDate: string;
        currentStatus: string;
    }

    export interface IProbationModel{
        designation: IDesignation;
        department: IDepartment;
        probationStartDate: string;
        probationEndDate: string;
        currentStatus: string;
    }

    export interface IPermanentModel{
        designation: IDesignation;
        department: IDepartment;
        permanentStartDate: string;
        permanentEndDate: string;
        currentStatus: string;
    }
}