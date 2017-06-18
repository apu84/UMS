module ums {
  export interface Holidays {
    id: string;
    holidayTypeId: string;
    holidayTypeName: string;
    moonDependency: number;
    year: number;
    fromDate: string;
    toDate: string;
    duration: number;
  }
}