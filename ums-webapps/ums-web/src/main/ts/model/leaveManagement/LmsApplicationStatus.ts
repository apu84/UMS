/**
 * Created by My Pc on 20-May-17.
 */
module ums {
  export interface  LmsApplicationStatus {
    id: string;
    appId: string;
    reason: string;
    appliedOn: string;
    actionTakenOn: string;
    actionTakenBy: string;
    actionTakenByName: string;
    comments: string;
    actionStatus: number;
    actionStatusLabel: string;
  }
}