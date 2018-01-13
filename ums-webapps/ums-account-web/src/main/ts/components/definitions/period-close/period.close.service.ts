module ums {
  export enum FinancialYearType {
    CURRENT_YEAR = 'current',
    PREVIOUS_YEAR = 'previous'
  }

  export enum IOpenCloseType{
    OPEN='O',
    CLOSE='C'
  }

  export interface IPeriodClose{
    id: string;
    month:IMonth;
    monthId: string;
    financialAccountYear: IFinancialAccountYear;
    financialAccountYearId: string;
    closeYear: number;
    periodCloseFlag:IOpenCloseType;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: string;
    modifiedBy: string;
  }
  export class PeriodCloseService {
    public static $inject = ['$q', 'HttpClient'];

    private periodCloseURL= "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.periodCloseURL = "account/definition/period-close";
    }

    public getPeriodCloseList(yearType:string):ng.IPromise<IPeriodClose[]>{
      let defer: ng.IDeferred<IPeriodClose[]> = this.$q.defer();
      this.httpClient.get(this.periodCloseURL+"/year-type/"+yearType, HttpClient.MIME_TYPE_JSON,
          (response: IPeriodClose[])=>defer.resolve(response))
      return defer.promise;
    }

    public save(periodCloseList: IPeriodClose[]):ng.IPromise<IPeriodClose[]>{
      let defer: ng.IDeferred<IPeriodClose[]> = this.$q.defer();
      this.httpClient.post(this.periodCloseURL+"/save", periodCloseList, HttpClient.MIME_TYPE_JSON)
          .success((response:IPeriodClose[])=>defer.resolve(response))
          .success((error)=>{
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }
  }

  UMS.service("PeriodCloseService", PeriodCloseService);
}