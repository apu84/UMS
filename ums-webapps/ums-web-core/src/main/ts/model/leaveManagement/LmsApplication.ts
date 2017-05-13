/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */
module ums {
  export interface LmsApplication {
    id: number;
    employeeId: string;
    employeeName: string;
    fromDate: string;
    toDate: string;
    reason: string;
  }
}