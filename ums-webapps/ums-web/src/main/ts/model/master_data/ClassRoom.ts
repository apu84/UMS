module ums {
  export interface ClassRoom {
    id:number;
    roomId:number;
    roomNumber:string;
    description: string;
    totalRow: number;
    totalColumn: number;
    totalCapacity: number;
    deptId: string;
    roomType: number;
    examSeatPlan: string;
  }
}
