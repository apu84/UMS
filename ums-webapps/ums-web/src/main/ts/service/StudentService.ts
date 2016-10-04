/**
 * Created by My Pc on 02-Oct-16.
 */
module ums{
  export class StudentService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getStudentById(studentId:string):ng.IPromise<any>{

      var defer = this.$q.defer();
      var student:any={};
      this.httpClient.get('/academic/student/'+studentId, 'application/json',
          (json:any, etag:string) => {
            student = json.entries;
            defer.resolve(student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting student data");
          });

      return defer.promise;
    }

  }

  UMS.service("studentService",StudentService);
}