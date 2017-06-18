/**
 * Created by My Pc on 17-Jun-17.
 */
module ums {
  export class HolidaysService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchHolidaysByYear(year: number): ng.IPromise<any> {
      var url = "holidays/year/" + year;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var holidays: any = {};
            holidays = json.entries;
            defer.resolve(holidays);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting holidays");
          });

      return defer.promise;
    }


  }

  UMS.service("holidaysService", HolidaysService);
}