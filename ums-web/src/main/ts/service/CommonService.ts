module ums{
  export class CommonService{
    public static $inject = ['appConstants','HttpClient','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService) {
    }
    public fetchCurrentUser():ng.IPromise<any> {
      var url="/ums-webservice-common/users/current";
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            //console.log(json);
            defer.resolve({id:json.departmentId,name:json.departmentName});
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
  }

  UMS.service('commonService',CommonService);
}