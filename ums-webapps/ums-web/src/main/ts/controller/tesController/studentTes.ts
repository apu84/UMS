/*
* Rumi-21-1-1028
* carry Approval Controller
* */
module ums{

    class studentTES{
        public  value:string;
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
            this.value="hello world";

        }
    }
    UMS.controller("studentTES",studentTES);
}