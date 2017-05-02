module ums{
  export interface DepartmentSelectionDeadline{
    id:number;
    semesterId:number;
    semesterName:string;
    unit:string;
    meritSerialNumberFrom: string;
    meritSerialNumberTo: string;
    disable:boolean;
    deadline: string;
  }
}