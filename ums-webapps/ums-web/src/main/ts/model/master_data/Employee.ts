module ums{
  export interface Employee{
    id:string;
    employeeName:string;
    text?: string;
    designation:number;
    designationName?:string;
    employmentType:string;
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
    joiningDate:string;
    jobPermanentDate:string;
    status:number;
  }

    export interface INewEmployee {
        id: string;
        firstName: string;
        lastName: string;
        department: object;
        designation: object;
        employmentType: object;
        joiningDate: string;
        status: object;
        shortName: string;
        email: string;
        employeeType: object;
        role: object;
        IUMSAccount: boolean;
    }

}