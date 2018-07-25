module ums{
  interface IConstants{
    id: number;
    name: string;
  }

  class LmsPendingLeaveController{

    private showMore: boolean = false;
    public pendingApplications: Array<LmsApplicationStatus>;
    public pendingApplication: LmsApplicationStatus;

    public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService', 'attachmentService'];

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
                private attachmentService: AttachmentService) {

    }

    private getPendingApplications() {
      this.pendingApplications = [];

      this.leaveApplicationStatusService.fetchPendingLeaves(this.leaveApplicationService.employeeId).then((pendingLeaves) => {
        this.pendingApplications = pendingLeaves;
        //this.leaveApplicationService.employeeId = angular.copy(this.pendingApplications[0].applicantsId);
      });
    }

  }

  class LmsPendingLeaveDir implements ng.IDirective{

    constructor(){

    }

    public restrict: string = "A";
    public scope={};

    public controller = LmsPendingLeaveController;
    public controllerAs = 'vm';

    public link = (scope:any, element:any, attributes:any)=>{

    };

    public templateUrl: string = './views/directive/pending-leave.html';

  }

  UMS.directive('lmsPendingLeaveDir', [()=>{
    return new LmsPendingLeaveDir();
  }])

}