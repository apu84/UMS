module ums{

  interface IApplicationCCIScope extends ng.IScope
  {
    parameter:IParameterSetting;
    applicationMessage:string;
    student:Student;
    registrationResults:Array<IUGRegistrationResult>;
    responseResults:Array<IUGRegistrationResult>;
    applicationCCI:Array<AppCCI>;
    twoOccuranceCourseList:Array<IUGRegistrationResult>;
    moreThanTwoOccuranceCourseList:Array<IUGRegistrationResult>;


    appCCICarryNumber:number;
    appCCIImprovementNumber:number;
    appCCIClearanceNumber:number;
    appCCISpecialCarryNumber:number;

    resultCarryNumber:number;
    resultImprovementNumber:number;
    resultClearanceNumber:number;

    selectedCarryNumber:number;
    selectedImprovementNumber:number;
    selectedClearanceNumber:number;

    //booleans
    applicationAllowed:boolean;
    applicationCCIFound:boolean;
    submitButtonClicked:boolean;
    loadingVisibility:boolean;

    //functions
    getParameterForCCIApplication:Function;
    getParameterInfoForCCIApplication:Function;
    getStudentInfo:Function;
    initialization:Function;
    getRegistrationResults:Function;
    getRegistrationResultInfo:Function;
    getApplicationCCI:Function;
    getApplicationCCIInfo:Function;
    postInitialization:Function;
    checkMoreThanOneSelection:Function;
    makeDataEmpty:Function;
    cancel:Function;
    submit:Function;
    submitAndClose:Function;
    close:Function;
    convertToJson:Function;

  }

  interface IUGRegistrationResult{
    studentId:string;
    courseId:string;
    gradeLetter:string;
    examType:number;
    type:string;
    courseNo:string;
    courseTitle:string;
    examDate:string;
    apply:boolean;
    status:string;
    backgroundColor:string;
    color:string;
  }

  interface AppCCI{
    semesterId:number;
    studentId:string;
    courseId:number;
    applicationType:number;
    applicationDate:string;
    courseNo:string;
    courseTitle:string;
    examDate:string;
  }

  export class ApplicationCCI{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IApplicationCCIScope,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {


      $scope.submitButtonClicked=false;
      $scope.applicationAllowed=false;
      $scope.applicationCCIFound=false;
      $scope.loadingVisibility=false;
      $scope.applicationMessage="";
      $scope.registrationResults=[];
      $scope.initialization = this.initializatino.bind(this);
      $scope.getParameterInfoForCCIApplication = this.getParameterInfoForCCIApplication.bind(this);
      $scope.getStudentInfo = this.getStudentInfo.bind(this);
      $scope.getRegistrationResultInfo = this.getRegistrationResultInfo.bind(this);
      $scope.getApplicationCCI = this.getApplicationCCI.bind(this);
      $scope.getApplicationCCIInfo = this.getApplicationCCIInfo.bind(this);
      $scope.postInitialization = this.postInitialization.bind(this);
      $scope.checkMoreThanOneSelection = this.checkMoreThanOneSelection.bind(this);
      $scope.makeDataEmpty = this.makeDataEmpty.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.submit = this.submit.bind(this);
      $scope.submitAndClose=this.submitAndClose.bind(this);
      $scope.close = this.close.bind(this);
      $scope.convertToJson = this.convertToJson.bind(this);
    }

    private submitAndClose(){
      var json:any=this.convertToJson(this.$scope.registrationResults);
      this.$scope.loadingVisibility=true;
      this.$scope.responseResults=[];
      this.httpClient.post('/ums-webservice-common/academic/applicationCCI',json,'application/json')
        .success((data,status,header,config)=>{
          console.log("#####################");
          console.log(data);

          this.$scope.responseResults = data.entries;
          console.log(this.$scope.responseResults);
          this.$scope.loadingVisibility=false;

          if(this.$scope.responseResults.length>=1){
            this.$scope.submitButtonClicked=false;
            this.$window.alert("Error in saving data");
          }else{
            $.notific8("Successfully saved data");
            this.initializatino();
          }

        }).error((data)=>{

        });

    }

    private close(){
      this.$scope.submitButtonClicked=false;
    }
    private submit(){
      var totalOccurance:number=0;
      this.$scope.selectedCarryNumber=0;
      this.$scope.selectedClearanceNumber=0;
      this.$scope.selectedImprovementNumber=0;

      for(var i=0;i<this.$scope.registrationResults.length;i++){
        if(this.$scope.registrationResults[i].apply==true){
          totalOccurance+=1;
          if(this.$scope.registrationResults[i].type=="Carry")
          {
            this.$scope.selectedCarryNumber+=1;
          }
          else if(this.$scope.registrationResults[i].type=="Clearance")
          {
            this.$scope.selectedClearanceNumber+=1;
          }
          else{
            this.$scope.selectedImprovementNumber+=1;
          }
        }
      }

      if(totalOccurance>0){
        this.$scope.submitButtonClicked=true;

      }
    }

    private makeDataEmpty(resultArr:Array<IUGRegistrationResult>):Array<IUGRegistrationResult>{
      this.$scope.resultCarryNumber=0;
      this.$scope.resultClearanceNumber=0;
      this.$scope.resultImprovementNumber=0;

      for(var i=0;i<resultArr.length;i++){
        if(resultArr[i].type=="Carry"){
          this.$scope.resultCarryNumber+=1;
        }
        else if(resultArr[i].type=="Clearance")
        {
          this.$scope.resultClearanceNumber+=1;
        }
        else{
          this.$scope.resultImprovementNumber+=1;
        }
        resultArr[i].backgroundColor="white";
        resultArr[i].color="black";
        resultArr[i].status="one";
        resultArr[i].apply=false;
        //this.$scope.registrationResults.push(resultArr[i]);
      }

      return resultArr;
    }

    private cancel(){
      this.$scope.registrationResults = this.makeDataEmpty(this.$scope.registrationResults);
    }


    private checkMoreThanOneSelection(result:IUGRegistrationResult){

      console.log("in the check operation");

      var totalOccuranceNumberOfTheDate: number=1;
      var courseNoStore:Array<string>=[];
      var dateStore:Array<string>=[];


      for(var i=0;i<this.$scope.registrationResults.length;i++){
       // $("#"+this.$scope.registrationResults[i].courseNo).css("background-color","white").css("color","black");
       /* if(dateStore.length==0){
          dateStore.push(this.$scope.registrationResults[i].examDate);
        }
        else{

        }*/
        if(this.$scope.registrationResults[i].courseNo!=result.courseNo){
          if(this.$scope.registrationResults[i].examDate==result.examDate && this.$scope.registrationResults[i].apply==result.apply &&  result.apply==true){
            courseNoStore.push(this.$scope.registrationResults[i].courseNo);
          }
        }
      }
      console.log(courseNoStore);
      if(courseNoStore.length==1){


        if(result.apply){
          courseNoStore.push(result.courseNo);
        }else{
          result.backgroundColor="white";
          result.color="black";
          result.status="one";
        }

        for(var i=0;i<courseNoStore.length;i++){
          for(var j=0;j<this.$scope.registrationResults.length;j++){
            if(this.$scope.registrationResults[j].courseNo==courseNoStore[i]){
                this.$scope.registrationResults[j].status="two";
                this.$scope.registrationResults[j].backgroundColor="yellow";
                this.$scope.registrationResults[j].color="black";
              break;
            }
          }
        }

      }
      /*if(courseNoStore.length>2)*/
      else if(courseNoStore.length>=2){
        if(result.apply){
          courseNoStore.push(result.courseNo);
        }else{
          result.backgroundColor="white";
          result.color="black";
          result.status="one";
        }

        for(var i=0;i<courseNoStore.length;i++){
          for(var j=0;j<this.$scope.registrationResults.length;j++){
            if(this.$scope.registrationResults[j].courseNo==courseNoStore[i]){
              this.$scope.registrationResults[j].status="three";
              this.$scope.registrationResults[j].backgroundColor="red";
              this.$scope.registrationResults[j].color="white";
              break;
            }
          }
        }
      }else{
        if(result.apply==false){
          if(result.status=="two"){
            for(var i=0;i<this.$scope.registrationResults.length;i++){
              if(this.$scope.registrationResults[i].examDate==result.examDate && this.$scope.registrationResults[i].courseNo!=result.courseNo){
                this.$scope.registrationResults[i].backgroundColor="white";
                this.$scope.registrationResults[i].color="black";
                this.$scope.registrationResults[i].status="one";
              }
            }
          }
          if(result.status=="three"){
            var tempStoreOfResults:Array<IUGRegistrationResult>=[];
            var counter:number=0;
            for(var i=0;i<this.$scope.registrationResults.length;i++){
              if(this.$scope.registrationResults[i].examDate==result.examDate && this.$scope.registrationResults[i].courseNo!=result.courseNo){
               counter+=1;
              }
            }
            for(var j=0;j<this.$scope.registrationResults.length;j++){
              if(counter==2){
                this.$scope.registrationResults[i].backgroundColor="yellow";
                this.$scope.registrationResults[i].color="black";
                this.$scope.registrationResults[i].status="two";
              }else{
                this.$scope.registrationResults[i].backgroundColor="red";
                this.$scope.registrationResults[i].color="white";
                this.$scope.registrationResults[i].status="three";
              }
            }
          }
          result.backgroundColor="white";
          result.color="black";
          result.status="one";
        }

      }


      console.log(this.$scope.registrationResults);
    }
    private initializatino(){
      this.getParameterInfoForCCIApplication();
      this.getStudentInfo();
    }


    private postInitialization(){
      if(this.$scope.applicationCCI.length==0){
        this.getRegistrationResult();
      }else{
        this.$scope.applicationCCIFound=true;
      }
    }
    private getRegistrationResult(){
      this.getRegistrationResultInfo().then((resultArr:Array<IUGRegistrationResult>)=>{
        this.$scope.registrationResults=this.makeDataEmpty(resultArr);

       /* for(var i=0;i<resultArr.length;i++){
          resultArr[i].backgroundColor="white";
          resultArr[i].color="black";
          resultArr[i].status="one";
          resultArr[i].apply=false;
          this.$scope.registrationResults.push(resultArr[i]);
        }*/

      });
    }

    private getApplicationCCI(){
      this.$scope.applicationCCI=[];
      this.$scope.appCCICarryNumber=0;
      this.$scope.appCCIClearanceNumber=0;
      this.$scope.appCCIImprovementNumber=0;

      this.getApplicationCCIInfo().then((appArr:Array<AppCCI>)=>{

        for(var i=0;i<appArr.length;i++){
          if(appArr[i].applicationType == 2){
            this.$scope.appCCIClearanceNumber+=1;
          }
          else if(appArr[i].applicationType == 5){
            this.$scope.appCCIImprovementNumber+=1;
          }
          else if(appArr[i].applicationType == 4){
            this.$scope.appCCISpecialCarryNumber+=1;
          }
          else{
            this.$scope.appCCICarryNumber+=1;
          }
          this.$scope.applicationCCI.push(appArr[i]);
        }
        console.log(this.$scope.applicationCCI);
        this.postInitialization();
      });

      console.log(this.$scope.appCCICarryNumber);
    }


    private getParameterInfoForCCIApplication(){

      var defer = this.$q.defer();
      var parameter:IParameterSetting;

      this.httpClient.get('/ums-webservice-common/academic/parameterSetting/parameter/'+'application_cci','application/json',
          (json:any,etag:string)=>{
            parameter = json.entries;


            var date = new Date();


            if(date>= new Date(parameter[0].startDate) && date <= new Date(parameter[0].endDate)){
              this.$scope.applicationAllowed = true;
              this.$scope.applicationMessage="";
              this.getApplicationCCI();
            }else{
              this.$scope.applicationMessage="Application Now Allowed";
            }
            defer.resolve(parameter);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

    private getStudentInfo(){
      var defer = this.$q.defer();
      var student:Student;

      this.httpClient.get('/ums-webservice-common/academic/student/getStudentInfoById','application/json',
          (json:any,etag:string)=>{
            student = json.entries;
            this.$scope.student = student;
            console.log(this.$scope.student);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

    private getRegistrationResultInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var registrationResult:Array<IUGRegistrationResult>=[];
      this.httpClient.get('/ums-webservice-common/academic/UGRegistrationResultResource/CarryClearanceImprovement','application/json',
          (json:any,etag:string)=>{
            registrationResult = json.entries;

            defer.resolve(registrationResult);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }

    private getApplicationCCIInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var appCCIArr : Array<AppCCI>=[];
      this.httpClient.get('/ums-webservice-common/academic/applicationCCI/student','application/json',
          (json:any,etag:string)=>{
            appCCIArr = json.entries;

            defer.resolve(appCCIArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
     }

    private convertToJson(result:Array<IUGRegistrationResult>):any{
      var completeJson={};
      console.log("result");
      console.log(result);
      var jsonObj=[];
      for(var i=0;i<this.$scope.registrationResults.length;i++){
        var item={};
        if(this.$scope.registrationResults[i].apply==true){
          var a:IUGRegistrationResult=this.$scope.registrationResults[i];
          item["semesterId"] = this.$scope.student[0].semesterId;
          item["studentId"]=this.$scope.student[0].id;
          item["courseId"] = this.$scope.registrationResults[i].courseId;
          var applicationTpe:any;
          if(a.type=='Carry'){
            applicationTpe=3;
          }else if(a.type=='Clearance'){
            applicationTpe=2;
          }else{
            applicationTpe=5;
          }
          item["applicationType"]=applicationTpe;
          jsonObj.push(item);
        }
      }
      completeJson["entries"]=jsonObj;
      console.log(completeJson);
      return completeJson;
    }

  }

  UMS.controller("ApplicationCCI",ApplicationCCI);
}