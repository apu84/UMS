module ums{
  export class DepartmentSelectionDeadlineService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public saveOrUpdateDeadline(json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      var url="academic/department-selection-deadline";

      this.httpClient.post(url, json, 'application/json')
          .success(()=>{
            this.notify.success("Successfully Saved");
            defer.resolve("success");
          }).error((data)=>{
            console.log("error in saving data");
            console.error(data);
            this.notify.error("Error in saving data");
          });

      return defer.promise;
    }

    public delete(id:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var url="academic/department-selection-deadline/delete/id/"+id;


      this.httpClient.delete(url)
          .success(()=>{
            this.notify.success("Successfully deleted");
            defer.resolve("success");
          })
          .error((data)=>{
            defer.resolve("error");
            console.log("Error in deleting data");
            console.error(data);
            this.notify.error("Error in deleting data");
          });

      return defer.promise;
    }

    public getDeadlines(semesterId:number, quota:string, unit: string):ng.IPromise<any>{
      var defer = this.$q.defer();
      var url="academic/department-selection-deadline/semester/"+semesterId+"/quota/"+quota+"/unit/"+unit;

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var deadlines: any  = json.entries;
            defer.resolve(deadlines);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in retrieving department selection deadline data");
          });

      return defer.promise;
    }
  }

  UMS.service("departmentSelectionDeadlineService", DepartmentSelectionDeadlineService);
}