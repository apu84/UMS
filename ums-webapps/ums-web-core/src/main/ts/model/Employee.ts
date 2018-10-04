module ums {
    export interface Employee {
        id: string;
        employeeName: string;
        name: string;
        text?: string;
        designation: number;
        designationObj: IDesignation;
        designationName?: string;
        employmentType: string;
        employeeType: number;
        departmentObj: IDepartment;
        deptOfficeId: string;
        deptOfficeName?: string;
        fatherName: string;
        motherName: string;
        birthDate: string;
        gender: string;
        bloodGroup: string;
        presentAddress: string;
        permanentAddress: string;
        mobileNumber: string;
        phoneNumber: string;
        emailAddress: string;
        shortName: string;
        joiningDate: string;
        jobPermanentDate: string;
        status: number;
        statusName: string;
    }

    export interface INewEmployee {
        id: string;
        email: string;
        salutation: ICommon;
        employeeName: string;
        department: IDepartment;
        employeeType: ICommon;
        designation: IDesignation;
        employmentType: ICommon;
        joiningDate: string;
        createAccount: boolean;
        requestedBy: string;
        requestedOn: string;
        actionTakenBy: string;
        actionTakenOn: string;
        actionStatus: number;
        academicInitial: string;
    }
}
