module ums {
  export class CertificateFeeController {
    public static $inject = ['CertificateFeeService', 'PaymentService', 'CertificateStatusService'];
    public attendedSemesters: AttendedSemester[];
    public certificateTypes: FeeCategory[];
    public payments: Payment[];
    public certificatesStatus: {[key: string]: CertificateStatus} = {};
    public semesterId: number;
    public feeId: number;
    public enableSemester: boolean = true;
    private certificateTypeMap: {[key: string]: FeeCategory};

    constructor(private certificateFeeService: CertificateFeeService,
                private paymentService: PaymentService,
                private certificateStatusService: CertificateStatusService) {
      this.certificateFeeService.getFeeCategories().then(
          (feeCategories: FeeCategory[]) => {
            this.certificateTypes = feeCategories;
            let convertedMap: {[key: string]: FeeCategory} = {};
            this.certificateTypeMap = feeCategories.reduce((map: {[key: string]: FeeCategory}, obj: FeeCategory) => {
              map[obj.id] = obj;
              return map;
            }, convertedMap);

            certificateFeeService.getAttendedSemesters().then(
                (semesters: AttendedSemester[]) => this.attendedSemesters = semesters
            );
          });
      this.getCertificateFeePaymentStatus();
      this.certificateStatusService.getCertificateStatus().then(
          (certificates: CertificateStatus[]) => {
            let convertedMap: {[key: string]: CertificateStatus} = {};
            this.certificatesStatus = certificates.reduce((map: {[key: string]: CertificateStatus}, obj: CertificateStatus) => {
              map[obj.transactionId] = obj;
              return map;
            }, convertedMap);
          }
      );
    }

    public apply(): void {
      this.certificateFeeService.apply(this.feeId, this.semesterId).then((success: boolean) => {
        if (success) {
          this.getCertificateFeePaymentStatus();
        }
      });
    }

    public setSemesterVisibility(): void {
      this.enableSemester = this.certificateTypeMap[this.feeId].feeId === "GRADESHEET_PROVISIONAL"
          || this.certificateTypeMap[this.feeId].feeId === "GRADESHEET_DUPLICATE";
      if (!this.enableSemester) {
        this.semesterId = undefined;
      }
    }

    private getCertificateFeePaymentStatus(): void {
      this.paymentService.getCertificateFeePaymentStatus().then(
          (payments: Payment[]) => {
            if (payments && payments.length > 0) {
              this.payments = payments;
            }
          });
    }

  }

  UMS.controller('CertificateFeeController', CertificateFeeController);
}
