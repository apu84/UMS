module ums {

  export interface ITransactionResponse {
    voucherNo: string;
    message: string;
  }

  export class JournalVoucherService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger/transaction/journal-voucher";
    }

    public getVoucherNumber(): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(this.url + "/voucher-number", HttpClient.MIME_TYPE_JSON,
          (response: ITransactionResponse) => {
            if (response.message.length > 1) {
              this.notify.error("Error in fetching voucher number");
              defer.resolve(undefined)
            }
            else {
              defer.resolve(response.voucherNo);
            }

          });
      return defer.promise;
    }
  }

  UMS.service("JournalVoucherService", JournalVoucherService);
}