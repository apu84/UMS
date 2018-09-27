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


    public getActiveTeachers():ng.IPromise<any>{
      var defer = this.$q.defer();
      var teachers:any={};
      this.httpClient.get("academic/employee/getActiveTeachers",'application/json',
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
                  defer.reject(response);
                  this.notify.error("Error in fetching employee data");
              });

          return defer.promise;
      }

      public getAll(): ng.IPromise<any>{
          let defer = this.$q.defer();
          let employees:any={};
          this.httpClient.get("academic/employee/all",'application/json',
              (json:any,etag:string)=>{
                  defer.resolve(json.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
                  this.notify.error("Error in fetching employee data");
              });

          return defer.promise;
      }

      public save(json: any): ng.IPromise<any> {
          let defer = this.$q.defer();
          this.httpClient.post("academic/employee/", json, 'application/json')
              .success(() => {
                  defer.resolve("Success");
              })
              .error((data) => {
                  console.log(data);
                  defer.reject();
              });
          return defer.promise;
      }

      public update(employeeId: string, json: any): ng.IPromise<any> {
          let defer = this.$q.defer();
          this.httpClient.put("academic/employee/"+ employeeId, json, 'application/json')
              .success(() => {
                  defer.resolve("Success");
              })
              .error((data) => {
                  console.log(data);
                  defer.reject();
              });
          return defer.promise;
      }

      public updateEmployee(json: any): ng.IPromise<any> {
          let defer = this.$q.defer();
          this.httpClient.put("academic/employee/update/verify", json, 'application/json')
              .success(() => {
                  defer.resolve("Success");
              })
              .error((data) => {
                  console.log(data);
                  defer.reject();
              });
          return defer.promise;
      }


      public getNewEmployeeId(pDept: string, pEmployeeType: number):ng.IPromise<any>{
          var defer = this.$q.defer();
          var employees:any={};
          this.httpClient.get("academic/employee/newId/deptId/" + pDept + "/employeeType/" + pEmployeeType,'application/json',
              (json:any,etag:string)=>{
                  defer.resolve(json.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
                  this.notify.error("Error");
              });

          return defer.promise;
      }

      public checkDuplicate(pShortName: string):ng.IPromise<any>{
          var defer = this.$q.defer();
          this.httpClient.get("academic/employee/validate/" + pShortName,'application/json',
              (result:boolean,etag:string)=>{
                  defer.resolve(result);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
                  this.notify.error("Error");
              });

          return defer.promise;
      }

      public getFilteredDesignation(pDeptId: string, pEmployeeTypeId: number): ng.IPromise<any>{
          var defer = this.$q.defer();
          this.httpClient.get("deptDesignationRoleMap/dept/" + pDeptId + "/employeeType/" + pEmployeeTypeId, 'application/json',
              (result:any,etag:string)=>{
                  defer.resolve(result.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
              });

          return defer.promise;
      }

      public getDesignation(pDeptId: string): ng.IPromise<any>{
          var defer = this.$q.defer();
          this.httpClient.get("deptDesignationRoleMap/dept/" + pDeptId, 'application/json',
              (result:any,etag:string)=>{
                  defer.resolve(result.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
              });

          return defer.promise;
      }

      public getSimilarUsers(name: string): ng.IPromise<any>{
          var defer = this.$q.defer();
          this.httpClient.get("employee/personal/firstName/name/" + name, 'application/json',
              (result:any,etag:string)=>{
                  defer.resolve(result.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  console.error(response);
              });

          return defer.promise;
      }

      public getEmployeeListWaitingForAccountVerification(): ng.IPromise<any>{
          var defer = this.$q.defer();
          this.httpClient.get("academic/employee/status/pending", 'application/json',
              (result:any,etag:string)=>{
                  defer.resolve(result.entries);
              },
              (response:ng.IHttpPromiseCallbackArg<any>)=>{
                  this.notify.error("Error in fetching");
                  console.error(response);
              });

          return defer.promise;
      }

      public deleteEmployee(id: string): ng.IPromise<any> {
          let defer = this.$q.defer();
          this.httpClient.doDelete("academic/employee" + "/" + id)
              .success(() => {
                  this.notify.success("Delete Successful");
                  defer.resolve();
              })
              .error((data) => {
                  this.notify.error("Error in Deleting");
              });
          return defer.promise;
      }
  }

  UMS.service("employeeService",EmployeeService);
}