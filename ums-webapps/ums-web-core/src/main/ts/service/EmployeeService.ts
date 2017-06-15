/**
 * Created by My Pc on 01-Oct-16.
 */
module ums{
  export class EmployeeService{
    public static $inject = ['HttpClient','$q','notify','$sce','$window'];
    constructor(
                private httpClient: HttpClient,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getActiveTeacherByDept():ng.IPromise<any>{
      var defer = this.$q.defer();
      var teachers:any={};
      this.httpClient.get("academic/employee/getActiveTeachersByDept",'application/json',
          (json:any,etag:string)=>{
            teachers = json.entries;
            defer.resolve(teachers);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching teacher data");
          });

      return defer.promise;
    }


    public getLoggedEmployeeInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var employees:any={};
      this.httpClient.get("academic/employee/employeeById",'application/json',
          (json:any,etag:string)=>{
            employees = json.entries;
            defer.resolve(employees[0]);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching employee data");
          });

      return defer.promise;
    }

      public getEmployees(departmentId: string):ng.IPromise<any>{
          var defer = this.$q.defer();
          var employees:any={};
          this.httpClient.get("academic/employee/employeeById/departmentId/" + departmentId,'application/json',
              (json:any,etag:string)=>{
                  defer.resolve(json.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
                  this.notify.error("Error in fetching employee data");
              });

          return defer.promise;
      }
  }

  UMS.service("employeeService",EmployeeService);
}