module ums{

  interface IApplicationScope extends ng.IScope{

    data:any;
    student:Student;
    semester:ISemester;
    getStudentInfo:Function;
    getStudentAndSemesterInfo:Function;
    saveApplication: Function;
    getLogInfoForStudent: Function;
    cancel:Function;
    edit: Function;
    updateApplication:Function;
    deleteApplication:Function;
    initialize:Function;
    submit:Function;
    applicationTypeOptions:any;
    typeSelector:any;
    applicationType: number;
    showSemesterWithdrawApplicationPanel:boolean;
    showLoginPanel:boolean;
    editApplication:boolean;
    editButtonShow: boolean;
    editButton: boolean;
    deleteButton : boolean;
    saveButton:boolean;
    cancelButton: boolean;
    submitButton:boolean;
    updateButton: boolean;
    submitButtonClicked: boolean;
    application:boolean;
    dateEnd: boolean;
    textEditable: boolean;
    savedOrSubmitted:number;
    cause:any;
    actors:any;
    actions:any;
    semesterWithdraw:SemesterWithdraw;
    semesterWithdrawArr: Array<SemesterWithdraw>;
    //semesterWithdrawLog: ISemesterWithdrawLog;
    parameterSetting: IParameterSetting;
  }



  interface SemesterWithdraw{
    id:number;
    semesterId:number;
    programId:number;
    cause:string;
    /*tempCause:string;*/
    studentId:string;
    studentName:string;
    year:number;
    semester:number;
    status:number;
    comments:string;
  }


  interface ISemester{
    id:string;
    name:string;
    startDate:string;
    endDate:string;
    status:number;
  }
  export class SemesterWithdrawAppStd{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IApplicationScope,private $q:ng.IQService, private notify: Notify,private $sce:ng.ISCEService ) {
        $scope.data={
          studentId:"",
          cause:"",
          status:""
        }
        //$scope.initialize = this.initialize.bind(this);
        this.initialize();
        //this.$scope.initialize = this.initialize.bind(this);
        $scope.cause="";
        $scope.editApplication=false;
        $scope.submitButtonClicked = false;
        $scope.submitButton = false;
        $scope.saveButton = false;
        $scope.deleteButton = false;
        $scope.cancelButton = false;
        $scope.savedOrSubmitted=0;
        $scope.showLoginPanel= true;
        $scope.dateEnd = false;
        $scope.editButton = false;
        $scope.showSemesterWithdrawApplicationPanel = false;
        $scope.editButtonShow = false;
        $scope.application = false;
        $scope.textEditable = false;
        $scope.applicationTypeOptions = appConstants.applicationTypes;
        $scope.actors = appConstants.actors;
        $scope.actions = appConstants.actions;
        $scope.initialize = this.initialize.bind(this);
        $scope.saveApplication = this.saveApplication.bind(this);
        $scope.edit = this.edit.bind(this);
        $scope.cancel = this.cancel.bind(this) ;
        $scope.updateApplication = this.updateApplication.bind(this);
        $scope.deleteApplication = this.deleteApplication.bind(this);
        $scope.submit = this.submit.bind(this);
    }

