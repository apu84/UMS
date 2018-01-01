module ums{

  export interface IAccount {
    id: string;
    accountCode: number;
    accountName: string;
    accGroupCode: number;
    reserved: boolean;
    taxLimit: string;
    taxCode: string;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: string;
    modifiedBy: string;
  }

  export interface IAccountResponse {
    entries: IAccount[];
  }

  export class AccountService{
    public static $inject = ['$q', 'HttpClient'];

    private accountServiceURL: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.accountServiceURL = "/account/definition/account";
    }

    public saveAccount(account: IAccount): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.post(this.accountServiceURL + "/save", account, HttpClient.MIME_TYPE_JSON)
          .success((response: IAccountResponse) => defer.resolve(response.entries))
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public getSize(): ng.IPromise<number> {
      let defer: ng.IDeferred<number> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/total-size", HttpClient.MIME_TYPE_JSON,
          (response: number) => defer.resolve(response));
      return defer.promise;
    }

    public getAllPaginated(itemPerPage: number, pageNumber: number): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/paginated/item-per-page/" + itemPerPage + "/page-number/" + pageNumber, HttpClient.MIME_TYPE_JSON,
          ((response: IAccountResponse) => defer.resolve(response.entries)));
      return defer.promise;
    }

    public getAccountsByAccountName(accountName: string): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/search/account-name/" + accountName, HttpClient.MIME_TYPE_JSON,
          ((response: IAccountResponse) => defer.resolve(response.entries)));
      return defer.promise;
    }
  }

  UMS.service("AccountService", AccountService);
}