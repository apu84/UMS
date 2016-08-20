module ums{


  export class SemesterService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getSemesterByProgramType(programType:string):ng.IPromise<any>{
      var programTypeNumeric=+programType;
      var defer = this.$q.defer();
      var limit=0;
      var semesterArr:Array<Semester>=[];
      this.httpClient.get('/ums-webservice-common/academic/semester/program-type/'+programTypeNumeric+'/limit/'+0, 'application/json',
          (json:any, etag:string) => {
            semesterArr = json.entries;
            defer.resolve(semesterArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }
  }

  UMS.service("semesterService",SemesterService);
}