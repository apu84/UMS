module ums{
  export interface ISemesterWithdrawLog{
    id:number;
    semesterWithdrawId:number;
    actor:number;
    actorId:string;
    action:number;
    comments:string;
    eventDateTime:string;
  }
}