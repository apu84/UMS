module ums {


  export interface IAccountBalance{
      id: string;
      accountCode: string;
      yearOpenBalance: number;
      yearCloseBalance: number;
      yearOpenBalanceType:any;
      modifiedDate: string;
  }

  export class AccountBalanceService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/account-balance";
    }

    public getAccountBalance(accountId: string): ng.IPromise<number> {
      let defer: ng.IDeferred<number> = this.$q.defer();
      this.httpClient.get(this.url + "/account-id/" + accountId, HttpClient.MIME_TYPE_JSON,
          (response: number) => {
            console.log("Response");
            console.log(response);
            defer.resolve(response)
          });
      return defer.promise;
    }
  }

  UMS.service("AccountBalanceService", AccountBalanceService);
}