module ums {
  export class CertificateFeeController {
    public static $inject = ['CertificateFeeService', 'PaymentService'];
    public attendedSemester: AttendedSemester[];
    public certificateTypes: FeeCategory[];
    public payments: Payment[];

    constructor(private certificateFeeService: CertificateFeeService,
                private paymentService: PaymentService) {
      this.certificateFeeService.getFeeCategories().then(
          (feeCategories: FeeCategory[]) => {
            this.certificateTypes = feeCategories;
            certificateFeeService.getAttendedSemesters().then(
                (semesters: AttendedSemester[]) => this.attendedSemester = semesters
            );
          });
      this.paymentService.getCertificateFeePaymentStatus().then(
          (payments: Payment[]) => {
            if (payments && payments.length > 0) {
              this.payments = payments;
            }
          });
    }
  }
}