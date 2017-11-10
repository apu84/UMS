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

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getFeeCategories(feeType: number): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
      this.httpClient.get(`fee-category/type/${feeType}`, HttpClient.MIME_TYPE_JSON,
          (feeCategories: FeeCategory[]) => defer.resolve(feeCategories));
      return defer.promise;
    }

    public getAllFeeCategories():ng.IPromise<FeeCategory[]>{
        let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
        this.httpClient.get(`fee-category/all`, HttpClient.MIME_TYPE_JSON,
            (feeCategories: FeeCategory[]) => defer.resolve(feeCategories));
        return defer.promise;
    }
  }

  UMS.service('FeeCategoryService', FeeCategoryService);
}
