module ums{
  interface IGradeSubmissionDeadline extends ng.IScope{
    semesterList:Array<Semester>;
    semesterId:number;
    examType:any;
    examRoutineArr:any;
    examGradeStatisticsArr:Array<IExamGrade>;
    examDate:string;
    showLoader:boolean;
    showTable:boolean;

    //functions
    getSemesters:Function;
    getExamDates:Function;
    search:Function;
  }

  interface IExamGrade{
    examDate:string;
    programShortName:string;
    courseNo:string;
    courseTitle:string;
    courseCreditHour:string;
    totalStudents:number;
    lastSubmissionDate:string;
    changed:boolean;
  }

  class GradeSubmissionDeadLine{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','examRoutineService','examGradeService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IGradeSubmissionDeadline,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private examRoutineService:ExamRoutineService,
                private examGradeService:ExamGradeService) {


      $scope.showLoader=false;
      $scope.showTable=false;
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.getExamDates = this.getExamDates.bind(this);
      $scope.search = this.search.bind(this);
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

    private search():void{
      this.$scope.examGradeStatisticsArr=[];
      var examType=+this.$scope.examType;
      this.$scope.showLoader=true;
      this.examGradeService.getGradeSubmissionDeadLine(this.$scope.semesterId,examType,this.$scope.examDate).then((outputArr:Array<IExamGrade>)=>{
        this.$scope.examGradeStatisticsArr=outputArr;
        console.log(outputArr);
        this.$scope.showTable=true;
        this.$scope.showLoader=false;
      });
    }

  }

  UMS.controller('GradeSubmissionDeadLine',GradeSubmissionDeadLine);
}