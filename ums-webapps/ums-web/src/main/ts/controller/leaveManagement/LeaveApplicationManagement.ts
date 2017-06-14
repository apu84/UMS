/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */

module ums {
  interface ILeaveApplicationManagement extends ng.IScope {
    leaveTypes: Array<LmsType>;
    leaveApprovalStatusList: Array<IConstants>;
    leaveApprovalStatus: IConstants;
    leaveType: LmsType;
    leaveApplication: LmsApplication;
    remainingLeaves: Array<RemainingLmsLeave>;
    remainingLeavesMap: any;
    pendingApplications: Array<LmsApplicationStatus>;
    pendingApplication: LmsApplicationStatus;
    applicationStatusList: Array<LmsApplicationStatus>;
    itemsPerPage: number;
    pageNumber: number;
    pagination: any;
    totalItems: number;
    statusModal: LmsApplicationStatus;
    data: any;
    employeeId: string;
    user: User;


    showStatusSection: boolean;
    showHistorySection: boolean;
    showApplicationSection: boolean;
    fromPendingApplicationSection: boolean;
    fromHistorySection: boolean;

    save: Function;
    applyLeave: Function;
    fetchApplicationStatus: Function;
    closeStatusSection: Function;
    getTotalDuration: Function;
    updateLeaveType: Function;
    pageChanged: Function;
    setStatusModalContent: Function;
    dateChanged: Function;
    showHistory: Function;
    closeHistory: Function;
    statusChanged: Function;
    setResultsPerPage: Function;

  }

  interface  IConstants {
    id: number;
    name: string;
  }

  class LeaveApplicationManagement {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService'];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: ILeaveApplicationManagement,
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
                private userService: UserService) {

      $scope.leaveApplication = <LmsApplication>{};
      $scope.showStatusSection = false;
      $scope.showHistorySection = false;
      $scope.fromHistorySection = false;
      $scope.fromPendingApplicationSection = true;
      $scope.showApplicationSection = true;
      $scope.data = {};
      $scope.data.totalLeaveDurationInDays = 0;
      $scope.pageNumber = 1;
      $scope.pagination = {};
      $scope.pagination.currentPage = 1;
      $scope.itemsPerPage = 50;
      $scope.save = this.save.bind(this);
      $scope.applyLeave = this.applyLeave.bind(this);
      $scope.fetchApplicationStatus = this.fetchApplicationStatus.bind(this);
      $scope.closeStatusSection = this.closeStatusSection.bind(this);
      $scope.getTotalDuration = this.getTotalDuration.bind(this);
      $scope.updateLeaveType = this.updateLeaveType.bind(this);
      $scope.pageChanged = this.pageChanged.bind(this);
      $scope.setStatusModalContent = this.setStatusModalContent.bind(this);
      $scope.dateChanged = this.dateChanged.bind(this);
      $scope.showHistory = this.showHistory.bind(this);
      $scope.closeHistory = this.closeHistory.bind(this);
      $scope.statusChanged = this.statusChanged.bind(this);
      $scope.setResultsPerPage = this.setResultsPerPage.bind(this);
      this.initializeDatePickers();

      this.getLeaveTypes();
      this.getRemainingLeaves();
      this.getPendingApplications();
      this.getUsersInformation();
    }


