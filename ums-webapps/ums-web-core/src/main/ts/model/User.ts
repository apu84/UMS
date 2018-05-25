module ums {
  export interface User {
    userName: string;
    firstName: string;
    lastName: string;
    roleId: number;
    employeeId: string;
    departmentId: string;
    name: string;
    id: string;
  }

  export interface UserView{
    userId: string;
    userName: string;
    gender: string;
    dateOfBirth: string;
    bloodGroup: string;
    fatherName: string;
    motherName: string;
    mobileNumber: string;
    emailAddress: string;
    department: string;
    designation: string;
    category: string;
    loginId: string;
  }
}