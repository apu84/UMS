module ums{

  interface IViewSeatPlan extends ng.IScope{
    student:Student;

  }

  class ViewSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IViewSeatPlan,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }


    private getStudentInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var student:Student;

      this.httpClient.get('/ums-webservice-common/academic/student/getStudentInfoById','application/json',
          (json:any,etag:string)=>{
            student = json.entries;
            this.$scope.student = student;
            console.log(this.$scope.student);
            defer.resolve(student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }


  }

  UMS.controller("ViewSeatPlan",ViewSeatPlan);
}