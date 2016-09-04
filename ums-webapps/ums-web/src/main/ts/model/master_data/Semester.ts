module ums {
  export interface Semester {
    semesterId:number;
    id:number;
    name:string;
    status:number;
    semesterName:string;
    programTypeId: string;
    programTypeName: string;
    semesterTypeId: string;
    semesterTypeName: string;
    year: any;
    statusId: number;
    statusName: string;
    startDate:string;
    endDate:string;
  }
}