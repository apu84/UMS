/**
 * Created by My Pc on 14-May-17.
 */
module ums {

  export enum VisibilityType{
    VISIBLE='Y',
    INVISIBLE='N'
  }

  export interface RemainingLmsLeave {
    leaveTypeId: number;
    leaveName: string;
    daysLeft: string;
    daysLeftNumber: number;
    visibility:VisibilityType;
    viewOrder:number;
  }
}