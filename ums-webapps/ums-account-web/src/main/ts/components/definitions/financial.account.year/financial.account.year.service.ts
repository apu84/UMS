module ums {
  export enum BookClosingFlagType {
    OPEN = "OPEN",
    CLOSED = "CLOSED"
  }

  export enum YearClosingFlagType {
    OPEN = "OPEN",
    CLOSED = "CLOSED"
  }

  export interface IFinancialAccountYear {
    id: number;
    stringId: string;
    currentStartDate: any;
    currentEndDate: any;
    previousStartDate: any;
    previousEndDate: any;
    bookClosingFlag: BookClosingFlagType;
    itLimit: number;
    yearClosingFlag: YearClosingFlagType;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: any;
    modifiedBy: string;
  }

  export class FinancialAccountYearService {
    public static $inject = ['$q', 'HttpClient'];
    private financialAccountYearServiceUrl = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.financialAccountYearServiceUrl = "account/definition/financialAccountYear";
    }

    public getAllFinalcialYears(): ng.IPromise<IFinancialAccountYear[]> {
      let defer: ng.IDeferred<IFinancialAccountYear[]> = this.$q.defer();
      this.httpClient.get(this.financialAccountYearServiceUrl + "/all", HttpClient.MIME_TYPE_JSON,
          (response: IFinancialAccountYear[]) => defer.resolve(response));
      return defer.promise;
    }

    public getOpenedFinancialAccountYear(): ng.IPromise<IFinancialAccountYear> {
      let defer: ng.IDeferred<IFinancialAccountYear> = this.$q.defer();
      this.httpClient.get(this.financialAccountYearServiceUrl + "/openedYear", HttpClient.MIME_TYPE_JSON,
          (response: IFinancialAccountYear) => defer.resolve(response));
      return defer.promise;
    }

    public saveAndGetAllFinancialYears(financialAccountYear: IFinancialAccountYear): ng.IPromise<IFinancialAccountYear[]> {
      let defer: ng.IDeferred<IFinancialAccountYear[]> = this.$q.defer();
      this.httpClient.post(this.financialAccountYearServiceUrl + "/save", financialAccountYear, HttpClient.MIME_TYPE_JSON)
          .success((response: IFinancialAccountYear[]) => defer.resolve(response))
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

      public closeCurrentYearAndCreateNewYear(startDate: string, endDate: string, transferType:number): ng.IPromise<IFinancialAccountYear[]> {
          let defer: ng.IDeferred<IFinancialAccountYear[]> = this.$q.defer();
          this.httpClient.get(this.financialAccountYearServiceUrl + "/startDate/"+startDate+"/endDate/"+endDate+"/transferType/"+transferType , HttpClient.MIME_TYPE_JSON,
              (response:IFinancialAccountYear[])=>defer.resolve(response));
          return defer.promise;
      }

  }

  UMS.service("FinancialAccountYearService", FinancialAccountYearService);
}