    private initialize():void{
      this.$scope.cause={};
      this.getStudentsInformation().then((studentArr:Array<Student>)=>{
          this.getParameterSetting().then((parameterArr:Array<IParameterSetting>)=>{
            var date = new Date();
            var parameterSettingStartDate = new Date(this.$scope.parameterSetting[0].startDate);
            var endDate = new Date(this.$scope.parameterSetting[0].endDate);

            if(date >= parameterSettingStartDate || date <= endDate){
                this.getSemesterWithdrawApplications().then((applicationArr:Array<SemesterWithdraw>)=>{

                  for(var i=0;i<this.$scope.semesterWithdrawArr.length;i++){

                    if(this.$scope.semesterWithdrawArr[i].studentId == this.$scope.student[0].id && this.$scope.semesterWithdrawArr[i].year == this.$scope.student[0].year && this.$scope.semesterWithdrawArr[i].semester== this.$scope.student[0].academicSemester){
                      this.$scope.application = true;
                      this.$scope.editButton = true;
                      this.$scope.textEditable = false;
                      this.$scope.semesterWithdraw = this.$scope.semesterWithdrawArr[i];

                      if(this.$scope.semesterWithdrawArr[i].status == 0){
                        this.$scope.data.status = "Saved";
                        this.$scope.editButton = true;
                        this.$scope.submitButton = true;
                        this.$scope.deleteButton=true;
                      }
                      else if(this.$scope.semesterWithdrawArr[i].status==2){
                        this.$scope.data.status = "Waiting for Registrar's approval"
                        this.$scope.saveButton=false;
                        this.$scope.deleteButton=false;
                        this.$scope.updateButton=false;
                        this.$scope.editButton=false;
                        this.$scope.submitButton=false;
                        this.$scope.textEditable=false;
                      }
                      else if(this.$scope.semesterWithdrawArr[i].status==3){
                        this.$scope.data.status = "Rejected By Head"
                        this.$scope.saveButton=false;
                        this.$scope.deleteButton=false;
                        this.$scope.updateButton=false;
                        this.$scope.editButton=false;
                        this.$scope.submitButton=false;
                        this.$scope.textEditable=false;
                      }
                      else if(this.$scope.semesterWithdrawArr[i].status==4){
                        this.$scope.data.status = "Accepted By Registrar"
                        this.$scope.saveButton=false;
                        this.$scope.deleteButton=false;
                        this.$scope.updateButton=false;
                        this.$scope.editButton=false;
                        this.$scope.submitButton=false;
                        this.$scope.textEditable=false;
                      }
                      else if(this.$scope.semesterWithdrawArr[i].status==5){
                        this.$scope.data.status = "Rejected By Registrar"
                        this.$scope.saveButton=false;
                        this.$scope.deleteButton=false;
                        this.$scope.updateButton=false;
                        this.$scope.editButton=false;
                        this.$scope.submitButton=false;
                        this.$scope.textEditable=false;
                      }
                      else{
                        this.$scope.data.status = "Submitted"
                        this.$scope.saveButton=false;
                        this.$scope.deleteButton=false;
                        this.$scope.updateButton=false;
                        this.$scope.editButton=false;
                        this.$scope.submitButton=false;
                        this.$scope.textEditable=false;
                      }
                      this.$scope.cause = this.$scope.semesterWithdraw.cause;

                      break;
                    }
                  }
                  if(this.$scope.application != true){
                    this.$scope.application = true;
                    this.$scope.editApplication = false;
                    this.$scope.editButton = false;
                    this.$scope.saveButton=true;
                    this.$scope.cancelButton = true;
                    this.$scope.textEditable=true;
                    this.$scope.data.status = "Application not yet created.";
                  }


                });
            }else{
              this.$scope.dateEnd = true;
              this.showApplicationDateEndMessage();
            }
          });
      });

    }

    private showApplicationDateEndMessage():void{

    }

    private saveApplication():void{
      this.$scope.data.status = 0;
      var jsons = this.convertToJsonForSemesterWithdraw();
      this.httpClient.post('academic/semesterWithdraw/', jsons, 'application/json')
          .success(() => {
              this.initialize();
              this.$scope.editButton = true;
              this.$scope.submitButton = true;
              this.$scope.deleteButton = true;
              this.$scope.saveButton = false;
            }).error((data) => {
      });

    }

    private submit():void{
      this.$scope.submitButtonClicked = true;
      var jsons = this.convertToJsonForSemesterWithdrawForUpdateOrSubmit();
      this.httpClient.put('academic/semesterWithdraw/'+this.$scope.semesterWithdraw.id,jsons,'application/json')
          .success(() => {
            //this.$scope.semesterWithdraw.cause = this.$scope.data.cause;
            this.$scope.semesterWithdraw.status = 1;
            this.$scope.data.status="Application Successfully Submitted";
            this.$scope.saveButton=false;
            this.$scope.deleteButton=false;
            this.$scope.editButton = false;
            this.$scope.updateButton = false;
            this.$scope.submitButton=false;
          }).error((data) => {
      });
    }