    private getUsersInformation() {
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.$scope.user = user;
        this.$scope.employeeId = this.$scope.employeeId;
        console.log("Users....");
        console.log(user);
      });
    }

    private showHistory() {
      this.$scope.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.$scope.leaveApprovalStatus = this.$scope.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_ALL - 1];
      console.log(this.$scope.leaveApprovalStatusList[8 - 1]);
      console.log("leave approval status: " + this.$scope.leaveApprovalStatus);
      this.$scope.pageNumber = 1;
      this.$scope.itemsPerPage = 10;
      this.getAllLeaveApplicationsForHistory();
      this.$scope.showHistorySection = true;
      this.$scope.showApplicationSection = false;
      this.$scope.fromHistorySection = true;
      this.$scope.fromPendingApplicationSection = false;
    }

    private getAllLeaveApplicationsForHistory() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.$scope.user.employeeId, this.$scope.leaveApprovalStatus.id, this.$scope.pageNumber, this.$scope.itemsPerPage).then((leaveApplications) => {
        this.$scope.pendingApplications = leaveApplications.statusList;
        this.$scope.totalItems = leaveApplications.totalSize;
        console.log(this.$scope.pendingApplications);
      });
    }

    private closeHistory() {
      this.$scope.showHistorySection = false;
      this.$scope.pageNumber = 1;
      this.$scope.itemsPerPage = 50;
      this.$scope.showApplicationSection = true;
      this.$scope.fromHistorySection = false;
      this.$scope.fromPendingApplicationSection = true;
      this.getPendingApplications();
    }


    private setResultsPerPage(itemPerPage: number) {
      this.$scope.itemsPerPage = itemPerPage;
      if (itemPerPage > 0 && itemPerPage != null)
        this.getAllLeaveApplicationsForHistory();
    }


    private statusChanged(leaveApplicationStatus: IConstants) {
      this.$scope.leaveApprovalStatus = leaveApplicationStatus;
      this.getAllLeaveApplicationsForHistory();
    }

    private initializeDatePickers() {
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);
    }

    private getLeaveTypes() {
      this.$scope.leaveTypes = [];
      this.$scope.leaveType = <LmsType>{};
      this.leaveTypeService.fetchLeaveTypes().then((leaveTypes) => {
        this.$scope.leaveTypes = leaveTypes;
        this.$scope.leaveType = this.$scope.leaveTypes[0];
        console.log("Leave types");
        console.log(this.$scope.leaveTypes);
      });
    }

    private dateChanged() {
      console.log("In the date changed");
      var thisScope = this;
      setTimeout(function () {
        thisScope.getTotalDuration();
      }, 200);
    }


    private setStatusModalContent(lmsApplicationStatus: LmsApplicationStatus) {
      this.$scope.statusModal = lmsApplicationStatus;
    }


    private updateLeaveType(lmsType: LmsType) {
      this.$scope.leaveType = lmsType;
    }

    private getTotalDuration() {
      if (this.$scope.leaveApplication.toDate != null && this.$scope.leaveApplication.fromDate != null) {
        var fromDateParts: any = this.$scope.leaveApplication.fromDate.split('-');
        var fromDate = new Date(fromDateParts[2], fromDateParts[1], fromDateParts[0]);

        var toDateParts: any = this.$scope.leaveApplication.toDate.split('-');
        var toDate = new Date(toDateParts[2], toDateParts[1], toDateParts[0]);

        var timeDiff: any = Math.abs(toDate.getTime() - fromDate.getTime());
        var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
        this.$scope.leaveApplication.duration = diffDays + 1;
        if (this.$scope.remainingLeavesMap[this.$scope.leaveType.id].daysLeft < this.$scope.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.$scope.leaveApplication.duration + " days left for the leave type");
        }
        this.$scope.$apply();
      }
    }

    private getPendingApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchPendingLeaves().then((pendingLeaves) => {
        this.$scope.pendingApplications = pendingLeaves;
        console.log("Pending leaves...");
        console.log(pendingLeaves);
        this.$scope.totalItems = pendingLeaves.length;
        //this.$scope.employeeId = angular.copy(this.$scope.pendingApplications[0].applicantsId);
      });
    }

    private getRemainingLeaves() {
      this.$scope.remainingLeaves = [];
      this.$scope.remainingLeavesMap = {};
      this.leaveApplicationService.fetchRemainingLeaves().then((leaves: Array<RemainingLmsLeave>) => {
        for (var i = 0; i < leaves.length; i++) {
          this.$scope.remainingLeaves.push(leaves[i]);
          this.$scope.remainingLeavesMap[leaves[i].leaveTypeId] = this.$scope.remainingLeaves[i];
        }
        console.log("remaining leave map");
        console.log(this.$scope.remainingLeavesMap);
      });
    }

    private save() {
      this.convertToJson(Utils.LEAVE_APPLICATION_SAVED).then((json) => {
        this.leaveApplicationService.saveLeaveApplication(json).then((message) => {
          this.$scope.leaveApplication = <LmsApplication>{};
          this.$scope.leaveType = this.$scope.leaveTypes[0];
          this.$scope.data.totalLeaveDurationInDays = 0;
        });
      });
    }

    private closeStatusSection() {
      this.$scope.showStatusSection = false;
      if (this.$scope.fromHistorySection)
        this.$scope.showHistorySection = true;
      else
        this.$scope.showApplicationSection = true;
    }

    private applyLeave() {
      console.log("**************");
      console.log("In apply leave method");
      var foundOccurance: boolean = false;
      this.findIfThereIsAnyOvalapping(foundOccurance).then((occuranceStatus: boolean) => {
        foundOccurance = occuranceStatus;
        if (this.$scope.leaveApplication.fromDate == null || this.$scope.leaveApplication.toDate == null || this.$scope.leaveApplication.reason == null) {
          this.notify.error("Please fill up all the fields");
        }
        else if (this.$scope.remainingLeavesMap[this.$scope.leaveType.id].daysLeft < this.$scope.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.$scope.leaveApplication.duration + " days of the leave type");
        }
        else if (foundOccurance) {
          this.notify.error("Date overlapping is not allowed! Please check your approved applications in  pending leaves or histories.");
        }
        else {
          this.convertToJson(Utils.LEAVE_APPLICATION_PENDING).then((json) => {
            this.leaveApplicationService.saveLeaveApplication(json).then((message) => {
              this.$scope.leaveApplication = <LmsApplication>{};
              this.$scope.leaveType = this.$scope.leaveTypes[0];
              this.getPendingApplications();
            });
          });
        }
      });

    }

    private findIfThereIsAnyOvalapping(foundOccurance: boolean): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.leaveApplicationService.fetchApprovedLeavesWithDateRange(this.$scope.leaveApplication.fromDate, this.$scope.leaveApplication.toDate).then((applications: any) => {
        if (applications.length > 0)
          foundOccurance = true;
        else
          foundOccurance = false;

        defer.resolve(foundOccurance);
      });
      return defer.promise;
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.showStatusSection = true;
      this.$scope.showHistorySection = false;
      this.$scope.showApplicationSection = false;
      this.$scope.pendingApplication = pendingApplication;
      this.$scope.applicationStatusList = [];
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.$scope.applicationStatusList = statusList;
      });
    }

    private pageChanged(currentPage: number) {
      console.log("current page: " + currentPage);
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.pageNumber = currentPage;
      if (this.$scope.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.getRemainingLeaves();
      }

    }


    private convertToJson(appType: number): ng.IPromise<any> {
      var application: LmsApplication = this.$scope.leaveApplication;
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];
      console.log("Lms application");
      var item: any = {};
      item['id'] = application.id;
      item['employeeId'] = application.employeeId;
      item['typeId'] = this.$scope.leaveType.id;
      item['fromDate'] = application.fromDate;
      item['toDate'] = application.toDate;
      item['reason'] = application.reason;
      item['appStatus'] = appType;
      jsonObject.push(item);
      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("LeaveApplicationManagement", LeaveApplicationManagement);
}