module ums{

  interface ISemesterWithdrawAppEmpScope extends  ng.IScope{
    semesterWithdrawArr:Array<SemesterWithdraw>;
    semesterWithdraw:SemesterWithdraw;
    tempSemesterWithdraw:SemesterWithdraw;
    semesterWithdrawLogArr:Array<ISemesterWithdrawLog>;
    semesterWithdrawLog:ISemesterWithdrawLog;
    employee:Employee;
    student:Student;
    semester:ISemester;
    data:any;
    comments:any;

    initialize:Function;
    getEmployeeInformation:Function;
    getSemesterWithdrawApplications:Function;
    getSemesterWithdrawApplicatinsAll:Function;
    getSemesterInfo:Function;
    getSemesterWithdrawLogs:Function;
    saveApplicationLog:Function;
    getStudentInfo:Function;
    details:Function;
    closeWindow:Function;
    updateApplication:Function;
    approve:Function;
    reject:Function;
    saveAndClose:Function;
    updateSemesterWithdrawalArray:Function;

    detailButtonCliecked:boolean;
    approveButton:boolean;
    rejectButton:boolean;
    showComment:boolean;
    commentEdit:boolean;
    saveAndCloseButton:boolean;
  }

  interface SemesterWithdraw{
    id:number;
    semesterId:number;
    programId:number;
    cause:string;
    studentId:string;
    studentName:string;
    year:number;
    semester:number;
    status:number;
    tempStatus:number;
    appDate:string;
    comments:string;
  }

  interface ISemester{
    id:number;
    name:string;
  }
  export class SemesterWithdrawAppEmp{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterWithdrawAppEmpScope,private $q:ng.IQService, private notify: Notify,private $sce:ng.ISCEService ) {
      $scope.data={
        action:"",
        comments:"",
        status:""
      }
      $scope.detailButtonCliecked=false;
      $scope.approveButton= false;
      $scope.rejectButton = false;
      $scope.showComment = false;
      $scope.commentEdit = false;
      $scope.saveAndCloseButton=false;
      //this.initialize();
      $scope.initialize=this.initialize.bind(this);
      $scope.getEmployeeInformation = this.getEmployeeInformation.bind(this);
      $scope.getSemesterWithdrawApplications = this.getSemesterWithdrawApplications.bind(this);
      $scope.getSemesterWithdrawApplicatinsAll = this.getSemesterWithdrawApplicationsAll.bind(this);
      $scope.getSemesterWithdrawLogs  = this.getSemesterWithdrawLogs.bind(this);
      $scope.getStudentInfo = this.getStudentInfo.bind(this);
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.saveApplicationLog = this.saveApplicationLog.bind(this);
      $scope.updateApplication = this.updateApplication.bind(this);


      $scope.details = this.details.bind(this);
      $scope.closeWindow = this.closeWindow.bind(this);
      $scope.approve = this.approve.bind(this);
      $scope.reject = this.reject.bind(this);
      $scope.updateSemesterWithdrawalArray = this.updateSemesterWithdrawalArray.bind(this);
      $scope.saveAndClose = this.saveAndClose.bind(this);

    }

    private initialize():void{
      this.getEmployeeInformation().then((employeeArr:Array<Employee>)=>{
        if(this.$scope.employee[0].designation=='101'){
          this.getSemesterWithdrawApplications().then((semesterWithdrawAppArr:Array<SemesterWithdraw>)=>{

          });
        }else{
          this.getSemesterWithdrawApplicationsAll().then((semesterWithdrawAppArr:Array<SemesterWithdraw>)=>{

          });
        }
      });
    }
    //check the status
    private details(semesterWith:SemesterWithdraw):void{
      this.$scope.comments={};
      this.$scope.tempSemesterWithdraw = semesterWith;
      this.$scope.detailButtonCliecked = true;
      this.$scope.data.status = "";
      //console.log(this.$scope.employee[0].status);
      //console.log(this.$scope.employee[0].designation);
      this.$scope.approveButton=false;
      this.$scope.rejectButton = false;
      if(this.$scope.employee[0].designation == 101 && this.$scope.tempSemesterWithdraw.status!=4 && this.$scope.tempSemesterWithdraw.status!=Number(5) && this.$scope.tempSemesterWithdraw.status!=3 && this.$scope.tempSemesterWithdraw.status!=Number(2) ){
        this.$scope.approveButton=true;
        this.$scope.rejectButton = true;
        console.log("I am in");
      }
      if(this.$scope.employee[0].designation!=101 && this.$scope.tempSemesterWithdraw.status==2 && this.$scope.tempSemesterWithdraw.status!=Number(4) && this.$scope.tempSemesterWithdraw.status!=Number(5)){

        this.$scope.approveButton = true;
        this.$scope.rejectButton= true;
        console.log("I dont want");
      }
      this.$scope.comments=semesterWith.comments;
      this.getStudentInfo().then((studentInfoArr:Array<Student>)=>{
        this.getSemesterInfo().then((semesterInfoArr:Array<Student>)=>{
          /*if(this.$scope.tempSemesterWithdraw.status==1){
           this.$scope.data.status = "Application Submitted";
           }else if(this.$scope.tempSemesterWithdraw.status=2){
           this.$scope.data.status="Application Approved By Head";
           }else if(this.$scope.tempSemesterWithdraw.status=3){
           this.$scope.data.status="Application Rejected By Head";
           }else if(this.$scope.tempSemesterWithdraw.status=4){
           this.$scope.data.status="Application Approved By Registrar";
           }else{
           this.$scope.data.status="Application Rejected By Registrar";
           }*/
        });
      });
    }

