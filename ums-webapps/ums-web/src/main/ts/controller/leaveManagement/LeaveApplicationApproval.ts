/**
 * Created by My Pc on 22-May-17.
 */
module ums {
  interface ILeaveApplicationApproval extends ng.IScope {
    leaveApprovalStatusList: Array<IConstants>;
    leaveApprovalStatus: IConstants;
    itemsPerPage: number;
    resultsPerPage: string;
    totalItems: number;
    pageNumber: number;
    applicationStatusList: Array<LmsApplicationStatus>;
    statusModal: LmsApplicationStatus;
    pagination: any;
    user: User;
    showStatusSection: boolean;
    disableApproveAndRejectButton: boolean;
    additionalRoles: Array<AdditionalRolePermissions>;
    pendingApplications: Array<LmsApplicationStatus>;
    pendingApplication: LmsApplicationStatus;
    remainingLeaves: Array<RemainingLmsLeave>;
    approveOrRejectionComment: string;
    data: any;

    approveButtonClicked: boolean;
    rejectButtonClicked: boolean;


    statusChanged: Function;
    closeStatusSection: Function;
    fetchApplicationStatus: Function;
    pageChanged: Function;
    setCurrent: Function;
    setResultsPerPage: Function;
    approve: Function;
    reject: Function;
    setStatusModalContent: Function;
    saveAction: Function;
  }

  interface  IConstants {
    id: number;
    name: string;
  }

