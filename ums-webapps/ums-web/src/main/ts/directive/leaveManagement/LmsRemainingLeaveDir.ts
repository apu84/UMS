module ums{
  interface IConstants{
    id: number;
    name: string;
  }

  class LmsRemainingLeaveController{

    private showMore: boolean = false;

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
      this.getRemainingLeaves();
      this.showMore = false;
    }

    public showMoreRemainingLeaves(){
      this.showMore = true;
    }

    public showLessRemainingLeaves(){
      this.showMore = false;
    }

    private getRemainingLeaves() {

      console.log("From the remaining leave directive");
      this.leaveApplicationService.remainingLeaves = [];
      this.leaveApplicationService.remainingLeavesMap = {};
      this.leaveApplicationService.fetchRemainingLeavesByEmployeeId(this.leaveApplicationService.employeeId).then((leaves: Array<RemainingLmsLeave>) => {
        console.log("remaining leaves");
        console.log(leaves);
        for (let i = 0; i < leaves.length; i++) {
          this.leaveApplicationService.remainingLeaves.push(leaves[i]);
          this.leaveApplicationService.remainingLeavesMap[leaves[i].leaveTypeId] = this.leaveApplicationService.remainingLeaves[i];
        }
      });
    }
  }

  class LmsRemainingLeaveDir implements ng.IDirective{

    constructor(){

    }

    public restrict: string = "A";
    public scope={};

    public controller = LmsRemainingLeaveController;
    public controllerAs = 'vm';

    public link = (scope:any, element:any, attributes:any)=>{

    };

    public templateUrl: string = './views/directive/remaining-leave.html';

  }

  UMS.directive('lmsRemainingLeaveDir', [()=>{
    return new LmsRemainingLeaveDir();
  }])

}