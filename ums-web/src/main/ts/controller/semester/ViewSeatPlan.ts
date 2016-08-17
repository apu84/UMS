module ums{
  /*
  * TODO: check in the student records, whether the student is registered in regular or cci or both.
  * TODO: add CIVIL seat plan functionality.
  * */

  /*Prerequisites:
  * For viewing seat plan, a student must be enrolled in the current semester.
  * If the controller office has prepared the seat plan, only then, students will be able to view.
  * If the student has CCI exams then, students will be able to view.
  *
  * Current semester will be checked, so, student_record table must contain student record of the semester for CCI also.*/

  interface IViewSeatPlan extends ng.IScope{
    student:Student;
    seatPlanArrLength:number;
    applicationCCILength:number;
    recordNotFoundMessage:string;
    seatPlanArr:ISeatPlan;
    roomId:number
    classRoom:IClassRoom;
    /*Both colIteration and rowIteration will be used
    * for looping purpose, as angular filter is trouble some*/
    colIterationArr:Array<number>;
    rowIterationArr:Array<number>;
    applicationCCIArr:Array<IApplicationCCI>;

    //booleans
    studentRecordFound:boolean;
    semesterFinalSelected:boolean;
    civilSelected:boolean;
    cciSelected:boolean;
    seatPlanStructure:boolean;
    isNoSeatPlanInfo:boolean;
    isNoCCIInfo:boolean;


    init:Function;
    getStudentInfo:Function;
    getStudentRecordInfo:Function;
    getSeatPlanInfoFinalExam:Function;
    getClassRoomInfo:Function;
    showSemesterFinalSeatPlan:Function;
    showCivilSpecialSeatPlan:Function;
    showCCISeatPlan:Function;
    getApplicationCCIInfo:Function;
    viewSeatPlan:Function;
    createRoomStructure:Function;
    goBack:Function;
    getSeatPlanInfoCCIExam:Function;

  }

  interface ISeatPlan{
    id:number;
    roomId:number;
    roomNo:string;
    rowNo:number;
    colNo:number;
    studentId:string;
    examType:number;
    semesterId:number;
    groupNo:number;
  }

  interface IClassRoom{
    totalRow:number;
    totalColumn:number;
  }

  interface IApplicationCCI{
    examDate:string;
    courseNo:string;
    applicationType:number;
    roomNo:string;
    roomId:number;
  }

  class ViewSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IViewSeatPlan,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      //$scope.studentRecordFound=false;

      $scope.semesterFinalSelected=true;
      $scope.civilSelected=false;
      $scope.cciSelected=false;
      $scope.seatPlanArr;
      $scope.init = this.init.bind(this);
      $scope.getStudentInfo = this.getStudentInfo.bind(this);
      $scope.getStudentRecordInfo = this.getStudentRecordInfo.bind(this);
      $scope.getSeatPlanInfoFinalExam = this.getSeatPlanInfoFinalExam.bind(this);
      $scope.getClassRoomInfo = this.getClassRoomInfo.bind(this);
      $scope.showSemesterFinalSeatPlan = this.showSemesterFinalSeatPlan.bind(this);
      $scope.getApplicationCCIInfo = this.getApplicationCCIInfo.bind(this);
      $scope.showCCISeatPlan = this.showCCISeatPlan.bind(this);
      $scope.createRoomStructure = this.createRoomStructure.bind(this);
      $scope.viewSeatPlan = this.viewSeatPlan.bind(this);
      $scope.goBack = this.goBack.bind(this);
      $scope.getSeatPlanInfoCCIExam = this.getSeatPlanInfoCCIExam.bind(this);

    }

    private init():void{
      /*First retrieve the student information*/
      this.getStudentInfo().then((studentInfo:Student)=>{
        this.getStudentRecordInfo().then((studentRecords:any)=>{
          if(studentRecords.length>0){

            this.$scope.studentRecordFound=true;
            this.showSemesterFinalSeatPlan();
          }else{
            this.$scope.studentRecordFound=false;
            this.$scope.recordNotFoundMessage="Not Yet Registered for the semester!";
          }
        });
      });
    }

    private goBack(){
      this.$scope.seatPlanStructure=false;
      this.$scope.cciSelected=true;
    }

    private createRoomStructure(roomId:number){

      this.$scope.roomId = roomId;

      this.getClassRoomInfo(roomId).then((roomArr:Array<IClassRoom>)=>{
        this.$scope.classRoom = roomArr[0];
        console.log(this.$scope.classRoom);
        this.$scope.colIterationArr=[];
        this.$scope.rowIterationArr=[];
        for(var i=1;i<=this.$scope.classRoom.totalColumn;i++){
          this.$scope.colIterationArr.push(i);
        }
        for(var i=1;i<=this.$scope.classRoom.totalRow;i++){
          this.$scope.rowIterationArr.push(i);
        }
      });
    }


    private showSemesterFinalSeatPlan():void{
      this.$scope.semesterFinalSelected=true;
      this.$scope.seatPlanStructure=true;
      this.$scope.cciSelected=false;
      this.$scope.civilSelected=false;
      this.getSeatPlanInfoFinalExam().then((seatPlanArr:Array<ISeatPlan>)=>{
        this.$scope.seatPlanArr = seatPlanArr[0];
        this.$scope.seatPlanArrLength=seatPlanArr.length;
        console.log(this.$scope.seatPlanArr);

        this.createRoomStructure(this.$scope.seatPlanArr.roomId);

      });
    }

    private showCCISeatPlan():void{
      this.$scope.cciSelected=true;
      this.$scope.seatPlanStructure=false;
      this.$scope.civilSelected=false;
      this.$scope.semesterFinalSelected=false;
      this.getApplicationCCIInfo().then((applicationCCI:Array<IApplicationCCI>)=>{
        this.$scope.applicationCCIArr=applicationCCI;
        this.$scope.applicationCCILength=applicationCCI.length;
      });
    }

    private viewSeatPlan(roomId:number,examDate:string){
      this.$scope.seatPlanStructure=true;
      this.$scope.cciSelected=false;
      this.getSeatPlanInfoCCIExam(examDate).then((seatPlanArr:Array<ISeatPlan>)=>{
        console.log("In the view section");
        console.log(seatPlanArr);
        this.$scope.seatPlanArrLength = seatPlanArr.length;
        this.$scope.seatPlanArr = seatPlanArr[0];
        console.log(this.$scope.seatPlanArr);

        this.createRoomStructure(roomId);
      });

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
      var studentRecord:any={};

      this.httpClient.get("/ums-webservice-common/academic/studentrecord/student/"+this.$scope.student[0].id+
              "/semesterId/"+this.$scope.student[0].semesterId+"/year/"+this.$scope.student[0].year+"/semester/"+this.$scope.student[0].academicSemester,
              'application/json',
          (json:any,etag:string)=>{

            studentRecord=json.entries;
            defer.resolve(studentRecord);
          },(response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    private getSeatPlanInfoFinalExam():ng.IPromise<any>{
      var defer = this.$q.defer();
      var seatPlanArr:Array<ISeatPlan>=[];
      this.httpClient.get("/ums-webservice-common/academic/seatplan/studentId/"+this.$scope.student[0].id+"/semesterId/"+this.$scope.student[0].semesterId,'application/json',
          (json:any,etag:string)=>{
            seatPlanArr = json.entries;
            console.log("*** seat plan information****");
            console.log(seatPlanArr);
            defer.resolve(seatPlanArr);
          },(response:ng.IHttpPromiseCallbackArg<any>)=>{
              console.error(response);
          });
      return defer.promise;
    }

    private getSeatPlanInfoCCIExam(examDate:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      var seatPlanArr:Array<ISeatPlan>=[];
      this.httpClient.get("/ums-webservice-common/academic/seatplan/studentId/"+this.$scope.student[0].id+"/semesterId/"+this.$scope.student[0].semesterId+'/examDate/'+examDate,'application/json',
          (json:any,etag:string)=>{
            seatPlanArr = json.entries;
            console.log("*** seat plan CCIIIII****");
            console.log(seatPlanArr);
            defer.resolve(seatPlanArr);
          },(response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }

    private getClassRoomInfo(roomId:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var classRoom:Array<IClassRoom>=[];
      this.httpClient.get("/ums-webservice-common/academic/classroom/roomId/"+roomId,'application/json',
          (json:any,etag:string)=>{
            classRoom=json.entries;
            defer.resolve(classRoom);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }


    private getApplicationCCIInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var applicationCCI:Array<IApplicationCCI>=[];
      this.httpClient.get("/ums-webservice-common/academic/applicationCCI/seatPlanView","application/json",
          (json:any,etag:string)=>{
            applicationCCI=json.entries;
            console.log(applicationCCI);
            defer.resolve(applicationCCI);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }



  }

  UMS.controller("ViewSeatPlan",ViewSeatPlan);
}