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


    public saveOrUpdateHolidays(json: any): ng.IPromise<any> {
      var defer = this.$q.defer();
      var url = "holidays/save";
      this.httpClient.post(url, json, 'application/json')
          .success(() => {
            this.notify.success("Successfully Saved");
            defer.resolve("success");
          }).error((data) => {
        console.log("error in saving data");
        console.error(data);
        this.notify.error("Error in saving data");
      });

      return defer.promise;
    }


    public saveHolidays(json: any): ng.IPromise<any> {
      console.log("Json");
      console.log(json);
      var defer = this.$q.defer();
      var url = "holidays/save";

      this.httpClient.put('holidays/save', json, 'application/json')
          .success(() => {
            defer.resolve("success");
            this.notify.success("Success");
          }).error((data) => {
        defer.resolve("error");
      });
      return defer.promise;
    }

  }

  UMS.service("holidaysService", HolidaysService);
}