module ums{


  export class SemesterService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }
    public fetchSemesters(programType:number,limit?:number):ng.IPromise<any> {
      if(!limit){
        limit=Utils.DEFAULT_SEMESTER_COUNT;
      }

      var url="academic/semester/program-type/"+programType+"/limit/"+limit;
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var semesters:any = json.entries;
            defer.resolve(semesters);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
  }

  UMS.service("semesterService",SemesterService);
}