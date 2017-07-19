/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */

module ums {
  interface ILeaveApplicationManagement extends ng.IScope {

    fileInserted: Function;
  }

  interface IConstants {
    id: number;
    name: string;
  }

  class LeaveApplicationManagement {

    public leaveTypes: Array<LmsType>;
    public leaveApprovalStatusList: Array<IConstants>;
    public leaveApprovalStatus: IConstants;
    public leaveType: LmsType;
    public leaveApplication: LmsApplication;
    public remainingLeaves: Array<RemainingLmsLeave>;
    public remainingLeavesMap: any;
    public pendingApplications: Array<LmsApplicationStatus>;
    public pendingApplication: LmsApplicationStatus;
    public applicationStatusList: Array<LmsApplicationStatus>;
    public itemsPerPage: number;
    public pageNumber: number;
    public pagination: any;
    public totalItems: number;
    public statusModal: LmsApplicationStatus;
    public data: any;
    public employeeId: string;
    public user: User;
    public fileAttachments: Array<Attachment> = [];
    public files: any = {};
    public filesCopy: any = {};

    public showStatusSection: boolean;
    public showHistorySection: boolean;
    public showApplicationSection: boolean;
    public fromPendingApplicationSection: boolean;
    public fromHistorySection: boolean;


    public static $inject = ['appConstants', '$scope', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService'];

    constructor(private appConstants: any,
                private $scope: ILeaveApplicationManagement,
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
                private userService: UserService) {

      this.leaveApplication = <LmsApplication>{};
      this.showStatusSection = false;
      this.showHistorySection = false;
      this.fromHistorySection = false;
      this.fromPendingApplicationSection = true;
      this.showApplicationSection = true;
      this.data = {};
      this.data.totalLeaveDurationInDays = 0;
      this.pageNumber = 1;
      this.pagination = {};
      this.pagination.currentPage = 1;
      this.itemsPerPage = 50;

      $scope.fileInserted = this.fileInserted.bind(this);

      this.initializeDatePickers();
      this.getLeaveTypes();
      this.getRemainingLeaves();
      this.getPendingApplications();
      this.getUsersInformation();
    }


    private removeFile(file: any) {

      console.log("In the remove file");
      console.log("The parameter");
      console.log(file);
      for (var i = 0; i < this.files.length; i++) {
        if (this.files[i].name === file.name) {
          console.log("Found an occurance");
          console.log(this.files[i]);
          this.files[i].splice(i, 1);
        }
        break;
      }

      console.log(this.files);
    }

    private fileInserted(event) {

      console.log("In the file insertion");

      console.log(event);

    }


    private getUsersInformation() {
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.user = user;
        this.employeeId = this.employeeId;
        console.log("Users....");
        console.log(user);
      });
    }

