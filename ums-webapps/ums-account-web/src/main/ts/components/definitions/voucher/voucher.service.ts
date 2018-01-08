module ums {

  export interface IVoucher {
    id: string;
    name: string;
    shortName: string;
  }

  export class VoucherService {

    public static $inject = ['$q', 'HttpClient']

    private voucherURL: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.voucherURL = "/account/definition/voucher";
    }

    public getAllVoucher(): ng.IPromise<IVoucher[]> {
      let defer: ng.IDeferred<IVoucher[]> = this.$q.defer();
      this.httpClient.get(this.voucherURL + "/all", HttpClient.MIME_TYPE_JSON,
          (response: IVoucher[]) => defer.resolve(response));
      return defer.promise;
    }
  }

  UMS.service("VoucherService", VoucherService);
}