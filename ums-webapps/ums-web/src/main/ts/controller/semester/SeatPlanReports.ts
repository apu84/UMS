 module ums{
  interface ISeatPlanReports extends ng.IScope{
    semesterList:Array<Semester>;
    semesterId:number;
    examType:any;
    examTypeNumeric:number;
    alertMessage:string;
    programsArr:Array<IProgram>;
    examRoutineArr:Array<ExamRoutineModel>;
    programType:string;
    examDate:string;


    isShowReportButtonClicked:boolean;
    isShowAlertFired:boolean;
    programTypeRequired:boolean;
    semesterIdRequired:boolean;
    examTypeRequired:boolean;
    showLoader:boolean;

    programTypeChanged:Function;
    semesterChanged:Function;
    examTypeChanged:Function;
    getSemesterInfo:Function;
    showReports:Function;
    closeAlertDialog:Function;
    getProgramInfo:Function;
    getExamRoutineInfo:Function;
    getAttendenceSheetReport:Function;
    getTopSheetReport:Function;
    getStickerReport:Function;

  }

  interface ISemester{
    id:number;
    name:string;
    startDate:string;
    status:number;
  }

  interface IRoom{
    id:number;
    roomNo:string;
    totalRow:number;
    totalColumn:number;
    capacity:number;
  }

  interface IProgram{
    id:number;
    shortName:string;
    longName:string;
    programType:number;
    departMentId:number;
  }

  interface IExamRoutine{
    semesterId:number;
    examTypeId:number;
    examDate:string;
    totalStudent:number;
    examDateOriginal:string;
  }

  class SeatPlanReports{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','examRoutineService','seatPlanService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISeatPlanReports,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,
                private semesterService:SemesterService,
                private examRoutineService:ExamRoutineService,
                private seatPlanService:SeatPlanService
                ) {

      $scope.examDate="";
      $scope.programTypeRequired=false;
      $scope.semesterIdRequired=false;
      $scope.examTypeRequired=false;
      $scope.isShowReportButtonClicked=false;
      $scope.showLoader=false;
      $scope.isShowAlertFired=false;
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.showReports = this.showReports.bind(this);
      $scope.closeAlertDialog=this.closeAlertDialog.bind(this);
      $scope.getProgramInfo = this.getProgramInfo.bind(this);
      $scope.programTypeChanged = this.programTypeChanged.bind(this);
      $scope.semesterChanged = this.semesterChanged.bind(this);
      $scope.examTypeChanged= this.examTypeChanged.bind(this);
      $scope.getAttendenceSheetReport = this.getAttendenceSheetReport.bind(this);
      $scope.getTopSheetReport = this.getTopSheetReport.bind(this);
      $scope.getStickerReport = this.getStickerReport.bind(this);

    }


    private closeAlertDialog():void{
      this.$scope.isShowAlertFired=false;
    }

    private programTypeChanged(){
      this.$scope.programTypeRequired=false;
      this.getExamRoutineInfo();

    }

    private semesterChanged(){
      this.$scope.semesterIdRequired=false;
      this.getExamRoutineInfo();
    }

    private examTypeChanged(){
      this.$scope.examTypeRequired=false;
      this.getExamRoutineInfo();
    }

    private showReports():void{
      if(this.$scope.semesterId==null){
        this.$scope.isShowAlertFired=true;
        this.$scope.alertMessage="Please select a semester!";
      }
      else if(this.$scope.examType==null || this.$scope.examType==""){
        this.$scope.isShowAlertFired=true;
        this.$scope.alertMessage="Please choose the exam type!";
      }
      else{
        this.$scope.examTypeNumeric=+this.$scope.examType;
        this.$scope.isShowReportButtonClicked=true;
      }
    }
    private getSemesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();


      this.semesterService.getSemesterByProgramType(this.$scope.programType).then((semesterArr:Array<Semester>)=>{
        this.$scope.semesterList = semesterArr;

        defer.resolve(this.$scope.semesterList);

      });


      return defer.promise;
    }

    private getAttendenceSheetReport():void{
      console.log("In the seat plan reports");
      console.log(this.$scope.examDate);
      var semesterId:number=this.$scope.semesterId;
      var programType:number = +this.$scope.programType;
      var examType:number = +this.$scope.examType;
      var examDate:string;
      if(this.$scope.examDate==""){
        examDate="NULL";
      }else{
        examDate=this.$scope.examDate;
      }
      this.$scope.showLoader=true;

      /*this.seatPlanService.getSeatPlanAttendanceSheetReport(programType,semesterId,examType,examDate).then((arr:any)=>{
        this.$scope.showLoader=false;
      });*/

      this.httpClient.get("/ums-webservice-academic/academic/seatplan/programType/"+programType+"/semesterId/"+semesterId+"/examType/"+examType+"/examDate/"+examDate,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            this.$scope.showLoader=false;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }



    private getTopSheetReport():void{
      console.log("In the seat plan reports");
      console.log(this.$scope.examDate);
      var semesterId:number=this.$scope.semesterId;
      var programType:number = +this.$scope.programType;
      var examType:number = +this.$scope.examType;
      var examDate:string;
      if(this.$scope.examDate==""){
        examDate="NULL";
      }else{
        examDate=this.$scope.examDate;
      }
      this.$scope.showLoader=true;

      /*this.seatPlanService.getSeatPlanAttendanceSheetReport(programType,semesterId,examType,examDate).then((arr:any)=>{
       this.$scope.showLoader=false;
       });*/

      this.httpClient.get("/ums-webservice-academic/academic/seatplan/topsheet/programType/"+programType+"/semesterId/"+semesterId+"/examType/"+examType+"/examDate/"+examDate,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            this.$scope.showLoader=false;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }



    private getStickerReport():void{
      console.log("In the seat plan reports");
      console.log(this.$scope.examDate);
      var semesterId:number=this.$scope.semesterId;
      var programType:number = +this.$scope.programType;
      var examType:number = +this.$scope.examType;
      var examDate:string;
      if(this.$scope.examDate==""){
        examDate="NULL";
      }else{
        examDate=this.$scope.examDate;
      }
      this.$scope.showLoader=true;

      /*this.seatPlanService.getSeatPlanAttendanceSheetReport(programType,semesterId,examType,examDate).then((arr:any)=>{
       this.$scope.showLoader=false;
       });*/

      this.httpClient.get("/ums-webservice-academic/academic/seatplan/sticker/programType/"+programType+"/semesterId/"+semesterId+"/examType/"+examType+"/examDate/"+examDate,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            this.$scope.showLoader=false;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }

    private getProgramInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/program/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.programsArr = json.entries;

            defer.resolve(this.$scope.programsArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getExamRoutineInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var examRoiutineList:Array<ExamRoutineModel>=[];
      var examType=+this.$scope.examType;
      console.log("In the exam routine info");
      if(this.$scope.programType!=null && this.$scope.semesterId!=null && this.$scope.examType!=null){
        console.log("into the main section");
        this.examRoutineService.getExamRoutineDates(this.$scope.semesterId,examType).then((examRoutineArr:Array<ExamRoutineModel>)=>{
          this.$scope.examRoutineArr = examRoutineArr;
          console.log(examRoutineArr);
        });
        defer.resolve(this.$scope.examRoutineArr);
      }

      return defer.promise;
    }


  }


  UMS.controller("SeatPlanReports",SeatPlanReports);
}