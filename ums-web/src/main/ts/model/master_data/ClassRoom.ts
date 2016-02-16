module ums {
  export interface ClassRoom {
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
