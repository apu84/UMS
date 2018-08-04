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


    public getActiveStudentsByDepartment():ng.IPromise<any>{

      var defer = this.$q.defer();
      var students:any={};
      this.httpClient.get('/ums-webservice-academic/academic/student/getStudentsByDept', 'application/json',
          (json:any, etag:string) => {
            students = json.entries;
            defer.resolve(students);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting student data");
          });

      return defer.promise;
    }

    public getActiveStudentsOfTheTeacher(teacherId:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      var students:any={};
      this.httpClient.get('academic/student/getStudents/adviser/'+teacherId, 'application/json',
          (json:any, etag:string) => {
              students = json.entries;
              defer.resolve(students);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
              this.notify.error("Error in getting student data");
          });

      return defer.promise;
  }



    public updateStudentsAdviser(json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      var that=this;
      this.httpClient.put("/ums-webservice-academic/academic/student/adviser",json,'application/json')
          .success(()=>{
            this.notify.success("Successfully Saved");
            defer.resolve('success')
          })
          .error((data)=>{
            console.log(data);
            this.notify.error("Problem in saving data");
            defer.resolve('failure');
          });
      return defer.promise;
    }
      public updateStudentsSection(json:any):ng.IPromise<any>{
          var defer = this.$q.defer();
          var that=this;
          this.httpClient.put("/ums-webservice-academic/academic/student/section",json,'application/json')
              .success(()=>{
                  this.notify.success("Successfully Saved");
                  defer.resolve('success')
              })
              .error((data)=>{
                  console.log(data);
                  this.notify.error("Problem in saving data");
                  defer.resolve('failure');
              });
          return defer.promise;
      }
  }

  UMS.service("studentService",StudentService);
}