    private showHistory() {
      console.log("Showing file");
      console.log(this.files);

      this.leaveApplicationService.uploadFile(this.files, '1');
      this.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.leaveApprovalStatus = this.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_ALL - 1];
      console.log(this.leaveApprovalStatusList[8 - 1]);
      console.log("leave approval status: " + this.leaveApprovalStatus);
      this.pageNumber = 1;
      this.itemsPerPage = 10;
      this.getAllLeaveApplicationsForHistory();
      this.showHistorySection = true;
      this.showApplicationSection = false;
      this.fromHistorySection = true;
      this.fromPendingApplicationSection = false;
    }

    private getAllLeaveApplicationsForHistory() {
      this.pendingApplications = [];
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.user.employeeId, this.leaveApprovalStatus.id, this.pageNumber, this.itemsPerPage).then((leaveApplications) => {
        this.pendingApplications = leaveApplications.statusList;
        this.totalItems = leaveApplications.totalSize;
        console.log(this.pendingApplications);
      });
    }

    private closeHistory() {
      this.showHistorySection = false;
      this.pageNumber = 1;
      this.itemsPerPage = 50;
      this.showApplicationSection = true;
      this.fromHistorySection = false;
      this.fromPendingApplicationSection = true;
      this.getPendingApplications();
    }


    private setResultsPerPage(itemPerPage: number) {
      if (itemPerPage > 0 && itemPerPage != null) {
        this.itemsPerPage = itemPerPage;
        this.getAllLeaveApplicationsForHistory();
      }
    }


    private statusChanged(leaveApplicationStatus: IConstants) {
      this.leaveApprovalStatus = leaveApplicationStatus;
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
      this.leaveTypes = [];
      this.leaveType = <LmsType>{};
      this.leaveTypeService.fetchLeaveTypes().then((leaveTypes) => {
        this.leaveTypes = leaveTypes;
        this.leaveType = this.leaveTypes[0];
        console.log("Leave types");
        console.log(this.leaveTypes);
      });
    }

    private dateChanged() {
      console.log("In the date changed");
      let thisScope = this;
      setTimeout(function () {
        thisScope.getTotalDuration();
      }, 200);
    }


    private setStatusModalContent(lmsApplicationStatus: LmsApplicationStatus) {
      this.statusModal = lmsApplicationStatus;
    }


    private updateLeaveType(lmsType: LmsType) {
      this.leaveType = lmsType;
    }

    private getTotalDuration() {
      if (this.leaveApplication.toDate != null && this.leaveApplication.fromDate != null) {
        let fromDateParts: any = this.leaveApplication.fromDate.split('-');
        let fromDate = new Date(fromDateParts[2], fromDateParts[1], fromDateParts[0]);

        let toDateParts: any = this.leaveApplication.toDate.split('-');
        let toDate = new Date(toDateParts[2], toDateParts[1], toDateParts[0]);

        let timeDiff: any = Math.abs(toDate.getTime() - fromDate.getTime());
        let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
        this.leaveApplication.duration = diffDays + 1;
        if (this.remainingLeavesMap[this.leaveType.id].daysLeft < this.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.leaveApplication.duration + " days left for the leave type");
        }

        this.$scope.$apply();

      }


    }

    private getPendingApplications() {
      this.pendingApplications = [];
      this.leaveApplicationStatusService.fetchPendingLeaves().then((pendingLeaves) => {
        this.pendingApplications = pendingLeaves;
        console.log("Pending leaves...");
        console.log(pendingLeaves);
        this.totalItems = pendingLeaves.length;
        //this.employeeId = angular.copy(this.pendingApplications[0].applicantsId);
      });
    }

    private getRemainingLeaves() {
      this.remainingLeaves = [];
      this.remainingLeavesMap = {};
      this.leaveApplicationService.fetchRemainingLeaves().then((leaves: Array<RemainingLmsLeave>) => {
        for (let i = 0; i < leaves.length; i++) {
          this.remainingLeaves.push(leaves[i]);
          this.remainingLeavesMap[leaves[i].leaveTypeId] = this.remainingLeaves[i];
        }
        console.log("remaining leave map");
        console.log(this.remainingLeavesMap);
      });
    }

    private save() {
      this.convertToJson(Utils.LEAVE_APPLICATION_SAVED).then((json) => {
        this.leaveApplicationService.saveLeaveApplication(json).then((message) => {
          this.leaveApplication = <LmsApplication>{};
          this.leaveType = this.leaveTypes[0];
          this.data.totalLeaveDurationInDays = 0;
        });
      });
    }

    private closeStatusSection() {
      this.showStatusSection = false;
      if (this.fromHistorySection)
        this.showHistorySection = true;
      else
        this.showApplicationSection = true;
    }

    private applyLeave() {
      console.log("**************");
      console.log("In apply leave method");
      let foundOccurance: boolean = false;
      this.findIfThereIsAnyOvalapping(foundOccurance).then((occuranceStatus: boolean) => {
        foundOccurance = occuranceStatus;
        if (this.leaveApplication.fromDate == null || this.leaveApplication.toDate == null || this.leaveApplication.reason == null) {
          this.notify.error("Please fill up all the fields");
        }
        else if (this.remainingLeavesMap[this.leaveType.id].daysLeft < this.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.leaveApplication.duration + " days of the leave type");
        }
        else if (foundOccurance) {
          this.notify.error("Date overlapping is not allowed! Please check your approved applications in  pending leaves or histories.");
        }
        else {
          this.convertToJson(Utils.LEAVE_APPLICATION_PENDING).then((json) => {
            this.leaveApplicationService.saveLeaveApplication(json).then((message) => {
              this.leaveApplication = <LmsApplication>{};
              this.leaveType = this.leaveTypes[0];
              this.getPendingApplications();
            });
          });
        }
      });

    }

    private findIfThereIsAnyOvalapping(foundOccurance: boolean): ng.IPromise<any> {
      let defer = this.$q.defer();
      this.leaveApplicationService.fetchApprovedLeavesWithDateRange(this.leaveApplication.fromDate, this.leaveApplication.toDate).then((applications: any) => {
        if (applications.length > 0)
          foundOccurance = true;
        else
          foundOccurance = false;

        defer.resolve(foundOccurance);
      });
      return defer.promise;
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      this.pagination.currentPage = currentPage;
      this.showStatusSection = true;
      this.showHistorySection = false;
      this.showApplicationSection = false;
      this.pendingApplication = pendingApplication;
      this.applicationStatusList = [];
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.applicationStatusList = statusList;
      });
    }

    private pageChanged(currentPage: number) {
      console.log("current page: " + currentPage);
      this.pagination.currentPage = currentPage;
      this.pageNumber = currentPage;
      if (this.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.getRemainingLeaves();
      }

    }


    /*private convertToBinary():ng.IPromise<any>{
     var binaryFiles: Array<any>=[];
     let defer = this.$q.defer();
     if (this.files.length > 0) {
     for (var i = 0; i < this.files.length; i++) {


     let binaryValue: any = {};
     let reader = new FileReader();
     reader.readAsDataURL(this.files[i]);

     reader.onload = () => {
     var dataUrl = reader.result;
     binaryValue = dataUrl;

     console.log(dataUrl);
     };


     }
     }
     }*/
    private convertToJson(appType: number): ng.IPromise<any> {
      let application: LmsApplication = this.leaveApplication;
      let defer = this.$q.defer();
      let completeJson = {};
      let jsonObject = [];
      console.log("Lms application");
      let item: any = {};
      item['id'] = application.id;
      item['employeeId'] = application.employeeId;
      item['typeId'] = this.leaveType.id;
      item['fromDate'] = application.fromDate;
      item['toDate'] = application.toDate;
      item['reason'] = application.reason;
      item['appStatus'] = appType;
      jsonObject.push(item);
      completeJson["entries"] = jsonObject;

      let jsonFileObject = [];
      if (this.files.length > 0) {
        for (var i = 0; i < this.files.length; i++) {
          let fileItem: any = {};
          let formData: FormData = new FormData();
          formData.append("uploadFile", this.files[i], this.files[i].name);

          fileItem['file'] = formData;

          fileItem['fileName'] = this.files[i].name;
          jsonFileObject.push(fileItem);
        }
      }
      completeJson["fileEntries"] = jsonFileObject;
      console.log("Complete json");
      console.log(completeJson);
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("LeaveApplicationManagement", LeaveApplicationManagement);
}