/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */
module ums {
  export interface LmsApplication {
    id: string;
    employeeId: string;
    employeeName: string;
    employee: Employee;
    leaveType: number;
    leaveTypeName: string;
    appliedOn: string;
    fromDate: string;
    toDate: string;
    duration: number;
    reason: string;
    applicationStatus: number;
    submittedBy: string;
    submittedByEmp: Employee;
  }
}