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
    application:boolean;
    savedOrSubmitted:number;
    actors:any;
    actions:any;
    semesterWithdraw:ISemesterWithdraw;
    semesterWithdrawLog: ISemesterWithdrawLog;
    parameterSetting: IParameterSetting;
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
          cause:"",
          status:""
        }

        $scope.editApplication=false;
        $scope.savedOrSubmitted=0;
        $scope.showLoginPanel= true;
        $scope.showSemesterWithdrawApplicationPanel = false;
        $scope.editButtonShow = false;
        $scope.application = false;
        $scope.applicationTypeOptions = appConstants.applicationTypes;
        $scope.actors = appConstants.actors;
        $scope.actions = appConstants.actions;
        $scope.saveApplication = this.saveApplication.bind(this);
        $scope.edit = this.edit.bind(this);
    }

    private initialize():void{
      this.getStudentsInformation().then((studentArr:Array<Student>)=>{
          this.getParameterSetting().then((parameterArr:Array<IParameterSetting>)=>{
            var date = new Date();
            var parameterSettingStartDate = new Date(this.$scope.parameterSetting.startDate);
            var parameterSettingEndDate = new Date(this.$scope.parameterSetting.endDate);

            if(date >= parameterSettingStartDate || date <= parameterSettingEndDate){
                this.getStudentsSemesterWithdrawApplication().then((applicationArr:Array<ISemesterWithdraw>)=>{
                    if(applicationArr.length== 0){
                        this.$scope.application = true;
                    }else{
                        this.$scope.application = true;
                        if(this.$scope.semesterWithdraw.status==0){
                          this.$scope.editButtonShow = true;
                        }
                    }
                });
            }else{
              this.showApplicationDateEndMessage();
            }
          });
      });
    }

    private showApplicationDateEndMessage():void{

    }

    private saveApplication():void{
      var jsons = this.convertToJsonForSemesterWithdraw();
      this.httpClient.post('academic/semesterWithdrawal/', jsons, 'application/json')
          .success(() => {

            }).error((data) => {
      });

    }

    private updateApplication():void{
      var jsons = this.convertToJsonForSemesterWithdraw();
      this.httpClient.put('academic/semesterWithdrawal/'+this.$scope.semesterWithdraw.id,jsons,'application/json')
          .success(() => {


          }).error((data) => {
      });
    }

    private edit():void{
      this.$scope.editApplication = true;
    }



    private getStudentsInformation():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-common/academic/student/getStudentInfoById', 'application/json',
          (json:any, etag:string) => {
            this.$scope.student = json;

            defer.resolve(this.$scope.student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getParameterSetting():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-common/academic/parameterSetting/parameter/9/semester/'+this.$scope.student.semesterId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.parameterSetting = json;

            defer.resolve(this.$scope.parameterSetting);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getStudentsSemesterWithdrawApplication():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-common/academic/semesterWithdraw/studentInfo/semester/'+this.$scope.student.semesterId+'/year/'+this.$scope.student.year+'/semester/'+this.$scope.student.academicSemester, 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterWithdraw = json;

            defer.resolve(this.$scope.semesterWithdraw);
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
      semesterWithdraw["year"] = this.$scope.student.year;
      semesterWithdraw["semester"] = this.$scope.student.academicSemester;
      semesterWithdraw["cause"] = this.$scope.data.cause;
      semesterWithdraw["status"] = this.$scope.data.status;
      semesterWithdraw["applicationDate"] = "";
    }

  }
  UMS.controller("ApplicationsStudent",ApplicationsStudent);
}