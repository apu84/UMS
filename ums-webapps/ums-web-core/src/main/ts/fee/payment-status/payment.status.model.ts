module ums {
  export interface PaymentStatus {
    id: string;
    transactionId: string;
    account: string;
    methodOfPayment: string;
    receiptNo: string;
    paymentDetails: string;
    amount: number;
    receivedOn: string;
    completedOn: string;
    status: string;
    statusId: number;
    lastModified;
  }

  export interface PaymentStatusFilter {
    id?: number;
    key?: string;
    value?: any;
    label?: string;
    labelValue?: any;
  }

  export interface PaymentStatusResponse {
    entries: PaymentStatus[];
    next: string;
    previous: string;
  }
}