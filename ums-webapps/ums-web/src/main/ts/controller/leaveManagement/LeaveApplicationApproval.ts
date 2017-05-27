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
    pagination: any;

    showStatusSection: boolean;


    pendingApplications: Array<LmsApplicationStatus>;
    pendingApplication: LmsApplicationStatus;

    statusChanged: Function;
    closeStatusSection: Function;
    fetchApplicationStatus: Function;
    pageChanged: Function;
    setCurrent: Function;
    setResultsPerPage: Function;
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

      $scope.resultsPerPage = "3";
      $scope.pagination = {};
      $scope.pagination.currentPage = 1;
      $scope.itemsPerPage = +$scope.resultsPerPage;
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
      this.getLeaveApplications();
    }


    private pageChanged(pageNumber: number) {
      this.setCurrent(pageNumber);
    }

    private initialization() {
      console.log("Leave constants");
      console.log(this.appConstants.leaveApprovalStatus);

    }

    private statusChanged(leaveApplicationStatus: IConstants) {
      this.$scope.leaveApprovalStatus = leaveApplicationStatus;
      this.getLeaveApplications();
    }


    private closeStatusSection() {
      this.$scope.showStatusSection = false;
    }

    private setResultsPerPage(resultsPerPage: number) {
      if (resultsPerPage >= 1) {
        this.$scope.itemsPerPage = resultsPerPage;
        this.getLeaveApplications();
      }

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
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        console.log("Status list");
        console.log(statusList);
        this.$scope.applicationStatusList = statusList;
      });
    }

  }

  UMS.controller("LeaveApplicationApproval", LeaveApplicationApproval);
}