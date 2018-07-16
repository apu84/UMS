module ums {
  export class LeaveApplicationController {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService', 'attachmentService', '$state'];

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
                private attachmentService: AttachmentService, private $state: any) {

      this.init();
    }

    private init() {
      this.getUsersInformation();
    }

    private getUsersInformation() {
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.leaveApplicationService.user = user;
        this.leaveApplicationService.employeeId = this.leaveApplicationService.user.employeeId;
        this.$state.go('leaveApplication.leaveApplicationManagement', {}, {reload: 'leaveApplication.leaveApplicationManagement'});
      });
    }
  }

  UMS.controller("LeaveApplicationController", LeaveApplicationController);
}