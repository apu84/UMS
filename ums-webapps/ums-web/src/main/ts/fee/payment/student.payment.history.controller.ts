module ums {
  export class StudentPaymentHistory {
    public static $inject = ['PaymentService', 'FeeReportService'];
    public paymentHistory: PaymentGroup;

    constructor(private paymentService: PaymentService,
                private feeReportService: FeeReportService) {
      this.paymentService.getPaymentHistory().then((paymentGroup: PaymentGroup) => {
        this.paymentHistory = paymentGroup;
      });
    }

    public receipt(transactionId: string): void {
      this.feeReportService.receipt(transactionId);
    }
  }
  UMS.controller('StudentPaymentHistory', StudentPaymentHistory);
}