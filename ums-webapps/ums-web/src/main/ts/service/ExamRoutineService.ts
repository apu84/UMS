module ums{



  export class ExamRoutineService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getExamRoutine(semesterId:number,examType:number):ng.IPromise<any>{
      var examRoutineList:Array<ExamRoutineModel>=[];
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-academic/academic/examroutine/simplified/semester/"+semesterId+"/examtype/"+examType,"application/json",
          (json:any,etag:string)=>{
            examRoutineList=json.entries;
            defer.resolve(examRoutineList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }



    public getExamRoutineDates(semesterId:number,examType:number):ng.IPromise<any>{
      var examRoutineList:Array<ExamRoutineModel>=[];
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-academic/academic/examroutine/examdates/semester/"+semesterId+"/examtype/"+examType,"application/json",
          (json:any,etag:string)=>{
            examRoutineList=json.entries;
            defer.resolve(examRoutineList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }

  }

  UMS.service("examRoutineService",ExamRoutineService);
}