/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */
module ums {
  export interface LmsApplication {
    id: string;
    employeeId: string;
    employeeName: string;
    leaveType: number;
    leaveTypeName: string;
    appliedOn: string;
    fromDate: string;
    toDate: string;
    duration: number;
    reason: string;
    applicationStatus: number;
  }
}