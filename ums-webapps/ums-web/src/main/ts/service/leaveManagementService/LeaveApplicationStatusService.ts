/**
 * Created by My Pc on 09-May-17.
 */
module ums {
  export class LeaveApplicationStatusService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchApplicationStatus(appId: string): ng.IPromise<any> {
      var url = "lmsAppStatus/appId/" + appId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var lmsAppStatusList: any = {};
            lmsAppStatusList = json.entries;
            defer.resolve(lmsAppStatusList);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting leave application status");
          });

      return defer.promise;
    }

    public fetchPendingLeaves(): ng.IPromise<any> {
      var url = "lmsAppStatus/pendingLeaves";
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var lmsAppStatusList: any = {};
            lmsAppStatusList = json.entries;
            defer.resolve(lmsAppStatusList);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting pending leaves");
          });

      return defer.promise;
    }

    public fetchLeaveApplicationsWithPagination(leaveApprovalStatus: number, pageNumber: number, pageSize: number): ng.IPromise<any> {
      var url = "lmsAppStatus/leaveApplications/status/" + leaveApprovalStatus + "/pageNumber/" + pageNumber + "/pageSize/" + pageSize;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var lmsAppStatusList: any = {};
            lmsAppStatusList = json.entries;
            defer.resolve(lmsAppStatusList);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting leave applications");
          });

      return defer.promise;
    }

  }

  UMS.service("leaveApplicationStatusService", LeaveApplicationStatusService);
}