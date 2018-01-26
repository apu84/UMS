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

    }
    class CarryApplicationApproval{
        public carryApprovalStatusList: Array<IConstants>;
        public carryApprovalStatus: IConstants;
        public allSemesters:Array<Isemester>;
        public semesterApprovalStatus:number;
        public itemsPerPage: number;
        public resultsPerPage: string;
        applicationCCI: Array<AppCCI>;
        applicationCCIGetAll: Array<AppCCI>;
        approvalStatus:String;
        counter:number;
        appliedStatus:number;
        approvedStatus:number;
        modalStatus:string;
        public pagination: any = {};
        studentID:string;
        semesterId:number;
        studentIdTA:string;
        semesterIdTA:number;
        fullNameTA:string;
        courseTitleTA:string;
        courseNoTA:string;
        totalcarry:number;
        submitResult:number;



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
            this.carryApprovalStatusList = [];
            this.carryApprovalStatusList = this.appConstants.carryApprovalStatus;
            this.carryApprovalStatus = this.carryApprovalStatusList[0];
            this.allSemesters=[];
           // this.semesterApprovalStatus=this.allSemesters[0].id;
            this.itemsPerPage = +this.resultsPerPage;
            this.applicationCCIGetAll=[];
            this.approvalStatus="";
            this.counter=0;
            this.appliedStatus=0;
            this.approvedStatus=0;
            this.pagination.currentPage=1;
            this.modalStatus="";
            this.studentID="";
            this.semesterId=0;
            this.totalcarry=0;
            this.submitsubmitResult=0;


            this.statusChanged(this.carryApprovalStatus);
            //var url = "lmsAppStatus/leaveApplications/status/" + leaveApprovalStatus + "/pageNumber/" + pageNumber + "/pageSize/" + pageSize;

        }

        private getSemester(studentidTa:string,semesteridTa:number):ng.IPromise<any>{
            this.studentID=studentidTa;
            this.semesterId=semesteridTa;
            var url = '/ums-webservice-academic/academic/applicationCCI/studentId/'+this.studentID+'/semesterId/'+this.semesterId;
            var defer = this.$q.defer();
           // var allSemestersList:Array<Isemester>=[];
            this.httpClient.get(url, 'application/json',
                (json: any, etag: string) => {
             //       allSemestersList=json.entries;
                    console.log("---------------------Rumi---------------------");
                      var totalcarry=json.entries;

                      this.totalcarry=totalcarry[0].TOTALCARRY;
                    console.log("Totalcarry");
                    console.log(totalcarry[0]);
                  //

                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                    this.notify.error("Error in getting leave applications");
                });

            return defer.promise;
        }
        private setResultsPerPage(resultsPerPage: number) {
            if (resultsPerPage >= 3) {
                this.itemsPerPage = resultsPerPage;
            alert(this.itemsPerPage);
            }

        }
        private cancel(){
           // this.getAppliedAndApprovedInfo();
            //this.getSemester();
        }





        private statusChanged(carryApplicationStatus: IConstants) :ng.IPromise<any> {
            this.carryApprovalStatus= carryApplicationStatus;
            if(this.carryApprovalStatus.name.match("Waiting for head's approval")){
             this.approvalStatus="headsApproval";
            }else if(this.carryApprovalStatus.name.match("Approved By Head")){
                this.approvalStatus="approved";
            }else if(this.carryApprovalStatus.name.match("Rejected By Head")){
                this.approvalStatus="rejected";
                
            }

            console.log(this.carryApprovalStatus);
            var defer = this.$q.defer();
            this.applicationCCI=[];
            var appCCIArr: Array<AppCCI> = [];
            this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/'+this.approvalStatus, 'application/json',
                (json: any, etag: string) => {
                    appCCIArr = json.entries;
                    console.log("*****RRRRRR******");
                    console.log("Applicatino cci Updated!!");

                    this.applicationCCI=appCCIArr;
                    console.log(this.applicationCCI);
                    defer.resolve(appCCIArr);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

         private getAppliedAndApprovedInfo() :ng.IPromise<any>{
             var defer = this.$q.defer();


             var appCCIArr: Array<AppCCI> = [];
             this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/getAllcarryInfo', 'application/json',
                 (json: any, etag: string) => {
                     appCCIArr = json.entries;
                     console.log("**********");
                     console.log("Applicatino CCI Appoved Aplied!!");

                     this.applicationCCIGetAll=appCCIArr;
                     console.log(this.applicationCCIGetAll);
                     defer.resolve(appCCIArr);

                 },
                 (response: ng.IHttpPromiseCallbackArg<any>) => {
                     console.error(response);
                 });
             return defer.promise;//getAllcarryInfo

         }

        private pageChanged(pageNumber: number) {
            this.setCurrent(pageNumber);
        }
        private setCurrent(currentPage: number) {
            this.pagination.currentPage = currentPage;
        }
        private click(studentId:string,semesterId:number,fullName:string,courseTitle:string,courseNo:string){
            this.getSemester(studentId,semesterId);
            this.studentIdTA=studentId;
            this.semesterIdTA=semesterId;
            this.fullNameTA=fullName;
            this.courseTitleTA=courseTitle;
            this.courseNoTA=courseNo;


        }
        private close(){
            console.log("Rumi");

        }
       private submitModal(submitStatus:string){
           alert(submitStatus);
       }
        private convertToJson(){

        }

    }
UMS.controller("CarryApplicationApproval",CarryApplicationApproval);

}