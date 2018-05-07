module ums{

  export enum BalanceType {
    Dr = "Dr",
    Cr = "Cr"
  }

  export interface IAccount {
    rowNumber: number;
    id: string;
    accountCode: number;
    accountName: string;
    accGroupCode: string;
    accGroupName: string;
    company: ICompany;
    companyId: string;
    reserved: boolean;
    taxLimit: string;
    taxCode: string;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: string;
    modifiedBy: string;
    yearOpenBalance: number;
    yearOpenBalanceType: BalanceType;
    yearClosingBalanceType: BalanceType;
  }

  export interface IAccountResponse {
    entries: IAccount[];
  }

  export interface IAccountBalanceResponse{
    account: IAccount;
    accountBalance: IAccountBalance;
  }

  export class AccountService{
    public static $inject = ['$q', 'HttpClient'];

    private accountServiceURL: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.accountServiceURL = "account/definition/account";
    }

    public saveAccountPaginated(account: IAccount, accountBalance: IAccountBalance, itemPerPage: number, pageNumber: number): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      let accountBalanceResponse: IAccountBalanceResponse = <IAccountBalanceResponse>{};
      accountBalanceResponse.account = account;
      accountBalanceResponse.accountBalance = accountBalance;
      this.httpClient.post(this.accountServiceURL + "/create/item-per-page/" + itemPerPage + "/page-number/" + pageNumber, accountBalanceResponse, HttpClient.MIME_TYPE_JSON)
          .success((response: IAccount[]) => defer.resolve(response))
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
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getAccountsByAccountName(accountName: string): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/search/account-name/" + accountName, HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getAccountsByGroupFlag(groupFlag: GroupFlag):ng.IPromise<IAccount[]>{
      let defer:ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL+"/group-flag/"+groupFlag, HttpClient.MIME_TYPE_JSON,
          ((response:IAccount[])=> defer.resolve(response)));
      return defer.promise;
    }

    public getAllAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/all", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getBankAndCostTypeAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/bank-cost-type-accounts", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getExcludingBankAndCostTypeAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/excluding-bank-cost-type-accounts", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getCustomerAndVendorTypeAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/customer-vendor-accounts", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getVendorAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/vendor-accounts", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }

    public getCustomerAccounts(): ng.IPromise<IAccount[]> {
      let defer: ng.IDeferred<IAccount[]> = this.$q.defer();
      this.httpClient.get(this.accountServiceURL + "/customer-accounts", HttpClient.MIME_TYPE_JSON,
          ((response: IAccount[]) => defer.resolve(response)));
      return defer.promise;
    }


    public generateChartOfAccountsReport(): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      var contentType: string = UmsUtil.getFileContentType("pdf");
      this.httpClient.get(this.accountServiceURL + "/chart-of-accounts/", undefined, (data: any, etag: string) => {
            UmsUtil.writeFileContent(data, contentType, 'Chart of Accounts Report.pdf');
          },
          (response: any) => {
            defer.resolve("success");
            console.error(response);
          }, 'arraybuffer');
      return defer.promise;
    }
  }

  UMS.service("AccountService", AccountService);
}