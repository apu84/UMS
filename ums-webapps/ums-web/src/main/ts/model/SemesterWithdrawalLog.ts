module ums{
  export interface ISemesterWithdrawLog{
    id:number;
    semesterWithdrawId:number;
    employeeId:string;
    action:number;
    comments:string;
    eventDateTime:string;
  }
}