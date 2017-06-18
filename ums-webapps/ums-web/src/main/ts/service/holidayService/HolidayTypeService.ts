module ums {
  export class HolidayTypeService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchAllHolidayTypes(): ng.IPromise<any> {
      var url = "holidayType/all";
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var holidayTypes: any = {};
            holidayTypes = json.entries;
            defer.resolve(holidayTypes);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting holiday types");
          });

      return defer.promise;
    }

  }

  UMS.service("holidayTypeService", HolidayTypeService);
}