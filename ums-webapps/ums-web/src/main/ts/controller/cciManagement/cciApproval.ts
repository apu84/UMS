/*
* Rumi-21-1-1028
* carry Approval Controller
* */
module ums{
    //interface for  Approval Status Constant Fetch

    interface IConstants {
        id: any;
        name: string;
    }

    interface Isemester{
        id:number;
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
        fullName: string;
        rowNum:number;

    }
    interface ImodalTableCarryInfo{
        courseNo:string;
        courseTitle:string;
        carryYear:number;
        carrySemester:number;
        appliedStatus:string;
        currentStatus:string;

    }

    interface  ImodalApliedInfo{
        courseId:string;
        courseTitle:string;
        statusName:string;
        apply:boolean;
    }
    class CarryApplicationApproval{
        public carryApprovalStatusList: Array<IConstants>;
        public carryApprovalStatus: IConstants;
        public allSemesters:Array<Isemester>;
        public semesterApprovalStatus:number;
        public itemsPerPage: number;
        public resultsPerPage: string;
        public totalItems: number;
        public pageNumber: number;
        public pagination: any = {};
        applicationCCI: Array<AppCCI>;
        applicationCCIGetAll: Array<AppCCI>;
        applicationModalTableinfo:Array<ImodalTableCarryInfo>;
        applicationModalAppliedInfo:Array<ImodalApliedInfo>;
        applicationModalAppliedInfoUpdated:Array<ImodalApliedInfo>;
        responseResult:Array<ImodalApliedInfo>;
        approvalStatus:String;
        modalAccept:string;
        modalReject:string;
        statusDefaultWaitingForpayment:string;
        statusDefaultApproved:string;
        counter:number;
        itemPerpageChecker:boolean;
        appliedStatus:number;
        approvedStatus:number;
        modalStatus:string;
        studentID:string;
        semesterId:number;
        studentIdTA:string;
        semesterIdTA:number;
        fullNameTA:string;
        courseTitleTA:string;
        courseNoTA:string;
        totalcarry:number;
        totalApplied:number;
        submitResult:number;
        approvalStatusFromHead:number;
        seachByStudentId:string;
        submit_Button_Disable:boolean;
        checkBoxCounter:number;
        pgDisable:boolean;
        carrylastDate:string;
        carrylastDateDeadline:boolean;
        carryStatusShow:string;
        carryStartDate:any;



        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'employeeService', 'additionalRolePermissionsService', 'userService', 'commonService', 'attachmentService'];
        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private $timeout: ng.ITimeoutService,
                    private leaveTypeService: LeaveTypeService,
                    private leaveApplicationService: LeaveApplicationService,
                    private leaveApplicationStatusService: LeaveApplicationStatusService,
                    private employeeService: EmployeeService,
                    private additionalRolePermissionsService: AdditionalRolePermissionsService,
                    private userService: UserService,
                    private commonservice: CommonService,
                    private attachmentService: AttachmentService) {
            this.resultsPerPage = "3";
            this.itemsPerPage = +this.resultsPerPage;
            this.pagination.currentPage = 1;
            this.pageNumber = 1;
            this.carryApprovalStatusList = [];
            this.carryApprovalStatusList = this.appConstants.carryApprovalStatus;
            this.carryApprovalStatus = this.carryApprovalStatusList[0];
            this.allSemesters=[];
           // this.semesterApprovalStatus=this.allSemesters[0].id;
            this.applicationCCIGetAll=[];
            this.applicationModalTableinfo=[];
            this.applicationModalAppliedInfo=[];
            this.applicationModalAppliedInfoUpdated=[];
            this.responseResult=[];
            this.approvalStatus="";
            this.itemPerpageChecker=false;
            this.totalItems=0;
            this.modalAccept="accept";
            this.modalReject="reject";
            this.statusDefaultWaitingForpayment="Waiting for payment";
            this.statusDefaultApproved="Approved";
            this.counter=0;
            this.appliedStatus=0;
            this.approvedStatus=0;
            this.modalStatus="";
            this.studentID="";
            this.semesterId=0;
            this.totalcarry=0;
            this.totalApplied=0;
            this.submitResult=0;
            this.approvalStatusFromHead=0;
            this.seachByStudentId="";
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            this.pgDisable=false;

            //Functions
            this.statusChanged(this.carryApprovalStatus);
            this.carryLastDateFinder();
        }

        private carryLastDateFinder():ng.IPromise<any>{
            this.carrylastDate="";
            var defer = this.$q.defer();
            this.httpClient.get('academic/applicationCCI/carryLastDate', HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    console.log("Jacksonlll-head-Show");
                    console.log(json);
                    this.carrylastDate=json.date;
                    this.carryStartDate=json.StartDate;
                    this.carrylastDateDeadline=json.deadline;
                    console.log("-----"+this.carrylastDateDeadline);
                    this.carryStatusShow=this.carrylastDateDeadline==true? "Not Allowed":"Available";
                    defer.resolve(json.date);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }





        private getSemester(studentidTa:string,semesteridTa:number):ng.IPromise<any>{
            this.studentID=studentidTa;
            this.semesterId=semesteridTa;
            this.totalcarry=0;
            var url = '/ums-webservice-academic/academic/applicationCCI/studentId/'+this.studentID+'/semesterId/'+this.semesterId;
            var defer = this.$q.defer();

           var modalcarryoinfo:Array<ImodalTableCarryInfo>=[];
            this.httpClient.get(url, 'application/json',
                (json: any, etag: string) => {
                    console.log("------------Rumi---------------------");
                      var modalcarryoinfo=json.entries;
                      this.applicationModalTableinfo=modalcarryoinfo;
                    console.log("applicationModalTableinfo");
                    console.log(this.applicationModalTableinfo);
                    this.totalcarry=this.applicationModalTableinfo.length;
                    defer.resolve(this.applicationModalTableinfo);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                    this.notify.error("Error in getting carry data applications");
                });

            return defer.promise;
        }
        private setResultsPerPage(resultsPerPage: number) {
                   if(resultsPerPage>0){
                       this.itemPerpageChecker=false;
                       this.itemsPerPage = resultsPerPage;
                       this.statusChanged(this.carryApprovalStatus);
                       console.log("status canhed from item per page changed");
                   }else{
                       this.itemPerpageChecker=true;
                   }

           // alert(this.itemsPerPage);


        }
        private cancel(){

        }



        private statusChanged(carryApplicationStatus: IConstants) :ng.IPromise<any> {
            this.carryApprovalStatus= carryApplicationStatus;
            this.pgDisable=false;
            console.log("PGdiasbe Result:"+this.pgDisable);

            if(this.carryApprovalStatus.name.match("Waiting for head's approval")){
             this.approvalStatus="Waiting for head's approval";
            }else if(this.carryApprovalStatus.name.match("Approved By Head")){
                this.approvalStatus="Approved By Head";
            }else if(this.carryApprovalStatus.name.match("Rejected By Head")){
                this.approvalStatus="Rejected By Head";
            }else{
                this.approvalStatus="All";
            }
            console.log(this.carryApprovalStatus);
            var defer = this.$q.defer();
            this.applicationCCI=[];
            var appCCIArr: Array<AppCCI> = [];
            this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/approvalStatus/'+this.approvalStatus+'/currentPage/'+this.pagination.currentPage+'/itemPerPage/'+this.itemsPerPage, 'application/json',
                (json: any, etag: string) => {
                //console.log("Json");
                //console.log(json);
                    appCCIArr = json.entries;
                    //console.log("*****pppppppp******");
                    //console.log("Applicatino cci Updated!!");

                    this.applicationCCI=appCCIArr;
                    //console.log("Application Size");
                    //console.log(json.appSize);

                    this.totalItems=Number(json.appSize);
                    //console.log("Total items");
                    //console.log(this.totalItems)
                    defer.resolve(appCCIArr);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            return defer.promise;
        }

         private getAppliedAndApprovedInfo(studentidTa:string,semesteridTa:number) :ng.IPromise<any>{
             this.studentID=studentidTa;
             this.semesterId=semesteridTa;
             this.totalApplied=0;
             var defer = this.$q.defer();
             var app: Array<ImodalApliedInfo> = [];
             this.applicationModalAppliedInfo=[];
             this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/getAllcarryInfo/studentId/'+this.studentID+'/semesterId/'+this.semesterId, 'application/json',
                 (json: any, etag: string) => {
                     app = json.entries;
                     console.log("**********");
                     console.log("Applicatino CCI Appoved Aplied!!");

                     this.applicationModalAppliedInfo=app;
                     this.totalApplied=this.applicationModalAppliedInfo.length;
                     console.log(app);
                     defer.resolve(app);

                 },
                 (response: ng.IHttpPromiseCallbackArg<any>) => {
                     console.error(response);
                 });
             return defer.promise;//getAllcarryInfo

         }

        private pageChanged(pageNumber: number) {
            this.setCurrent(pageNumber);
        }

        //-----------------
        private setCurrent(currentPage: number) {
            if(this.itemsPerPage>0) {
                this.pagination.currentPage = currentPage;
               this.statusChanged(this.carryApprovalStatus);
            }else{
                console.log("Nulll Values !!!!")
            }

        }

        //---------------
        private click(studentId:string,semesterId:number,fullName:string,courseTitle:string,courseNo:string){
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            this.getAppliedAndApprovedInfo(studentId,semesterId).then((value:any)=>{
                this.getSemester(studentId,semesterId);
            })

            this.studentIdTA=studentId;
            this.semesterIdTA=semesterId;
            this.fullNameTA=fullName;
            this.courseTitleTA=courseTitle;
            this.courseNoTA=courseNo;
        }
        private close(){
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            console.log("Rumi");

        }

        private submitModal(submitStatus:string){
            var appliedvalue:Array<ImodalApliedInfo>=[];
            this.applicationModalAppliedInfoUpdated=[];


             if(submitStatus=="accept"){
                 this.approvalStatusFromHead=7;
                 console.log("Accepted By head Sir");

                 this.applicationModalAppliedInfoUpdated=this.applicationModalAppliedInfo.filter((f)=>f.apply===true);
                 appliedvalue=this.applicationModalAppliedInfoUpdated;
                 console.log("----");
                 console.log(appliedvalue);
                 this.convertToJson(this.applicationModalAppliedInfo.filter((f)=>f.apply===true)).then((app:any)=>{
                     console.log("Hello From Another Side!!!!!");
                     console.log(app);
                     this.responseResult=[];
                     this.httpClient.post('academic/applicationCCI/appliedAndApproved/studentId/'+this.studentIdTA+'/semesterId/'+this.semesterIdTA, app, 'application/json')
                         .success((data, status, header, config) => {
                             /*this.responseResult=data.entries;
                             if(this.responseResult.length>=1){
                                 console.log("Error in savinf Data");
                             }*/
                             this.notify.success("Data saved successfully");
                             this.statusChanged(this.carryApprovalStatus);
                         }).error((data) => {

                     });
                 })
             }else{
                 console.log("Rejected By head Sir");
                 this.approvalStatusFromHead=9;
                 this.applicationModalAppliedInfoUpdated=this.applicationModalAppliedInfo.filter((f)=>f.apply===true);
                 appliedvalue=this.applicationModalAppliedInfoUpdated;
                 console.log("----");
                 console.log(appliedvalue);
                 this.convertToJson(this.applicationModalAppliedInfo.filter((f)=>f.apply===true)).then((app:any)=>{
                     console.log("Hello From Another Side!!!!!");
                     console.log(app);
                     this.responseResult=[];
                     this.httpClient.post('academic/applicationCCI/appliedAndApproved/studentId/'+this.studentIdTA+'/semesterId/'+this.semesterIdTA, app, 'application/json')
                         .success((data, status, header, config) => {
                             /*this.responseResult=data.entries;
                             if(this.responseResult.length>=1){
                                 console.log("Error in savinf Data");
                             }*/
                             this.notify.success("Data saved successfully");
                             this.statusChanged(this.carryApprovalStatus);
                         }).error((data) => {

                     });
                 })
             }
        }
        private convertToJson(result: Array<ImodalApliedInfo>): ng.IPromise<any> {
            let defer:ng.IDeferred<any> = this.$q.defer();
            var completeJson = {};
            console.log("result in converto Json");
            console.log(result);
            console.log(result.length);
            var jsonObj = [];
            for(var i=0;i<result.length;i++){
                var item = {};
                if(result[i].apply==true) {
                    item["courseId"] = result[i].courseId;
                    item["cciStatus"] = this.approvalStatusFromHead;
                    console.log("Items");
                    console.log(item);
                    //this.notify.success("sending./.....");
                    jsonObj.push(item);
                }
            }
            completeJson["entries"] = jsonObj;
            console.log("Complete json!!!!!!!!!!!!!!!")
            console.log(completeJson);
            defer.resolve(completeJson);
            return defer.promise;

        }

        private searchByStudentId(studentId:string) {
            this.seachByStudentId=studentId;

            if(this.seachByStudentId.length>=9){
                this.pgDisable=true;
                console.log("PGdiasbe Result:"+this.pgDisable);
                console.log(this.seachByStudentId+""+this.approvalStatus);
                var defer = this.$q.defer();
                this.applicationCCI=[];
                var appCCIArr: Array<AppCCI> = [];
                this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/searchByStudentId/approvalStatus/'+this.approvalStatus+'/studentId/'+this.seachByStudentId, 'application/json',
                    (json: any, etag: string) => {
                        appCCIArr = json.entries;
                        console.log("*****RRRRRR******");
                        console.log("SearchByStudentId!!");

                        this.applicationCCI=appCCIArr;
                        console.log(this.applicationCCI);
                        if(this.applicationCCI.length<1){
                            this.notify.error("No Students Records Found");
                        }
                        defer.resolve(appCCIArr);
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                      //  console.error(response);
                        alert("No Records Found For this Student Id");
                    });
                this.seachByStudentId="";
                return defer.promise;
            }else{
                alert("Invalid Student Id");
            }
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;

          this.seachByStudentId="";

        }

        private refresh(){
            console.log("refresh");
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            this.statusChanged(this.carryApprovalStatus);
            this.seachByStudentId="";

        }
        private checkMoreThanOneSelectionSubmit(result:ImodalApliedInfo) {

            if(result.apply){
                this.checkBoxCounter++;
                this.enableOrDisableSubmitButton();
            }
            else{
                this.checkBoxCounter--;
                this.enableOrDisableSubmitButton();
            }

            console.log("value:"+this.submit_Button_Disable);

        }

        private enableOrDisableSubmitButton(): void{
            if( this.checkBoxCounter > 0){
                this.submit_Button_Disable=false;
            }else{
                this.submit_Button_Disable=true;
            }
        }

//ng-disabled="a.statusName=='Waiting for payment' || a.statusName=='Approved' ?  true : false"

    }
UMS.controller("CarryApplicationApproval",CarryApplicationApproval);

}