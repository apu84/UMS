module ums{
  export interface ISemesterWithdraw{
    id:number;
    semesterId:number;
    programId:number;
    cause:string;
    studentId:string;
    year:number;
    semester:number;
    status:number;
  }
}