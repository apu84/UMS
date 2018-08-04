module ums{
  export interface Employee{
    id:string;
    employeeName:string;
    text?: string;
    designation:number;
    designationName?:string;
    employmentType:string;
    employeeType:number;
    deptOfficeId:string;
    deptOfficeName?: string;
    fatherName:string;
    motherName:string;
    birthDate:string;
    gender:string;
    bloodGroup:string;
    presentAddress:string;
    permanentAddress:string;
    mobileNumber:string;
    phoneNumber:string;
    emailAddress:string;
    shortName: string;
    joiningDate:string;
    jobPermanentDate:string;
    status:number;
  }

    export interface INewEmployee {
        id: string;
        name: string;
        department: any;
        designation: any;
        employmentType: any;
        joiningDate: string;
        status: any;
        shortName: string;
        email: string;
        employeeType: any;
        role: any;
        endDate: string;
        IUMSAccount: boolean;
    }

}
