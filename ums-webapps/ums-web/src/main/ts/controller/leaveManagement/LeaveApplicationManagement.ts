/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */

module ums {
  interface ILeaveApplicationManagement extends ng.IScope {
    leaveTypes: Array<LmsType>;
    leaveType: LmsType;
    leaveApplication: LmsApplication;
    remainingLeaves: Array<RemainingLmsLeave>;
    pendingApplications: Array<LmsApplication>;
    pendingApplication: LmsApplication;
    applicationStatusList: Array<LmsApplicationStatus>;
    totalLeaveDurationInDays: number;

    showStatusSection: boolean;

    save: Function;
    applyLeave: Function;
    fetchApplicationStatus: Function;
    closeStatusSection: Function;
    getTotalDuration: Function;
    updateLeaveType: Function;
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
      $scope.totalLeaveDurationInDays = 0;
      $scope.save = this.save.bind(this);
      $scope.applyLeave = this.applyLeave.bind(this);
      $scope.fetchApplicationStatus = this.fetchApplicationStatus.bind(this);
      $scope.closeStatusSection = this.closeStatusSection.bind(this);
      $scope.getTotalDuration = this.getTotalDuration.bind(this);
      $scope.updateLeaveType = this.updateLeaveType.bind(this);

      this.getLeaveTypes();
      this.getRemainingLeaves();
      this.getPendingApplications();
    }

    private getLeaveTypes() {
      this.$scope.leaveTypes = [];
      this.$scope.leaveType = <LmsType>{};
      this.leaveTypeService.fetchLeaveTypes().then((leaveTypes) => {
        this.$scope.leaveTypes = leaveTypes;
        this.$scope.leaveType = this.$scope.leaveTypes[0];
      });
    }

    private updateLeaveType(lmsType: LmsType) {
      this.$scope.leaveType = lmsType;
    }

    private getTotalDuration() {
      console.log("In total duration");
      if (this.$scope.leaveApplication.toDate != null && this.$scope.leaveApplication.fromDate != null) {
        var fromDate = new Date(this.$scope.leaveApplication.fromDate);
        var toDate = new Date(this.$scope.leaveApplication.toDate);
        var timeDiff = Math.abs(toDate.getTime() - fromDate.getTime());
        this.$scope.totalLeaveDurationInDays = Math.ceil((timeDiff / (1000 * 3600 * 24)));
      }
    }

    private getPendingApplications() {
      this.$scope.pendingApplications = [];
      this.leaveApplicationService.fetchPendingLeaves().then((pendingLeaves) => {
        this.$scope.pendingApplications = pendingLeaves;
        console.log("Pending leaves");
        console.log(pendingLeaves);
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
        });
      });
    }

    private closeStatusSection() {
      this.$scope.showStatusSection = false;
    }

    private applyLeave() {
      this.convertToJson(Utils.LEAVE_APPLICATION_PENDING).then((json) => {
        this.leaveApplicationService.saveLeaveApplication(json).then((message) => {
          this.$scope.leaveApplication = <LmsApplication>{};
          this.$scope.leaveType = this.$scope.leaveTypes[0];
          this.getPendingApplications();
        });
      });
    }

    private fetchApplicationStatus(pendingApplication: LmsApplication) {
      this.$scope.showStatusSection = true;
      this.$scope.pendingApplication = pendingApplication;
      this.$scope.applicationStatusList = [];
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.id).then((statusList: Array<LmsApplicationStatus>) => {
        console.log("Status list");
        console.log(statusList);
        this.$scope.applicationStatusList = statusList;
      });
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