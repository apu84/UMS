/**
 * Created by My Pc on 25-Jan-17.
 */
module ums{
  export interface PaymentInfo{
    id:number;
    receiptId:number;
    studentName:string;
    unit:string;
    semesterId:number;
    semesterName:String;
    paymentType:number;
    paymentDate:string;
    paymentMode:number;
  }
}