/**
 * Created by My Pc on 22-May-17.
 */
module ums {
  interface ILeaveApplicationApproval extends ng.IScope {
    leaveApprovalStatusList: Array<IConstants>;
    leaveApprovalStatus: IConstants;
    itemsPerPage: string;
    pageNumber: string;
    pendingApplications: Array<LmsApplicationStatus>;
    pendingApplication: LmsApplicationStatus;
  }

  interface  IConstants {
    id: number;
    name: string;
  }

  class LeaveApplicationApproval {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService'];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: ILeaveApplicationApproval,
                private $q: ng.IQService,
                private notify: Notify,
                private $sce: ng.ISCEService,
                private $window: ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private programService: ProgramService,
                private $timeout: ng.ITimeoutService,
                private leaveTypeService: LeaveTypeService,
                private leaveApplicationService: LeaveApplicationService, private leaveApplicationStatusService: LeaveApplicationStatusService) {

      $scope.itemsPerPage = "10";
      $scope.pageNumber = "1";
      this.initialization();
    }


    private initialization() {
      console.log("Leave constants");
      console.log(this.appConstants.leaveApprovalStatus);
      this.$scope.leaveApprovalStatusList = [];
      this.$scope.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[0];
    }

    private getLeaveApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.$scope.leaveApprovalStatus.id, +this.$scope.pageNumber, +this.$scope.itemsPerPage).then((apps: Array<LmsApplicationStatus>) => {
        this.$scope.pendingApplications = apps;
      });
    }


  }

  UMS.controller("LeaveApplicationApproval", LeaveApplicationApproval);
}