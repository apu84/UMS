module ums{
  export interface Routine{
    id:string;
    semesterId:number;
    programId:number;
    courseId:string;
    section:string;
    day:number;
    academicYear:number;
    academicSemester:number;
    startTime:string;
    endTime:string;
    duration:number;
    roomNo:string;
  }
}