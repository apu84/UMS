module ums {
  export enum ResetBasis {
    Monthly = "M",
    Yearly = "Y",
    Daily = "D"
  }

  export interface IVoucherNumberControl {
    id: string;
    finAccountYearId: string;
    finAccountYear: IFinancialAccountYear;
    voucherId: string;
    voucher: IVoucher;
    resetBasis: ResetBasis;
    startVoucherNo: number;
    voucherLimit: string;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: string;
    modifiedBy: string;
  }
  export class VoucherNumberControlService {
    public static $inject = ['$q', 'HttpClient'];

    private voucherNumberControlServiceURL = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.voucherNumberControlServiceURL = "account/definition/voucher-number-control";
    }

    public getAllByCurrentFinancialYear(): ng.IPromise<IVoucherNumberControl[]> {
      let defer: ng.IDeferred<IVoucherNumberControl[]> = this.$q.defer();
      this.httpClient.get(this.voucherNumberControlServiceURL + "/based-on-curr-fin-year", HttpClient.MIME_TYPE_JSON,
          (response: IVoucherNumberControl[]) => defer.resolve(response));
      return defer.promise;
    }

    public saveAndReturnList(voucherNumberControl: IVoucherNumberControl[]): ng.IPromise<IVoucherNumberControl[]> {
        console.log("Jsons");
        console.log(voucherNumberControl);
      let defer: ng.IDeferred<IVoucherNumberControl[]> = this.$q.defer();
      this.httpClient.post(this.voucherNumberControlServiceURL + "/save", voucherNumberControl, HttpClient.MIME_TYPE_JSON)
          .success((response: IVoucherNumberControl[]) => defer.resolve(response))
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

  }

  UMS.service("VoucherNumberControlService", VoucherNumberControlService);
}