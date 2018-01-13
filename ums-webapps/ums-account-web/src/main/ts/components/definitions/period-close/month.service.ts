module ums {

  export interface IMonth {
    id: string;
    name: string;
    shortName: string;
  }

  export class MonthService {
    public static $inject = ['$q', 'HttpClient'];

    private monthURL = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.monthURL = "account/definition/period-close/month";
    }

    public getAllMonths(): ng.IPromise<IMonth[]> {
      let defer: ng.IDeferred<IMonth[]> = this.$q.defer();
      this.httpClient.get(this.monthURL + "/all", HttpClient.MIME_TYPE_JSON,
          (response: IMonth[]) => defer.resolve(response));

      return defer.promise;
    }
  }

  UMS.service("MonthService", MonthService);
}