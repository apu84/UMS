module ums {
  export interface Semester {
    semesterId:number;
    semesterName:string;
    programTypeId: string;
    programTypeName: string;
    semesterTypeId: string;
    semesterTypeName: string;
    year: number;
    statusId: number;
    statusName: string;
    startDate:string;
    endDate:string;
  }
}