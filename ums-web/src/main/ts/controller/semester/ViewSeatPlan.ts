module ums{

  interface IViewSeatPlan extends ng.IScope{
    student:Student;

    getStudentInfo:Function;
    getStudentRecordInfo:Function;
    getSeatPlanInfoFinalExam:Function;

  }

  interface ISeatPlan{

  }

  class ViewSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IViewSeatPlan,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      $scope.getStudentInfo = this.getStudentInfo.bind(this);
      $scope.getStudentRecordInfo = this.getStudentRecordInfo.bind(this);
      $scope.getSeatPlanInfoFinalExam = this.getSeatPlanInfoFinalExam.bind(this);

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

    private getStudentRecordInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var studentRecord:any;

      this.httpClient.get("/ums-webservice-common/academic/studentrecord/student/"+this.$scope.student.id+
              "/semesterId"+this.$scope.student.semesterId+"/year/"+this.$scope.student.year+"/semester/"+this.$scope.student.academicSemester,
              'application/json',
          (json:any,etag:string)=>{
            studentRecord:json.entries;
            defer.resolve(studentRecord);
          },(response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    private getSeatPlanInfoFinalExam():ng.IPromise<any>{
      return null;
    }




  }

  UMS.controller("ViewSeatPlan",ViewSeatPlan);
}