module ums{

  interface IApplicationScope extends ng.IScope{

    data:any;
    student:Student;
    semester:ISemester;
    getStudentInfo:Function;
    getStudentAndSemesterInfo:Function;
    saveApplication: Function;
    getLogInfoForStudent: Function;
    edit: Function;
    applicationTypeOptions:any;
    typeSelector:any;
    applicationType: number;
    showSemesterWithdrawApplicationPanel:boolean;
    showLoginPanel:boolean;
    editApplication:boolean;
    editButtonShow: boolean;
    savedOrSubmitted:number;
    actors:any;
    actions:any;
    semesterWithdraw:ISemesterWithdraw;
    semesterWithdrawLog: ISemesterWithdrawLog;
  }





  interface ISemester{
    id:string;
    name:string;
    startDate:string;
    endDate:string;
    status:number;
  }
  export class ApplicationsStudent{

    public static $inject = ['appConstants','HttpClient','$scope','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IApplicationScope,private $q:ng.IQService ) {
        $scope.data={
          studentId:"",
          cause:""
        }
        $scope.editApplication=false;
        $scope.savedOrSubmitted=0;
        $scope.showLoginPanel= true;
        $scope.showSemesterWithdrawApplicationPanel = false;
        $scope.editButtonShow = false;
        $scope.applicationTypeOptions = appConstants.applicationTypes;
        $scope.actors = appConstants.actors;
        $scope.actions = appConstants.actions;
        $scope.getStudentAndSemesterInfo = this.getStudentAndSemesterInfo.bind(this);
        $scope.saveApplication = this.saveApplication.bind(this);
        $scope.edit = this.edit.bind(this);
    }

    private saveApplication():void{
      var jsons = this.convertToJsonForSemesterWithdrawLog();
      this.httpClient.post('academic/semesterWithdrawalLog/', jsons, 'application/json')
          .success(() => {

            });

    }

    private edit():void{
      this.$scope.editApplication = true;
    }

    private getStudentAndSemesterInfo():void{
      this.studentInfo().then((studentArr:Array<Student>)=>{
        this.semesterInfo().then((semesterArr:Array<ISemester>)=>{
          if(this.$scope.applicationType==1){
            this.$scope.showLoginPanel = false;
            this.$scope.showSemesterWithdrawApplicationPanel = true;
            this.getLogInfoForStudent().then((logArr:Array<ISemesterWithdrawLog>)=>{
              if(this.$scope.semesterWithdrawLog == null){
                this.$scope.editApplication=true;
              }
              else{
                if(this.$scope.semesterWithdrawLog.action==0){
                  this.$scope.editButtonShow=true;
                }
              }
            });
          }
        });

      });
    }

    private studentInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      console.log(this.$scope.data.studentId);

      var studentArr:Student;
      this.httpClient.get('/ums-webservice-common/academic/student/'+this.$scope.data.studentId, 'application/json',
          (json:any, etag:string) => {
            studentArr = json;

            this.$scope.student = studentArr;

            defer.resolve(studentArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private semesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var semesterArr: ISemester;
      this.httpClient.get('/ums-webservice-common/academic/semester/'+this.$scope.student.semesterId, 'application/json',
          (json:any, etag:string) => {
            semesterArr = json;
            console.log("------------------");
            console.log(semesterArr);
            this.$scope.semester = semesterArr;
            defer.resolve(semesterArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getLogInfoForStudent():ng.IPromise<any>{
      var defer = this.$q.defer();
      var logArr: ISemesterWithdrawLog;
      this.httpClient.get('/ums-webservice-common/academic/semesterWithdrawalLog/studentId/'+this.$scope.student.id+'/semesterId/'+this.$scope.student.semesterId, 'application/json',
          (json:any, etag:string) => {
            logArr = json;
            this.$scope.semesterWithdrawLog = logArr;
            defer.resolve(logArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private convertToJsonForSemesterWithdraw(){
      var semesterWithdraw={};
      semesterWithdraw["id"]="";
      semesterWithdraw["programId"]=this.$scope.student.programId;
      semesterWithdraw["semesterId"] = this.$scope.student.semesterId;
      semesterWithdraw["cause"] = this.$scope.data.cause;
      semesterWithdraw["applicationDate"] = "";
    }

    private convertToJsonForSemesterWithdrawLog(){
      var log={};
      log["id"] = "";
      log["semesterWithdrawalId"] = this.$scope.semesterWithdrawLog.semesterWithdrawId;
      log["actor"]=this.$scope.semesterWithdrawLog.actor;
      log["actorId"]=this.$scope.semesterWithdrawLog.actorId;
      log["action"]=this.$scope.savedOrSubmitted;
      log["comments"] = "";
    }
  }
  UMS.controller("ApplicationsStudent",ApplicationsStudent);
}