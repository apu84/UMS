module ums {
  export class EmployeeLeaveApplicationController {

    private employeeList: Employee[];
    private selectedEmployee: Employee;
    private showApplicationSection: boolean;


    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService', 'attachmentService', '$state', 'employeeService'];

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
                private attachmentService: AttachmentService, private $state: any, private employeeService: EmployeeService) {

      this.init();
    }

    private init() {
      this.showApplicationSection = false;
      this.fetchDeptEmployeeList();
    }


    public employeeSelected() {

      this.showApplicationSection = false;
      this.leaveApplicationService.employeeId = this.selectedEmployee.id;
      this.userService.getUser(this.selectedEmployee.id).then((user: User) => {
        console.log("Fetched user");
        console.log(user);
        this.leaveApplicationService.user = user;
        this.leaveApplicationService.user.employeeId = this.selectedEmployee.id;
        this.showApplicationSection = true;
      })
    }

    private fetchDeptEmployeeList() {
      this.userService.fetchCurrentUserInfo().then((user: User) => {
        this.employeeService.getEmployees(user.departmentId).then((employeeList: Employee[]) => {
          this.employeeList = employeeList;
        });
      })
    }
  }

  UMS.controller("employeeLeaveApplicationController", EmployeeLeaveApplicationController);
}