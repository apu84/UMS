module ums{
    class HeadTES{
        public checkAssignCourse:boolean;
        public checkShowResults:boolean;
        public checkShowRecords:boolean;
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
                    private attachmentService: AttachmentService){
            this.checkAssignCourse=true;
            this.checkShowResults=true;
            this.checkShowRecords=true;

        }

        private assignCourse(){
            this.checkAssignCourse=false;
            this.checkShowResults=true;
            this.checkShowRecords=true;

            console.log(this.checkAssignCourse);
        }
        private showResults(){
            this.checkShowResults=false;
            this.checkAssignCourse=true;
            this.checkShowRecords=true;
        }
        private ShowRecords(){
            this.checkShowRecords=false;
            this.checkShowResults=true;
            this.checkAssignCourse=true;
        }
    }
    UMS.controller("HeadTES",HeadTES)
}