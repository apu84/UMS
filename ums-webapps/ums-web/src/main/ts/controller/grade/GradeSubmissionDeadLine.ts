module ums{
  interface IGradeSubmissionDeadline extends ng.IScope{
    semesterList:Array<Semester>;
    semesterId:number;
    examType:any;
    examRoutineArr:any;
    examGradeStatisticsArr:Array<IExamGrade>;
    examGradeStatisticsArrTemp:Array<IExamGrade>;
    examDate:string;
    showLoader:boolean;
    showTable:boolean;
    showButton:boolean;
    editable:boolean;

    //functions
    getSemesters:Function;
    getExamDates:Function;
    fetchDeadlineInformation:Function;
    dateChanged:Function;
    saveChanges:Function;
    cancel:Function;
    semesterSelected:Function;

    convertToJson:Function;
  }

  interface IExamGrade{
    examDate:string;
    programShortName:string;
    courseId:string;
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
      $scope.showButton=false;
      $scope.editable=false;
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.getExamDates = this.getExamDates.bind(this);
      $scope.fetchDeadlineInformation = this.fetchDeadlineInformation.bind(this);
      $scope.dateChanged = this.dateChanged.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.convertToJson =this.convertToJson.bind(this);
      $scope.saveChanges=this.saveChanges.bind(this);
      //Utils.setValidationOptions("form-horizontal");
    }



    private getSemesters():ng.IPromise<any>{

      this.$scope.semesterList=[];
      var defer = this.$q.defer();

      this.semesterService.fetchSemesters(Utils.UG).then((semesterArr:Array<Semester>)=>{
        this.$scope.semesterList = semesterArr;
        this.$scope.semesterId=semesterArr[0].id;
        this.$scope.examType=Utils.EXAM_TYPE_REGULAR;
        this.getExamDates();
        defer.resolve(semesterArr);
      });

      return defer.promise;
    }


    private saveChanges(){
      this.convertToJson().then((json:any)=>{
        this.$scope.examGradeStatisticsArrTemp=[];
        this.$scope.examGradeStatisticsArrTemp=angular.copy(this.$scope.examGradeStatisticsArr);
        console.log(json);
        this.examGradeService.updateGradeSubmissionDeadLine(json).then((message:any)=>{
          this.notify.success(message);
          this.$scope.showButton=false;
        });
      });
    }

    private dateChanged(examGrade:IExamGrade){
      this.$scope.showButton=true;
      examGrade.changed=true;
    }

    private cancel(){
      this.$scope.examGradeStatisticsArr = [];

      this.$scope.examGradeStatisticsArr=angular.copy(this.$scope.examGradeStatisticsArrTemp);
      this.$scope.showButton=false;
      this.initializeDatePickers();
    }

    private initializeDatePickers(){
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);
    }

    private getExamDates():void{

      var semester= this.$scope.semesterList[Utils.findIndex(this.$scope.semesterList,String(this.$scope.semesterId))];
      if(semester.status==Utils.SEMESTER_STATUS_ACTIVE)
        this.$scope.editable=true;
      else
        this.$scope.editable=false;

      var examType = + this.$scope.examType;
      this.$scope.examDate=null;
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

    private fetchDeadlineInformation():void{

    Utils.expandRightDiv();
      this.$scope.showButton=false
      this.$scope.examGradeStatisticsArr=[];
      this.$scope.examGradeStatisticsArrTemp=[];
      var examType=+this.$scope.examType;

        this.$scope.showLoader=true;
        this.examGradeService.getGradeSubmissionDeadLine(this.$scope.semesterId,examType,this.$scope.examDate).then((outputArr:Array<IExamGrade>)=>{

          if(outputArr.length==0){
            this.$scope.showLoader=false;
            this.notify.error("No relevant data found");
            this.$scope.showTable=false;
          }
          else{
            for(var i=0;i<outputArr.length;i++){
              outputArr[i].changed=false;
            }
            this.$scope.examGradeStatisticsArr=outputArr;
            this.$scope.examGradeStatisticsArrTemp = angular.copy(outputArr);
            this.$scope.showTable=true;
            this.$scope.showLoader=false;

            console.log(outputArr);

            this.initializeDatePickers();
          }


        });

    }


    private convertToJson():ng.IPromise<any>{

      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject=[];

      for(var i=0;i<this.$scope.examGradeStatisticsArr.length;i++){
        if(this.$scope.examGradeStatisticsArr[i].changed==true){
          this.$scope.examGradeStatisticsArr[i].changed=false;
          var item:any={};
          item['semesterId']=this.$scope.semesterId;
          item['courseId'] = this.$scope.examGradeStatisticsArr[i].courseId;
          item['examType'] = +this.$scope.examType;
          item['lastSubmissionDate'] = this.$scope.examGradeStatisticsArr[i].lastSubmissionDate;
          jsonObject.push(item);
        }
      }
      completeJson["entries"]=jsonObject;
      defer.resolve(completeJson);

      return defer.promise;
    }

  }

  UMS.controller('GradeSubmissionDeadLine',GradeSubmissionDeadLine);
}