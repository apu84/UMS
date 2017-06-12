module ums {
  export class CertificateFeeController {
    public static $inject = ['CertificateFeeService', 'PaymentService', 'CertificateStatusService'];
    public attendedSemester: AttendedSemester[];
    public certificateTypes: FeeCategory[];
    public payments: Payment[];
    public certificatesStatus: CertificateStatus[];

    constructor(private certificateFeeService: CertificateFeeService,
                private paymentService: PaymentService,
                private certifcateStatusService: CertificateStatusService) {
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
      this.certifcateStatusService.getCertificateStatus().then(
          (certifcates: CertificateStatus[]) => this.certificatesStatus = certifcates
      );
    }
  }
}