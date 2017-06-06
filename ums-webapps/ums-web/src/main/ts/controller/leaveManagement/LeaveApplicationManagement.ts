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


    showStatusSection: boolean;
    showHistorySection: boolean;
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
    setResultPerPage: Function;

  }

  interface  IConstants {
    id: number;
    name: string;
  }

  class LeaveApplicationManagement {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService'];

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
                private leaveApplicationService: LeaveApplicationService, private leaveApplicationStatusService: LeaveApplicationStatusService) {

      $scope.leaveApplication = <LmsApplication>{};
      $scope.showStatusSection = false;
      $scope.showHistorySection = false;
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
      $scope.setResultPerPage = this.setResultPerPage.bind(this);
      this.initializeDatePickers();

      this.getLeaveTypes();
      this.getRemainingLeaves();
      this.getPendingApplications();
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
    }

    private getAllLeaveApplicationsForHistory() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.$scope.employeeId, this.$scope.leaveApprovalStatus.id, this.$scope.pageNumber, this.$scope.itemsPerPage).then((leaveApplications) => {
        this.$scope.pendingApplications = leaveApplications.statusList;
        this.$scope.totalItems = leaveApplications.totalSize;
        console.log(this.$scope.pendingApplications);
      });
    }

    private closeHistory() {
      this.$scope.showHistorySection = false;
      this.$scope.pageNumber = 1;
      this.$scope.itemsPerPage = 50;
    }


    private setResultPerPage(itemPerPage: number) {
      this.$scope.itemsPerPage = itemPerPage;
      if (itemPerPage > 0)
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
        this.$scope.leaveApplication.duration = diffDays;
        this.$scope.$apply();
      }
    }

    private getPendingApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationStatusService.fetchPendingLeaves().then((pendingLeaves) => {
        this.$scope.pendingApplications = pendingLeaves;
        this.$scope.totalItems = pendingLeaves.length;
        this.$scope.employeeId = angular.copy(this.$scope.pendingApplications[0].applicantsId);
      });
    }

    private getRemainingLeaves() {
      this.$scope.remainingLeaves = [];
      this.leaveApplicationService.fetchRemainingLeaves().then((leaves) => {
        this.$scope.remainingLeaves = leaves;
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
    }

    private applyLeave() {
      console.log("**************");
      console.log("In apply leave method");
      var foundOccurance: boolean = false;
      var momentFromDate: any = moment(this.$scope.leaveApplication.fromDate).format("DD/MM/YYYY");
      var momentToDate: any = moment(this.$scope.leaveApplication.toDate).format("DD/MM/YYYY");

      for (var i = 0; i < this.$scope.pendingApplications.length; i++) {
        var momentFrom: any = moment(this.$scope.pendingApplications[i].fromDate).format("DD/MM/YYYY");
        var momentTo: any = moment(this.$scope.pendingApplications[i].toDate).format("DD/MM/YYYY");
        if (momentFromDate >= momentFrom && momentToDate >= momentFrom && momentFromDate <= momentTo && momentToDate <= momentTo) {
          foundOccurance = true;
          break;
        }
      }
      if (this.$scope.leaveApplication.fromDate == null || this.$scope.leaveApplication.toDate == null || this.$scope.leaveApplication.reason == null) {
        this.notify.error("Please fill up all the fields");
      } else if (foundOccurance) {
        this.notify.error("Date overlapping is not allowed");
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
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      this.$scope.pagination.currentPage = currentPage;
      this.$scope.showStatusSection = true;
      this.$scope.pendingApplication = pendingApplication;
      this.$scope.applicationStatusList = [];
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        console.log("Status list");
        console.log(statusList);
        this.$scope.applicationStatusList = statusList;
      });
    }

    private pageChanged(currentPage: number) {
      //this.$scope.totalItems = this.$scope.applicationStatusList.length;
      this.$scope.pagination.currentPage = currentPage;
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