/**
 * Created by My Pc on 20-May-17.
 */
module ums {
  export interface  LmsApplicationStatus {
    id: string;
    leaveApplication: LmsApplication;
    appId: string;
    applicantsId: string;
    applicantsName: string;
    deptName: string;
    reason: string;
    appliedOn: string;
    fromDate: string;
    toDate: string;
    duration: string;
    leaveTypeName: string;
    applicationStatus: number;
    applicationStatusLabel: string;
    actionTakenOn: string;
    actionTakenBy: string;
    actionTakenByName: string;
    rowNumber: number;
    comments: string;
    actionStatus: number;
    actionStatusLabel: string;
  }
}