    private deleteApplication():void{
      this.httpClient.delete('academic/semesterWithdraw/'+this.$scope.semesterWithdraw.id)
      .success(()=>{
        this.$scope.data.status = "Application Deleted Successfully";
        this.$scope.data.cause = "";
        this.$scope.semesterWithdraw.cause = "";
        this.$scope.textEditable=true;
        this.$scope.saveButton = true;
        this.$scope.cancelButton = true;
        this.$scope.editButton=false;
        this.$scope.submitButton=false;
        this.$scope.deleteButton = false;
      });
    }
    private updateApplication():void{
      var jsons = this.convertToJsonForSemesterWithdrawForUpdateOrSubmit();
      this.httpClient.put('academic/semesterWithdraw/'+this.$scope.semesterWithdraw.id,jsons,'application/json')
          .success(() => {
            if(this.$scope.semesterWithdraw.status==1){
              this.$scope.editButton=false;
              this.$scope.saveButton=false;
              this.$scope.textEditable=false;
              this.$scope.updateButton=false;
              this.$scope.cancelButton=false;
              this.$scope.submitButton=false;
            }
            if(this.$scope.semesterWithdraw.status==0){
              this.$scope.editButton=true;
              this.$scope.submitButton=true;
              this.$scope.saveButton=false;
              this.$scope.textEditable=false;
              this.$scope.updateButton=false;
              this.$scope.cancelButton=false;
            }

            this.initialize();
          }).error((data) => {
      });
    }

    private edit():void{
      this.$scope.textEditable = true;
      this.$scope.editButton = false;
      this.$scope.updateButton=true;
      this.$scope.saveButton = false;
      this.$scope.cancelButton = true;
      this.$scope.submitButton = false;
    }

    private cancel():void{
      this.$scope.textEditable = false;
      this.$scope.editButton = true;
      this.$scope.updateButton = false;
      this.$scope.data.cause = this.$scope.semesterWithdraw.cause;
    }


    private getStudentsInformation():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/student/getStudentInfoById', 'application/json',
          (json:any, etag:string) => {
            this.$scope.student = json.entries;
            defer.resolve(this.$scope.student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getParameterSetting():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/parameterSetting/parameter/9/semester/'+this.$scope.student[0].semesterId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.parameterSetting = json.entries;

            defer.resolve(this.$scope.parameterSetting);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSemesterWithdrawApplications():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-academic/academic/semesterWithdraw/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterWithdrawArr = json.entries;
            defer.resolve(this.$scope.semesterWithdrawArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
    private convertToJsonForSemesterWithdrawForUpdateOrSubmit(){
      var semesterWithdraw={};
      semesterWithdraw["id"]=this.$scope.semesterWithdraw.id;
      semesterWithdraw["programId"]=this.$scope.student[0].programId;
      semesterWithdraw["semesterId"] = this.$scope.student[0].semesterId;
      semesterWithdraw["studentId"] = this.$scope.student[0].id;
      semesterWithdraw["year"] = this.$scope.student[0].year;
      semesterWithdraw["semester"] = this.$scope.student[0].academicSemester;
      if(this.$scope.data.cause.length>0){
        var cause:any ={};
        //cause = this.nl2br(this.$scope.data.cause);
        cause = this.$sce.getTrustedHtml(this.$scope.data.cause);
        var str = cause.replace("&#10;","<br>")
        semesterWithdraw["cause"] = this.$scope.data.cause;
      }
      else{
        semesterWithdraw["cause"] = this.$scope.semesterWithdraw.cause;
      }
      if(this.$scope.submitButtonClicked==true){
        semesterWithdraw["status"] = 1;
      }else{
        semesterWithdraw["status"] = 0;
      }
      semesterWithdraw["applicationDate"] = "";
      semesterWithdraw["comments"] = " ";
      return semesterWithdraw;
    }


    private convertToJsonForSemesterWithdraw(){
      var semesterWithdraw={};
      semesterWithdraw["id"]=0;
      semesterWithdraw["programId"]=this.$scope.student[0].programId;
      semesterWithdraw["semesterId"] = this.$scope.student[0].semesterId;
      semesterWithdraw["studentId"] = this.$scope.student[0].id;
      semesterWithdraw["year"] = this.$scope.student[0].year;
      semesterWithdraw["semester"] = this.$scope.student[0].academicSemester;
      var cause:any = this.$sce.trustAsHtml(this.$scope.data.cause) ;
      semesterWithdraw["cause"] = this.$scope.data.cause;

      semesterWithdraw["status"] = this.$scope.data.status;
      semesterWithdraw["applicationDate"] = "";
      semesterWithdraw["comments"] = " ";
      return semesterWithdraw;
    }

  }
  UMS.controller("SemesterWithdrawAppStd",SemesterWithdrawAppStd);
}