  class LeaveApplicationApproval {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'employeeService', 'additionalRolePermissionsService', 'userService', 'commonService'];

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
                private leaveApplicationService: LeaveApplicationService,
                private leaveApplicationStatusService: LeaveApplicationStatusService,
                private employeeService: EmployeeService,
                private additionalRolePermissionsService: AdditionalRolePermissionsService,
                private userService: UserService,
                private commonservice: CommonService) {

      $scope.resultsPerPage = "3";
      $scope.pagination = {};
      $scope.pagination.currentPage = 1;
      $scope.itemsPerPage = +$scope.resultsPerPage;
      $scope.disableApproveAndRejectButton = true;
      $scope.approveOrRejectionComment = "";
      $scope.data = {};
      $scope.pageNumber = 1;
      $scope.showStatusSection = false;
      this.$scope.leaveApprovalStatusList = [];
      this.$scope.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[0];

      $scope.closeStatusSection = this.closeStatusSection.bind(this);
      $scope.fetchApplicationStatus = this.fetchApplicationStatus.bind(this);
      $scope.pageChanged = this.pageChanged.bind(this);
      $scope.statusChanged = this.statusChanged.bind(this);
      $scope.setCurrent = this.setCurrent.bind(this);
      $scope.setResultsPerPage = this.setResultsPerPage.bind(this);
      $scope.setStatusModalContent = this.setStatusModalContent.bind(this);
      $scope.saveAction = this.saveAction.bind(this);
      $scope.approve = this.approve.bind(this);
      $scope.reject = this.reject.bind(this);
      this.getLeaveApplications();
      this.getUsersInformation();
      this.getAdditionaPermissions();
    }

    private approve() {
      this.$scope.approveButtonClicked = true;
    }

    private reject() {
      this.$scope.rejectButtonClicked = true;
    }


    private getAdditionaPermissions() {
      this.additionalRolePermissionsService.fetchLoggedUserAdditionalRolePermissions().then((additionalRolePermissions) => {
        this.$scope.additionalRoles = [];
        this.$scope.additionalRoles = additionalRolePermissions;

        console.log("permissions");
        console.log(additionalRolePermissions);
      });
    }

    private saveAction() {
      this.convertToJson().then((json) => {
        this.leaveApplicationStatusService.saveLeaveApplicationStatus(json).then((message) => {
          this.getRemainingLeaves(this.$scope.pendingApplication.applicantsId);
        });
      });
    }

    private getUsersInformation() {
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.$scope.user = user;
        console.log("users: ");
        console.log(user);
      });
    }


    private pageChanged(pageNumber: number) {
      this.setCurrent(pageNumber);
    }

    private initialization() {
      console.log("Leave constants");
      console.log(this.appConstants.leaveApprovalStatus);

    }


    private setStatusModalContent(lmsApplicationStatus: LmsApplicationStatus) {
      this.$scope.statusModal = lmsApplicationStatus;
    }

    private statusChanged(leaveApplicationStatus: IConstants) {
      this.$scope.leaveApprovalStatus = leaveApplicationStatus;
      this.getLeaveApplications();
    }


    private closeStatusSection() {
      this.$scope.showStatusSection = false;
      this.getLeaveApplications();
    }

    private setResultsPerPage(resultsPerPage: number) {
      if (resultsPerPage >= 1) {
        this.$scope.itemsPerPage = resultsPerPage;
        this.getLeaveApplications();
      }

    }


    private getRemainingLeaves(employeeId: string) {
      this.$scope.remainingLeaves = [];
      this.leaveApplicationService.fetchRemainingLeavesByEmployeeId(employeeId).then((remainingLeaves) => {
        this.$scope.remainingLeaves = remainingLeaves;
      });
    }


    private getLeaveApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.$scope.leaveApprovalStatus.id, this.$scope.pagination.currentPage, this.$scope.itemsPerPage).then((apps) => {
        this.$scope.pendingApplications = apps.statusList;
        this.$scope.totalItems = apps.totalSize;
      });
    }

    private setCurrent(currentPage: number) {
      console.log("In set current");
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.$scope.leaveApprovalStatus.id, currentPage, this.$scope.itemsPerPage).then((apps) => {
        this.$scope.pendingApplications = apps.statusList;
        this.$scope.totalItems = apps.totalSize;
      });
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.showStatusSection = true;
      this.$scope.pendingApplication = pendingApplication;
      this.$scope.applicationStatusList = [];

      this.$scope.approveButtonClicked = false;
      this.$scope.rejectButtonClicked = false;

      this.getRemainingLeaves(pendingApplication.applicantsId);
      this.decideWhetherToEnableOrDisableActionButtons(pendingApplication);


      console.log("disableApproveAndRejectButton:" + this.$scope.disableApproveAndRejectButton);
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.$scope.applicationStatusList = statusList;
      });
    }

    private decideWhetherToEnableOrDisableActionButtons(pendingApplication: ums.LmsApplicationStatus) {
      if (pendingApplication.actionStatus == Utils.LEAVE_APPLICATION_WAITING_FOR_HEADS_APPROVAL) {
        for (var i = 0; i < this.$scope.additionalRoles.length; i++) {
          if (this.$scope.additionalRoles[i].roleId == Utils.DEPT_HEAD || this.$scope.user.roleId == Utils.LIBRARIAN || this.$scope.user.roleId == Utils.COE) {
            this.$scope.disableApproveAndRejectButton = false;
            break;
          }
        }
      } else if (pendingApplication.actionStatus == Utils.LEAVE_APPLICATION_WAITING_FOR_REGISTRARS_APPROVAL) {
        if (this.$scope.user.roleId === Utils.REGISTRAR) {
          this.$scope.disableApproveAndRejectButton = false;
        }
      } else if (pendingApplication.actionStatus === Utils.LEAVE_APPLICATION_WAITING_FOR_VC_APPROVAL) {
        if (this.$scope.user.roleId === Utils.VC) {
          this.$scope.disableApproveAndRejectButton = false;
        }
      } else {
        //Do nothing.
      }
    }


    private convertToJson(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      var item: any = {};
      item['appId'] = this.$scope.pendingApplication.appId;
      item['comments'] = this.$scope.data.comment;
      console.log("approved button clicked or not ***");
      console.log(this.$scope.approveButtonClicked);
      if (this.$scope.approveButtonClicked) {
        item['leaveApprovalStatus'] = Utils.LEAVE_APPLICATION_ACCEPTED;
      } else {
        item['leaveApprovalStatus'] = Utils.LEAVE_APPLICATION_REJECTED;
      }
      jsonObject.push(item);
      completeJson['entries'] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("LeaveApplicationApproval", LeaveApplicationApproval);
}