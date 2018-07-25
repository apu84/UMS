/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */

module ums {



  export class LeaveTypeService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchLeaveTypes(): ng.IPromise<any> {
      var url = "lmsType/all";
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var lmsTypes: any = {};
            lmsTypes = json.entries;
            defer.resolve(lmsTypes);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting Leave Types");
          });

      return defer.promise;
    }
  }

  UMS.service("leaveTypeService", LeaveTypeService);
}