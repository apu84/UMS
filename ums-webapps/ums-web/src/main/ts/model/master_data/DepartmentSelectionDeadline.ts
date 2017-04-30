module ums{
  export interface DepartmentSelectionDeadline{
    id:number;
    semesterId:number;
    semesterName:string;
    unit:string;
    meritSerialNumberFrom: number;
    meritSerialNumberTo: number;
    deadline: string;
  }
}