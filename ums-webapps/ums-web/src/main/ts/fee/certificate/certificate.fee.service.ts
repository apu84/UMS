module ums {
  export interface AttendedSemester {
    semesterId: number;
    semesterName: string;
    year: number;
    academicSemester: number;
  }

  export class CertificateFeeService {
    public static $inject = ['$q', 'HttpClient', 'FeeTypeService', 'FeeCategoryService'];
    public static CERTIFICATE_FEE = 'CERTIFICATE_FEE';

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private feeTypeService: FeeTypeService,
                private feeCategoryService: FeeCategoryService) {

    }

    public getFeeCategories(): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
      this.feeTypeService.getFeeTypes().then((feeTypes: FeeType[]) => {
        for (let i = 0; i < feeTypes.length; i++) {
          if (feeTypes[i].name === CertificateFeeService.CERTIFICATE_FEE) {
            this.feeCategoryService.getFeeCategories(feeTypes[i].id).then(
                (feeCategories: FeeCategory[]) => defer.resolve(feeCategories)
            );
          }
        }
      });
      return defer.promise;
    }

    public getAttendedSemesters(): ng.IPromise<AttendedSemester[]> {
      let defer: ng.IDeferred<AttendedSemester[]> = this.$q.defer();
      this.httpClient.get('certificate-fee/attended-semesters', HttpClient.MIME_TYPE_JSON,
          (semesters: AttendedSemester[]) => defer.resolve(semesters));
      return defer.promise;
    }

    public apply(semesterId: number, categoryId: number): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.post(`/apply/semester/${semesterId}/category/${categoryId}`, {}, HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }
  }
}