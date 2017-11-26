module ums {
  export interface AttendedSemester {
    semesterId: number;
    semesterName: string;
    year: number;
    academicSemester: number;
  }

  interface AttendedSemesterResponse {
    entries: AttendedSemester[];
  }

  export class CertificateFeeService {
    public static $inject = ['$q', 'HttpClient', 'FeeTypeService', 'FeeCategoryService', 'notify'];
    public static CERTIFICATE_FEE = 'CERTIFICATE_FEE';

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private feeTypeService: FeeTypeService,
                private feeCategoryService: FeeCategoryService,
                private notify: Notify) {

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
          (response: AttendedSemesterResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public apply(categoryId: number, semesterId?: number): ng.IPromise<boolean> {
      let resourceUrl = semesterId ? `certificate-fee/apply/semester/${semesterId}/category/${categoryId}`
          : `certificate-fee/apply/category/${categoryId}`;
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.post(resourceUrl, {}, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            this.notify.success("Successfully Applied");
            defer.resolve(true)
          })
          .error(() => {
            this.notify.error("Error in applying");
            defer.resolve(false)
          });
      return defer.promise;
    }
  }

  UMS.service('CertificateFeeService', CertificateFeeService);
}
