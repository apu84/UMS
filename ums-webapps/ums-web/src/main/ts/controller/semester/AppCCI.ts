module ums {
  interface IApplicationCCIScope extends ng.IScope {
    parameter: IParameterSetting;
    applicationMessage: string;
    student: Student;
    registrationResults: Array<IUGRegistrationResult>;
    responseResults: Array<IUGRegistrationResult>;
    applicationCCI: Array<AppCCI>;
    carryLastdateIn:Array<ICarryLastdate>;
    twoOccuranceCourseList: Array<IUGRegistrationResult>;
    ugResultsForSave:Array<IUGRegistrationResult>;
    appImpApprove:Array<IAppImprove>;
    moreThanTwoOccuranceCourseList: Array<IUGRegistrationResult>;
    //New property added
    checkBoxCounter: number;


    appCCICarryNumber: number;
    appCCIImprovementNumber: number;
    appCCIClearanceNumber: number;
    appCCISpecialCarryNumber: number;
    CARRY: number;
    CLEARANCE: number;
    IMPROVEMENT: number;
    SPECIAL_CARRY: number;
      dateCheckStatus:string;
      statusApproved:number;
      statusWaitingForPayment:number;
      statusWaitingforHeadsApproval:number;
      statusRejected:number;


    resultCarryNumber: number;
    resultImprovementNumber: number;
    resultClearanceNumber: number;

    selectedCarryNumber: number;
    selectedImprovementNumber: number;
    selectedClearanceNumber: number;
    //Rumi
      carry_status_initial:number;
      clerance_status_initial:number;
      improvement_status_initial:number;
      submitButtonParameter:string;
      pendingApprovedStatus:number;
      improvementLimit:number;
      improvementLimitstatic:number;
      improvemetLimitCrossed:boolean;
      totalSemesterStatic:string;
      carrylastDate:string;
      carryStartDate:string;
      carrylastDateDeadline:boolean;
      carryStatusShow:string;
      historyButtonStatus:boolean;


    //booleans
    applicationAllowed: boolean;
    applicationCCIFound: boolean;
    submitButtonClicked: boolean;
    loadingVisibility: boolean;
    submitAndCloseEscape:boolean;
    //Rumi
      appcci_load_status:boolean;
      submit_Button_Disable:boolean;

    //functions
    getParameterForCCIApplication: Function;
    getParameterInfoForCCIApplication: Function;
    getStudentInfo: Function;
    initialization: Function;
    getRegistrationResults: Function;
    getRegistrationResultInfo: Function;
    getApplicationCCI: Function;
    getApplicationCCIInfo: Function;
    postInitialization: Function;
      checkMoreThanOneSelectionSubmit: Function;
    makeDataEmpty: Function;
    cancel: Function;
    submit: Function;
    submitAndClose: Function;
    close: Function;
    convertToJson: Function;
    finalSave:Function;
      submitButtonEnable:Function;
      receipt:Function;
      improvementLimitCalculation:Function;
      carryLastDateFinder:Function;
      insertIntoStudentPayment:Function;
      getApplicationCCIImprovemnetApproved:Function;
   // alert:Function;

  }
  //New interface
  interface IAppImprove{
      studentId: string;
      semesterId:number;
      courseId: number;
      courseNo: string;
      courseTitle:string;
      courseYear:number;
      courseSemester:number;
      semesterName:string;
  }

  interface IUGRegistrationResult{
    studentId: string;
    courseId: string;
    gradeLetter: string;
    examType: number;
    type: number;
    courseNo: string;
    courseTitle: string;
    examDate: string;
    lastApplyDate:string;
    apply: boolean;
    status: string;
    backgroundColor: string;
    color: string;
    courseYear:number;
    courseSemester:number;
    deadline:string;
    deadLineBol:boolean;
    //status and pending status
  }

  interface AppCCI {
    semesterId: number;
    studentId: string;
    courseId: number;
    applicationType: number;
    applicationDate: string;
    courseNo: string;
    courseTitle: string;
    examDate: string;
    cciStatus:number;
    grade:string;
    statusName: string;
    carryYear:number;
    carrySemester:number;
    transactionId:string;

}

interface ICarryLastdate{
    carryLastdate:string;
    deadLine:boolean;
}

  export class ApplicationCCI {

    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify','FeeReportService', '$sce', '$window'];

    private static REGULAR = 1;
    private static CLEARANCE = 2;
    private static CARRY = 3;
    private static SPECIAL_CARRY = 4;
    private static IMPROVEMENT = 5;
    private static LEAVE = 6;
    private static statusApproved=8;
    private static dateCheckStatus="Date Over";
      private static  statusWaitingForPayment=7;
      private static statusWaitingforHeadsApproval=2;
      private static statusRejected=9;


    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IApplicationCCIScope,
                private $q: ng.IQService, private notify: Notify,
                private feeReportService: FeeReportService,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {


        $scope.checkBoxCounter = 0;


      $scope.CARRY = 3;
      $scope.IMPROVEMENT = 5;
      $scope.SPECIAL_CARRY = 4;
      $scope.CLEARANCE = 2;
      $scope.statusApproved=8;
      $scope.statusWaitingForPayment=7;
      $scope.statusWaitingforHeadsApproval=2;
      $scope.statusRejected=9;
      $scope.dateCheckStatus="Date Over";
      $scope.totalSemesterStatic="From 1.1 to 4.2"
      //rumi
        $scope.carry_status_initial=14;
       // $scope.clerance_status_initial=15;
        $scope.improvement_status_initial=16;
        $scope.pendingApprovedStatus=0;
        $scope.ugResultsForSave=[];
        $scope.improvementLimitstatic=4;
        $scope.improvemetLimitCrossed=false;
        $scope.improvementLimit=0;
        $scope.carrylastDate="";
        $scope.submit_Button_Disable=true;
        //-----
      $scope.submitButtonClicked = false;
      $scope.applicationAllowed = false;
      $scope.applicationCCIFound = false;
      $scope.loadingVisibility = false;
      $scope.submitAndCloseEscape=false;
      $scope.appcci_load_status=true;
      $scope.applicationMessage = "";
      $scope.submitButtonParameter="";
      $scope.registrationResults = [];
      $scope.initialization = this.initializatino.bind(this);
      $scope.getParameterInfoForCCIApplication = this.getParameterInfoForCCIApplication.bind(this);
      $scope.getStudentInfo = this.getStudentInfo.bind(this);
      $scope.getRegistrationResultInfo = this.getRegistrationResultInfo.bind(this);
      $scope.getApplicationCCI = this.getApplicationCCI.bind(this);
      $scope.getApplicationCCIInfo = this.getApplicationCCIInfo.bind(this);
      $scope.postInitialization = this.postInitialization.bind(this);
      $scope.checkMoreThanOneSelectionSubmit = this.checkMoreThanOneSelectionSubmit.bind(this);
      $scope.makeDataEmpty = this.makeDataEmpty.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.submit = this.submit.bind(this);
      $scope.receipt=this.receipt.bind(this);
      $scope.improvementLimitCalculation=this.improvementLimitCalculation.bind(this);
      $scope.carrylastDate=this.carryLastDateFinder.bind(this);
      $scope.submitAndClose = this.submitAndClose.bind(this);
      $scope.getApplicationCCIImprovemnetApproved=this.getApplicationCCIImprovemnetApproved.bind(this);
      $scope.close = this.close.bind(this);
      $scope.insertIntoStudentPayment=this.insertIntoStudentPayment.bind(this);
     // $scope.submitButtonEnable=this.submitButtonEnable.bind(this);
     // $scope.alert=this.alert.bind(this);
        $scope.finalSave=this.finalSave.bind(this);
      $scope.convertToJson = this.convertToJson.bind(this);
      this.improvementLimitCalculation();
      this.carryLastDateFinder();

    }

      private  finalSave(){
          this.submitAndClose();
      }

      private carryLastDateFinder():ng.IPromise<any>{
          this.$scope.carrylastDate="";
          var defer = this.$q.defer();
          var carryLastDate_check:Array<ICarryLastdate>=[];
          this.httpClient.get('academic/applicationCCI/carryLastDate', HttpClient.MIME_TYPE_JSON,
              (json: any, etag: string) => {
              console.log("Jacksonlll");
              console.log(json);
                this.$scope.carrylastDate=json.date;
                this.$scope.carryStartDate=json.StartDate;
                  this.$scope.carrylastDateDeadline=json.deadline;
                  console.log("-----"+this.$scope.carrylastDateDeadline);
                  this.$scope.carryStatusShow=this.$scope.carrylastDateDeadline==true? "Not Allowed":"Available";
                  defer.resolve(json.date);
              },
              (response: ng.IHttpPromiseCallbackArg<any>) => {
                  console.error(response);
              });
          return defer.promise;
      }
      private improvementLimitCalculation(): ng.IPromise<any>{
        this.$scope.improvementLimit=0;
          var defer = this.$q.defer();
          var improvement_limit_check: number=0;
          this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/getImprovementLimit', 'application/json',
              (json: any, etag: string) => {
                  improvement_limit_check = json;
                  console.log("---------------------Improvement Limit ---------------------");
                  this.$scope.improvementLimit=improvement_limit_check;
                  this.$scope.historyButtonStatus=this.$scope.improvementLimit>0 ? false:true;
                  this.$scope.improvemetLimitCrossed=this.$scope.improvementLimit<4 ? false:true;
                  console.log(this.$scope.improvementLimit);
                  defer.resolve(this.$scope.improvementLimit);
              },
              (response: ng.IHttpPromiseCallbackArg<any>) => {
                  console.error(response);
              });
          return defer.promise;

      }

      public receipt(transactionId: string): void {
        console.log("report:"+transactionId);
        this.feeReportService.receipt(transactionId);
      }

      private insertIntoStudentPayment(){
         /* this.convertToJson( )).
          then((json:any)=>{
              console.log("Stydeht payment Accept Clearance Exam");
              console.log(json);
              this.$scope.loadingVisibility = true;
              this.$scope.responseResults = [];

          })*/

      }

    private submitAndClose() {
      this.convertToJson(this.$scope.registrationResults).then((json:any)=>{
          console.log("Checking");
          console.log(json);

          this.$scope.loadingVisibility = true;
          this.$scope.responseResults = [];
          this.httpClient.post('academic/applicationCCI', json, 'application/json')
              .success((data, status, header, config) => {

                  this.$scope.responseResults = data.entries;
                  if (this.$scope.responseResults.length >= 1) {
                     // alert('Error->value:'+this.$scope.responseResults.length);
                      this.$scope.submitButtonClicked = false;
                      //this.$window.alert("Error in saving data");
                  }
                  console.log("Rumi");

                  console.log(this.$scope.responseResults.length);
                  this.$scope.checkBoxCounter=0;
                  this.$scope.submit_Button_Disable=true;
               this.initializatino();

                  this.$scope.loadingVisibility = false;

              }).error((data) => {

          });
      })
        this.insertIntoStudentPayment();


    }

    private close() {
      this.$scope.submitButtonClicked = false;
    }

    private submit() {
            this.submitAndClose();
    }


    private makeDataEmpty(resultArr: Array<IUGRegistrationResult>): Array<IUGRegistrationResult> {
      this.$scope.resultCarryNumber = 0;
      this.$scope.resultClearanceNumber = 0;
      this.$scope.resultImprovementNumber = 0;

      for (var i = 0; i < resultArr.length; i++) {
        if (resultArr[i].type == ApplicationCCI.CARRY) {
          this.$scope.resultCarryNumber += 1;
        }
        else if (resultArr[i].type == ApplicationCCI.CLEARANCE) {
          this.$scope.resultClearanceNumber += 1;
        }
        else {
          this.$scope.resultImprovementNumber += 1;
        }
        resultArr[i].backgroundColor = "white";
        resultArr[i].color = "black";
        resultArr[i].status = "one";
        resultArr[i].apply = false;
        //this.$scope.registrationResults.push(resultArr[i]);
      }

      return resultArr;
    }

    private cancel() {
        this.$scope.checkBoxCounter=0;
        this.$scope.submit_Button_Disable=true;
      this.$scope.registrationResults = this.makeDataEmpty(this.$scope.registrationResults);
      //this.$scope.submit_Button_Disable=true;
    }


    private checkMoreThanOneSelectionSubmit(result: IUGRegistrationResult) {

        if(result.apply){
            this.$scope.checkBoxCounter++;
            this.enableOrDisableSubmitButton();
        }
        else{
            this.$scope.checkBoxCounter--;
            this.enableOrDisableSubmitButton();
        }

        console.log("value:"+this.$scope.submit_Button_Disable);

    }

    private enableOrDisableSubmitButton(): void{
        if( this.$scope.checkBoxCounter > 0){
            this.$scope.submit_Button_Disable=false;
        }else{
            this.$scope.submit_Button_Disable=true;
        }
    }

    private initializatino() {
      this.getParameterInfoForCCIApplication();
      this.getStudentInfo();
        this.improvementLimitCalculation();
        this.carryLastDateFinder();
    }


    private postInitialization() {

        this.getRegistrationResult();
        this.$scope.applicationCCIFound = false;
    }

    private getRegistrationResult() {
      this.getRegistrationResultInfo().then((resultArr: Array<IUGRegistrationResult>) => {
        console.log("result arr");
        console.log(resultArr);
        this.$scope.registrationResults = this.makeDataEmpty(resultArr);

      });
    }

    private getApplicationCCI() {
      this.$scope.applicationCCI = [];
      this.$scope.appCCICarryNumber = 0;
      this.$scope.appCCIClearanceNumber = 0;
      this.$scope.appCCIImprovementNumber = 0;
      this.$scope.pendingApprovedStatus=0;

        this.getApplicationCCIInfo().then((appArr: Array<AppCCI>) => {

            for (var i = 0; i < appArr.length; i++) {
                if (appArr[i].applicationType == ApplicationCCI.CLEARANCE) {
                    this.$scope.appCCIClearanceNumber += 1;
                }
                else if (appArr[i].applicationType == ApplicationCCI.IMPROVEMENT) {
                    this.$scope.appCCIImprovementNumber += 1;
                }
                else if (appArr[i].applicationType == ApplicationCCI.SPECIAL_CARRY) {
                    this.$scope.appCCISpecialCarryNumber += 1;
                }
                else {
                    this.$scope.appCCICarryNumber += 1;
                }

            }

            //console.log(this.$scope.applicationCCI);
            this.postInitialization();
        });

       // console.log(this.$scope.appCCICarryNumber);

      //console.log(this.$scope.appCCICarryNumber);
    }


    private getParameterInfoForCCIApplication() {

      var defer = this.$q.defer();
      var parameter: IParameterSetting;

      this.httpClient.get('/ums-webservice-academic/academic/parameterSetting/parameter/' + 'application_cci', 'application/json',
          (json: any, etag: string) => {
            parameter = json.entries;

            var date = new Date();

            console.log("*****8");
            console.log("Parameter");
            console.log(parameter);
            if (date >= new Date(parameter[0].startDateJs) && date <= new Date(parameter[0].endDateJs)) {
              this.$scope.applicationAllowed = true;
              this.$scope.applicationMessage = "";
              this.getApplicationCCI();
            } else {
              this.$scope.applicationMessage = "Application Not Allowed";
            }
            defer.resolve(parameter);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private getStudentInfo() {
      var defer = this.$q.defer();
      var student: Student;

      this.httpClient.get('/ums-webservice-academic/academic/student/getStudentInfoById', 'application/json',
          (json: any, etag: string) => {
            student = json.entries;
            this.$scope.student = student;
            console.log(this.$scope.student);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private getRegistrationResultInfo(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var registrationResult: Array<IUGRegistrationResult> = [];
      this.httpClient.get('/ums-webservice-academic/academic/UGRegistrationResultResource/CarryClearanceImprovement', 'application/json',
          (json: any, etag: string) => {
            registrationResult = json.entries;
              console.log("---------------------Registration result---------------------");
              console.log(registrationResult);
            defer.resolve(registrationResult);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getApplicationCCIInfo(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var appCCIArr: Array<AppCCI> = [];
      this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/student', 'application/json',
          (json: any, etag: string) => {
            appCCIArr = json.entries;
            console.log("****RRRRRRRRRRRRRRRRR******");
            console.log("Applicatino cci Updated!!");
            console.log(json);
            this.$scope.applicationCCI=appCCIArr;
            defer.resolve(appCCIArr);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
      private getApplicationCCIImprovemnetApproved(): ng.IPromise<any> {
          var defer = this.$q.defer();
          var appCCIArr: Array<IAppImprove> = [];
          this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/ApprovedImprovementInfo', 'application/json',
              (json: any, etag: string) => {
                  appCCIArr = json.entries;
                  console.log("****zzzzzzzz******");
                  console.log(json.semesterName);
                  console.log("Applicatino cci Updated!!");
                  console.log(json);
                  this.$scope.appImpApprove=appCCIArr;
                  defer.resolve(appCCIArr);
              },
              (response: ng.IHttpPromiseCallbackArg<any>) => {
                  console.error(response);
              });
          return defer.promise;
      }

    private convertToJson(result: Array<IUGRegistrationResult>): ng.IPromise<any> {
        let defer:ng.IDeferred<any> = this.$q.defer();
      var completeJson = {};
      console.log("result");
      console.log(result);
      var jsonObj = [];
      for (var i = 0; i <this.$scope.registrationResults.length; i++) {
        var item = {};
        if (this.$scope.registrationResults[i].apply == true) {
          var a: IUGRegistrationResult = this.$scope.registrationResults[i];
          item["semesterId"] = this.$scope.student[0].currentEnrolledSemesterId;
          item["studentId"] = this.$scope.student[0].id;
          item["courseId"] = this.$scope.registrationResults[i].courseId;

          item["applicationType"] = a.type;

           if(this.$scope.registrationResults[i].type==ApplicationCCI.CLEARANCE){

               this.$scope.clerance_status_initial=8;

               item["cciStatus"] = this.$scope.clerance_status_initial;
           }else if(this.$scope.registrationResults[i].type==ApplicationCCI.IMPROVEMENT){

               this.$scope.improvement_status_initial=7;
               item["cciStatus"] = this.$scope.improvement_status_initial;
           }else{
               this.$scope.carry_status_initial=2;
               item["cciStatus"] = this.$scope.carry_status_initial;
           }
           console.log("Items");
          console.log(item);
          //this.notify.success("sending./.....");
          jsonObj.push(item);
        }

      }
      completeJson["entries"] = jsonObj;
      console.log(completeJson);
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("ApplicationCCI", ApplicationCCI);
}