    private approve():void{
      if(this.$scope.employee[0].designation==101){
        this.$scope.tempSemesterWithdraw.tempStatus=2;
        this.$scope.commentEdit=true;
        this.$scope.approveButton=false;
        this.$scope.rejectButton = false;
        this.$scope.saveAndCloseButton=true;
      }
      else{
        this.$scope.tempSemesterWithdraw.tempStatus=4;
        this.$scope.commentEdit=true;
        this.$scope.approveButton=false;
        this.$scope.rejectButton = false;
        this.$scope.saveAndCloseButton=true;
      }
    }
    private reject():void{
      if(this.$scope.employee[0].designation==101){
        this.$scope.tempSemesterWithdraw.tempStatus=3;

        this.$scope.commentEdit=true;
        this.$scope.approveButton=false;
        this.$scope.rejectButton = false;
        this.$scope.saveAndCloseButton=true;
      }
      else{
        this.$scope.tempSemesterWithdraw.tempStatus=5;

        this.$scope.commentEdit=true;
        this.$scope.approveButton=false;
        this.$scope.rejectButton = false;
        this.$scope.saveAndCloseButton=true;
      }
    }

    private updateSemesterWithdrawalArray():void{
      for(var i=0;i<this.$scope.semesterWithdrawArr.length;i++){
        if(this.$scope.semesterWithdrawArr[i].id == this.$scope.tempSemesterWithdraw.id){
          this.$scope.semesterWithdrawArr[i] = this.$scope.tempSemesterWithdraw;
          break;
        }
      }
    }

    private saveAndClose():void{
      this.$scope.tempSemesterWithdraw.status = this.$scope.tempSemesterWithdraw.tempStatus;
      this.saveApplicationLog();
      this.updateApplication();
      this.closeWindow();
    }

    private closeWindow():void{
      this.$scope.approveButton=true;
      this.$scope.rejectButton = true;
      this.$scope.saveAndCloseButton=false;
      this.$scope.commentEdit=false;
      this.$scope.detailButtonCliecked=false;
    }

    private getEmployeeInformation():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/employee/employeeById', 'application/json',
          (json:any, etag:string) => {
            this.$scope.employee = json.entries;
            defer.resolve(this.$scope.employee);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSemesterWithdrawApplications():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/semesterWithdraw/deptId/'+this.$scope.employee[0].deptOfficeId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterWithdrawArr = json.entries;
            defer.resolve(this.$scope.semesterWithdrawArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSemesterWithdrawApplicationsAll():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/semesterWithdraw/all', 'application/json',
          (json:any, etag:string) => {0
            this.$scope.semesterWithdrawArr = json.entries;
            defer.resolve(this.$scope.semesterWithdrawArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
    private getSemesterWithdrawLogs():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/semesterWithdrawLog/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterWithdrawLogArr = json.entries;
            defer.resolve(this.$scope.semesterWithdrawArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
    private getStudentInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/student/'+this.$scope.tempSemesterWithdraw.studentId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.student = json;

            defer.resolve(this.$scope.student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSemesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/semester/'+this.$scope.tempSemesterWithdraw.semesterId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.semester = json;
            defer.resolve(this.$scope.semester);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private saveApplicationLog():void{
      var jsons = this.convertToJsonForSemesterWithdraw();
      this.httpClient.post('academic/semesterWithdrawalLog/', jsons, 'application/json')
          .success(() => {

          }).error((data) => {
      });

    }



    private updateApplication():void{
      var jsons = this.convertToJsonForSemesterWithdrawForUpdateOrSubmit();
      this.httpClient.put('academic/semesterWithdraw/'+this.$scope.tempSemesterWithdraw.id,jsons,'application/json')
          .success(() => {
            this.updateSemesterWithdrawalArray();
            this.$scope.tempSemesterWithdraw.status = this.$scope.tempSemesterWithdraw.tempStatus;
          }).error((data) => {
      });
    }

    private convertToJsonForSemesterWithdrawForUpdateOrSubmit(){
      var cause:any={};
      var semesterWithdraw={};
      semesterWithdraw["id"]=this.$scope.tempSemesterWithdraw.id;
      semesterWithdraw["programId"]=this.$scope.tempSemesterWithdraw.programId;
      semesterWithdraw["semesterId"] = this.$scope.tempSemesterWithdraw.semesterId;
      semesterWithdraw["studentId"] = this.$scope.tempSemesterWithdraw.studentId
      semesterWithdraw["year"] = this.$scope.tempSemesterWithdraw.year;
      semesterWithdraw["semester"] = this.$scope.tempSemesterWithdraw.semester;
      semesterWithdraw["cause"] = this.$scope.tempSemesterWithdraw.cause;
      semesterWithdraw["status"] = this.$scope.tempSemesterWithdraw.status;
      semesterWithdraw["applicationDate"] = this.$scope.tempSemesterWithdraw.appDate;
      var comments = this.$sce.getTrustedHtml(this.$scope.tempSemesterWithdraw.comments);
      cause = this.$sce.getTrustedHtml(this.$scope.data.comments);

      //console.log(str);
      semesterWithdraw["comments"] = this.$scope.data.comments;
      return semesterWithdraw;
    }


    private convertToJsonForSemesterWithdraw(){
      var semesterWithdrawLog={};
      semesterWithdrawLog["id"]=0;
      semesterWithdrawLog["semesterWithdrawId"]=this.$scope.tempSemesterWithdraw.id;
      semesterWithdrawLog["employeeId"] = this.$scope.employee[0].id;
      semesterWithdrawLog["action"] = this.$scope.tempSemesterWithdraw.status;
      semesterWithdrawLog["eventDateTime"] = "";
      return semesterWithdrawLog;
    }
  }

  UMS.controller("SemesterWithdrawAppEmp",SemesterWithdrawAppEmp);
}