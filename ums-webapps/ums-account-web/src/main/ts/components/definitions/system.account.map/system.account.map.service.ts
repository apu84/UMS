module ums {

  export interface ISystemAccountMap {
    id: string;
    accountType: AccountType;
    accountTypeName: string;
    accountId: string;
    account: IAccount;
    companyId: string;
    company: ICompany;
    modifiedBy: string;
    modifierName: string;
    modifiedDate: string;
  }

  export enum AccountType {
    ENGINEERING_PROGRAM_ACCOUNT = 1,
    BUSINESS_PROGRAM_ACCOUNT = 2,
    CONVOCATION_ACCOUNT = 3,
    PROVIDENT_FUND_ACCOUNT = 4,
    STUDENT_WELFARE_FUND_ACCOUNT = 5
  }

  export class SystemAccountMapService {

    public static $inject = ['$q', 'HttpClient'];


    private url = "account/definition/system-account-map";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {

    }

    public getAll(): ng.IPromise<ISystemAccountMap[]> {
      let defer: ng.IDeferred<ISystemAccountMap[]> = this.$q.defer();
      this.httpClient.get(this.url + "/all", HttpClient.MIME_TYPE_JSON,
          (response: any) => {
            console.log("System account map");
            console.log(response);
            defer.resolve(response);
          });

      return defer.promise;
    }

    public get(id: string): ng.IPromise<ISystemGroupMap> {
      let defer: ng.IDeferred<ISystemGroupMap> = this.$q.defer();
      this.httpClient.get(this.url + "/id/" + id, HttpClient.MIME_TYPE_JSON,
          (response: ISystemGroupMap) => defer.resolve(response));
      return defer.promise;
    }

    public createOrUpdate(systemAccountMap: ISystemAccountMap): ng.IPromise<ISystemAccountMap> {
      let defer: ng.IDeferred<ISystemAccountMap> = this.$q.defer();
      this.httpClient.put(this.url + "/create-or-update", systemAccountMap, HttpClient.MIME_TYPE_JSON)
          .success((response: ISystemAccountMap) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            defer.resolve(undefined);
          });
      return defer.promise;
    }
  }

  UMS.service("systemAccountMapService", SystemAccountMapService);
}