module ums {
  export interface FeeCategory {
    feeId: string;
    feeTypeId: number;
    name: string;
    description: string;
    id: string;
    lastModified: string;
  }

  export class FeeCategoryService {
    public static $inject = ['$q', 'HttpClient'];
    public static CERTIFICATE_FEE = 2;
    public static SEMESTER_FEE = 1;
    public static DUES = 3;
    public static PENALTY = 4;
    public static OTHERS = 0;
    public static DEPT_CERTIFICATE_FEE = 5;
    public static REG_CERTIFICATE_FEE = 6;
    public static PROC_CERTIFICATE_FEE = 7;

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getFeeCategories(feeType: number): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
      this.httpClient.get(`fee-category/type/${feeType}`, HttpClient.MIME_TYPE_JSON,
          (feeCategories: FeeCategory[]) => defer.resolve(feeCategories));
      return defer.promise;
    }

    public getAllFeeCategories(): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
      this.httpClient.get(`fee-category/all`, HttpClient.MIME_TYPE_JSON,
          (feeCategories: FeeCategory[]) => defer.resolve(feeCategories));
      return defer.promise;
    }
  }

  UMS.service('FeeCategoryService', FeeCategoryService);
}
