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

    class CarryApplicationApproval{
        public carryApprovalStatusList: Array<IConstants>;
        public carryApprovalStatus: IConstants;
        public allSemesters:Array<Isemester>;
        public semesterApprovalStatus:number;
        public itemsPerPage: number;
        public resultsPerPage: string;


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

            this.getSemester();

        }

        private getSemester():ng.IPromise<any>{
            var url = "academic/semester/all";
            var defer = this.$q.defer();
            var allSemestersList:Array<Isemester>=[];
            this.httpClient.get(url, 'application/json',
                (json: any, etag: string) => {
                    allSemestersList=json.entries;
                    console.log("---------------------Rumi---------------------");

                    this.allSemesters=allSemestersList;
                    console.log(this.allSemesters);
                  //

                    defer.resolve(allSemestersList);
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
            this.getSemester();
          //  alert('Hi'+this.carryApprovalStatus);
        }




    }
UMS.controller("CarryApplicationApproval",CarryApplicationApproval);

}