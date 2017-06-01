/**
 * Created by Monjur-E-Morshed on 29-May-17.
 */
module ums {
  export class AdditionalRolePermissionsService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchLoggedUserAdditionalRolePermissions(): ng.IPromise<any> {

      var url = "additionalRolePermissions/loggedUser";
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var additionalRoles: any = {};
            additionalRoles = json.entries;
            console.log("Json ");
            console.log(json);
            console.log("Entries value");
            console.log(additionalRoles);
            defer.resolve(additionalRoles);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting additional roles");
          });

      /*  this.httpClient.get(url, this.appConstants.mimeTypeJson,
       (json: any, etag: string) => {

       var additonalPermissions: any = {};
       additonalPermissions = json.entries;
       defer.resolve(additonalPermissions);
       },
       (response: ng.IHttpPromiseCallbackArg<any>) => {
       this.notify.error("Error in getting additional role permissions");
       console.error(response);
       });*/

      return defer.promise;
    }
  }

  UMS.service("additionalRolePermissionsService", AdditionalRolePermissionsService);
}