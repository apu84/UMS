module ums{
  interface IGradeSubmissionDeadline extends ng.IScope{
    semesterList:Array<Semester>;
    semesterId:number;
    examType:any;
    examRoutineArr:any;

    //functions
    getSemesters:Function;
    getExamDates:Function;
  }

  class GradeSubmissionDeadLine{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','examRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IGradeSubmissionDeadline,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private examRoutineService:ExamRoutineService) {


      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.getExamDates = this.getExamDates.bind(this);
    }

    private getSemesters():ng.IPromise<any>{

      this.$scope.semesterList=[];
      var defer = this.$q.defer();

      this.semesterService.getAllSemesters().then((semesterArr:Array<Semester>)=>{
        this.$scope.semesterList = semesterArr;
        defer.resolve(semesterArr);
      });

      return defer.promise;
    }

    private getExamDates():void{
      var examType = + this.$scope.examType;
      console.log(examType);
      console.log(this.$scope.semesterId);
      if(this.$scope.semesterId!=null && this.$scope.examType!=""){
        this.examRoutineService.getExamRoutineDates(this.$scope.semesterId,examType).then((examDateArr:any)=>{
          this.$scope.examRoutineArr={};
          console.log(examDateArr);
          this.$scope.examRoutineArr=examDateArr;
        });
      }

    }

  }

  UMS.controller('GradeSubmissionDeadLine',GradeSubmissionDeadLine);
}