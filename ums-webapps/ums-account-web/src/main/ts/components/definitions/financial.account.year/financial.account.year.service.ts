module ums {
  export enum BookClosingFlagType {
    OPEN = "O",
    CLOSED = "C"
  }

  export enum YearClosingFlagType {
    OPEN = "O",
    CLOSED = "C"
  }

  export interface IFinancialAccountYear {
    id: number;
    mStringId: string;
    mCurrentStartDate: Date;
    mCurrentEndDate: Date;
    previousStartDate: Date;
    previousEndDate: Date;
    bookClosingFlag: BookClosingFlagType;
    itLimit: number;
    yearClosingFlag: YearClosingFlagType;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: Date;
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

  }

  UMS.service("FinancialAccountYearService", FinancialAccountYearService);
}