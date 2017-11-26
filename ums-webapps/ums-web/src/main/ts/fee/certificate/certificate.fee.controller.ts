module ums {
  export class CertificateFeeController {
    public static $inject = ['CertificateFeeService', 'PaymentService', 'CertificateStatusService', 'FeeReportService', 'FeeCategoryService', '$q'];
    public attendedSemesters: AttendedSemester[];
    public certificateTypes: FeeCategory[];
    public payments: Payment[];
    public certificatesStatus: { [key: string]: CertificateStatus } = {};
    public semesterId: number;
    public feeId: number;
    public enableSemester: boolean = true;
    private certificateTypeMap: { [key: string]: FeeCategory };

    constructor(private certificateFeeService: CertificateFeeService,
                private paymentService: PaymentService,
                private certificateStatusService: CertificateStatusService,
                private feeReportService: FeeReportService,
                private feeCategoryService: FeeCategoryService,
                private $q: ng.IQService) {
      /*this.certificateFeeService.getFeeCategories().then(
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
          });*/
      this.getCertificateFeeCategories().then((feeCategories: FeeCategory[]) => {
        this.certificateTypes = feeCategories;
        let convertedMap: { [key: string]: FeeCategory } = {};
        this.certificateTypeMap = feeCategories.reduce((map: { [key: string]: FeeCategory }, obj: FeeCategory) => {
          map[obj.id] = obj;
          return map;
        }, convertedMap);

        certificateFeeService.getAttendedSemesters().then(
            (semesters: AttendedSemester[]) => this.attendedSemesters = semesters
        );

      });
      //this.getCertificateFeePaymentStatus();
      this.certificateStatusService.getCertificateStatus().then(
          (certificates: CertificateStatus[]) => {
            let convertedMap: { [key: string]: CertificateStatus } = {};
            this.certificatesStatus = certificates.reduce((map: { [key: string]: CertificateStatus }, obj: CertificateStatus) => {
              map[obj.transactionId] = obj;
              return map;
            }, convertedMap);
          }
      );
    }


    private getCertificateFeeCategories(): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();

      this.feeCategoryService.getAllFeeCategories().then((feeCategories: FeeCategory[]) => {
        var filteredFeeCategories: FeeCategory[] = [];
        for (var i = 0; i < feeCategories.length; i++) {
          if (feeCategories[i].feeTypeId === FeeCategoryService.CERTIFICATE_FEE
              || feeCategories[i].feeTypeId === FeeCategoryService.DEPT_CERTIFICATE_FEE
              || feeCategories[i].feeTypeId === FeeCategoryService.REG_CERTIFICATE_FEE
              || feeCategories[i].feeTypeId === FeeCategoryService.PROC_CERTIFICATE_FEE) {
            filteredFeeCategories.push(feeCategories[i]);
          }
        }
        defer.resolve(filteredFeeCategories);
      });

      return defer.promise;
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

    public receipt(transactionId: string): void {
      this.feeReportService.receipt(transactionId);
    }
  }

  UMS.controller('CertificateFeeController', CertificateFeeController);
}
