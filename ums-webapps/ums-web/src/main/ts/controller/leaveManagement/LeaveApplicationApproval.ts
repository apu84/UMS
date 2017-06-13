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
    backgroundColor: string;
    disableApproveAndRejectButton: boolean;
    additionalRoles: Array<AdditionalRolePermissions>;
    pendingApplications: Array<LmsApplicationStatus>;
    pendingApplication: LmsApplicationStatus;
    remainingLeaves: Array<RemainingLmsLeave>;
    approveOrRejectionComment: string;
    data: any;
    applicantsId: string;
    deptOffices: Array<IConstants>;
    deptOffice: IConstants;

    approveButtonClicked: boolean;
    rejectButtonClicked: boolean;
    showHistorySection: boolean;
    showApprovalSection: boolean;
    showStatusSection: boolean;
    fromHistory: boolean;
    activeLeaveSection: boolean;
    fromActiveLeaveSection: boolean;


    statusChanged: Function;
    showLeaveSection: Function;
    closeStatusSection: Function;
    fetchApplicationStatus: Function;
    pageChanged: Function;
    setCurrent: Function;
    setResultsPerPage: Function;
    approve: Function;
    reject: Function;
    setStatusModalContent: Function;
    saveAction: Function;
    showHistory: Function;
    closeHistory: Function;
    showActiveLeaveSection: Function;
    closeActiveLeaveSection: Function;
    initializeDepartmentOffice: Function;
  }

  interface  IConstants {
    id: any;
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
      $scope.showApprovalSection = true;
      $scope.backgroundColor = "white";
      $scope.showHistorySection = false;
      $scope.showStatusSection = false;
      $scope.activeLeaveSection = false;
      $scope.fromActiveLeaveSection = false;
      $scope.fromHistory = false;
      $scope.pagination = {};
      $scope.pagination.currentPage = 1;
      $scope.itemsPerPage = +$scope.resultsPerPage;
      $scope.disableApproveAndRejectButton = true;
      $scope.approveOrRejectionComment = "";
      $scope.data = {};
      $scope.pageNumber = 1;
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
      $scope.showHistory = this.showHistory.bind(this);
      $scope.closeHistory = this.closeHistory.bind(this);
      $scope.showActiveLeaveSection = this.showActiveLeaveSection.bind(this);
      $scope.closeActiveLeaveSection = this.closeActiveLeaveSection.bind(this);
      $scope.initializeDepartmentOffice = this.initializeDepartmentOffice.bind(this);
      //this.getLeaveApplications();
      this.getUsersInformation();
      this.getAdditionaPermissions();
    }

    private showActiveLeaveSection() {
      this.$scope.activeLeaveSection = true;
      this.$scope.showHistorySection = false;
      this.$scope.showStatusSection = false;
      this.$scope.showApprovalSection = false;
      this.$scope.itemsPerPage = 10;
      this.initializeDepartmentOffice().then((deptOffice: IConstants) => {
        this.fetchActiveLeaves(deptOffice);
      });
    }

    private fetchActiveLeaves(deptOffice: IConstants) {
      this.$scope.pagination.currentPage = 1;
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsActiveOnTheDay(deptOffice.id, this.$scope.pagination.currentPage, this.$scope.itemsPerPage).then((apps) => {
        this.$scope.pendingApplications = apps.statusList;
        this.$scope.totalItems = apps.totalSize;
        console.log("active leaves");
        console.log(apps);
      });
    }

    private closeActiveLeaveSection() {
      this.$scope.activeLeaveSection = false;
      this.$scope.showHistorySection = false;
      this.$scope.showStatusSection = false;
      this.$scope.showApprovalSection = true;
      this.getLeaveApplications();
    }

    private initializeDepartmentOffice(deptOffice?: IConstants): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.$scope.deptOffices = [];
      this.$scope.deptOffices = this.appConstants.departmentOffice;
      if (deptOffice != null) {
        this.$scope.deptOffice = deptOffice;
        console.log("Dept office");
        console.log(this.$scope.deptOffice);
        this.fetchActiveLeaves(this.$scope.deptOffice);
      }
      else {
        for (var i = 0; i < this.$scope.deptOffices.length; i++) {
          if (this.$scope.deptOffices[i].id == Utils.DEPT_ALL)
            this.$scope.deptOffice = this.$scope.deptOffices[i];
        }
      }

      defer.resolve(this.$scope.deptOffice);
      return defer.promise;
    }

    private showHistory() {
      this.$scope.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_ALL - 1];
      this.$scope.pageNumber = 1;
      this.$scope.itemsPerPage = 10;
      this.getAllLeaveApplicationsForHistory();
      this.$scope.showHistorySection = true;
      this.$scope.showStatusSection = false;
      this.$scope.showApprovalSection = false;
      this.$scope.backgroundColor = "#FAFAD2";

    }

    private closeHistory() {
      this.$scope.showHistorySection = false;
      this.$scope.showStatusSection = true;
      this.$scope.showApprovalSection = false;
      this.getUsersInformation();
      this.$scope.backgroundColor = "white";
    }


    private getAllLeaveApplicationsForHistory() {
      this.$scope.pendingApplications = [];
      console.log(this.$scope.pageNumber);
      console.log(this.$scope.itemsPerPage);
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.$scope.applicantsId, this.$scope.leaveApprovalStatus.id, this.$scope.pagination.currentPage, this.$scope.itemsPerPage).then((leaveApplications) => {
        this.$scope.pendingApplications = leaveApplications.statusList;
        this.$scope.totalItems = leaveApplications.totalSize;
        console.log("Histories...");
        console.log(this.$scope.pendingApplications);
      });
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
          this.$scope.disableApproveAndRejectButton = true;
          this.fetchApplicationStatus(this.$scope.pendingApplication, this.$scope.pagination.currentPage);
        });
      });
    }

    private getUsersInformation(): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.$scope.user = user;
        if (this.$scope.user.roleId == Utils.VC)
          this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_VC_APPROVAL - 1];
        else if (this.$scope.user.roleId == Utils.REGISTRAR)
          this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_REGISTRARS_APPROVAL - 1];
        else
          this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_HEADS_APPROVAL - 1];

        this.fetchLeaveApplications();
        defer.resolve(this.$scope.leaveApprovalStatus);

      });

      return defer.promise;
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
      if (this.$scope.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.getLeaveApplications();
      }
    }


    private closeStatusSection() {
      this.$scope.showStatusSection = false;
      if (this.$scope.fromHistory == true && this.$scope.showHistorySection == true) {
        this.showHistory();
      }
      else if (this.$scope.fromActiveLeaveSection == true) {
        this.showActiveLeaveSection();
      }
      else {
        this.$scope.showApprovalSection = true;
        this.$scope.showHistorySection = false;
        this.getLeaveApplications();
      }


    }

    private setResultsPerPage(resultsPerPage: number) {
      if (resultsPerPage >= 1) {
        this.$scope.itemsPerPage = resultsPerPage;
        if (this.$scope.showHistorySection) {
          this.getAllLeaveApplicationsForHistory();
          console.log("In the history section");
        }
        else if (this.$scope.activeLeaveSection) {
          this.fetchActiveLeaves(this.$scope.deptOffice);
          console.log("In the active leave section");
        }
        else
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
      this.fetchLeaveApplications();
    }

    private fetchLeaveApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.$scope.leaveApprovalStatus.id, this.$scope.pagination.currentPage, this.$scope.itemsPerPage).then((apps) => {
        this.$scope.pendingApplications = apps.statusList;
        this.$scope.totalItems = this.$scope.pendingApplications.length > 0 ? apps.totalSize : 0;
      });
    }

    private setCurrent(currentPage: number) {
      console.log("In set current");
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.pendingApplications = [];
      if (this.$scope.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.$scope.leaveApprovalStatus.id, currentPage, this.$scope.itemsPerPage).then((apps) => {
          this.$scope.pendingApplications = apps.statusList;
          this.$scope.totalItems = apps.totalSize;
        });
      }
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      if (this.$scope.showHistorySection == true) {
        this.$scope.fromHistory = true;
      } else {
        this.$scope.fromHistory = false;
      }
      if (this.$scope.activeLeaveSection == true) {
        this.$scope.fromActiveLeaveSection = true;
      } else {
        this.$scope.fromActiveLeaveSection = false;
      }
      this.$scope.applicantsId = angular.copy(pendingApplication.applicantsId);
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.showStatusSection = true;
      this.$scope.showApprovalSection = false;
      this.$scope.showHistorySection = false;
      this.$scope.activeLeaveSection = false;
      this.$scope.pendingApplication = pendingApplication;
      this.$scope.applicationStatusList = [];
      this.$scope.approveButtonClicked = false;
      this.$scope.rejectButtonClicked = false;

      this.getRemainingLeaves(pendingApplication.applicantsId);

      console.log("disableApproveAndRejectButton:" + this.$scope.disableApproveAndRejectButton);
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.$scope.applicationStatusList = statusList;
        this.decideWhetherToEnableOrDisableActionButtons(this.$scope.applicationStatusList[this.$scope.applicationStatusList.length - 1]);
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