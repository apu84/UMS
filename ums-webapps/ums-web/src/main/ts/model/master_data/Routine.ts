module ums{
  export interface IRoutine{
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
    roomId:number;
  }
}