module ums {
  export class EmployeeLeaveApplicationController {

    private employeeList: Employee[];
    private selectedEmployee: Employee;


    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService', 'attachmentService', 'employeeService'];

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
                private userService: UserService,
                private attachmentService: AttachmentService,
                private employeeService: EmployeeService) {


    }

    private init() {

    }


    public employeeSelected() {
      this.userService.getUser(this.selectedEmployee.shortName).then((user: User) => {
        this.leaveApplicationService.user = user;
        this.leaveApplicationService.employeeId = user.employeeId;
      })
    }

    private fetchDeptEmployeeList(deptId: string) {
      this.userService.fetchCurrentUserInfo().then((user: User) => {
        this.employeeService.getEmployees(user.departmentId).then((employeeList: Employee[]) => {
          this.employeeList = employeeList;
        });
      })
    }
  }

  UMS.controller("employeeLeaveApplication", EmployeeLeaveApplicationController);
}