module ums {
  export interface Student {
    fullName:string;
    id:string;
    programTypeId: string;
    programTypeName: string;
    deptId: string;
    department: string;
    departmentName:string;
    programName: string;
    programId: string;
    semesterId: string;
    semesterName: string;
    year: number;
    academicSemester: number;
    fatherName: string;
    motherName: string;
    dateOfBirth: string;
    gender: string;
    mobileNo: string;
    phoneNo: string;
    bloodGroup: string;
    email: string;
    presentAddress: string;
    permanentAddress: string;
    guardianPhone: string;
    guardianName: string;
    guardianEmail: string;
    guardianMobile: string;
    theorySection:string;
    sessionalSection:string;
  }
}