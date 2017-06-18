module ums {
  export interface FeeType {
    id: number;
    name: string;
    description: string;
  }

  export class FeeTypeService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getFeeTypes(): ng.IPromise<FeeType[]> {
      let defer: ng.IDeferred<FeeType[]> = this.$q.defer();
      this.httpClient.get('fee-type/all', HttpClient.MIME_TYPE_JSON, (feeTypes: FeeType[]) => defer.resolve(feeTypes));
      return defer.promise;
    }
  }

  UMS.service('FeeTypeService', FeeTypeService);
}