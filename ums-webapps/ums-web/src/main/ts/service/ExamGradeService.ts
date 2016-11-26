module ums{

  export class ExamGradeService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getGradeSubmissionDeadLine(semesterId:number,examType:number,examDate:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      var gradeSubmissionStatisticsArr:any={};

      this.httpClient.get(`academic/gradeSubmission/deadline/semester/${semesterId}/examType/${examType}/examDate/${examDate}`,
          HttpClient.MIME_TYPE_JSON,
          (json:any,etag:string)=>{
            gradeSubmissionStatisticsArr = json.entries;
            defer.resolve(gradeSubmissionStatisticsArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public updateGradeSubmissionDeadLine(json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.put('academic/gradeSubmission/gradeSubmissionDeadLine',json, HttpClient.MIME_TYPE_JSON)
        .success(()=>{
            defer.resolve("Successfully saved");
        }).error((data)=>{
        console.log(data);
      });

      return defer.promise;
    }
  }

  UMS.service('examGradeService',ExamGradeService);
}