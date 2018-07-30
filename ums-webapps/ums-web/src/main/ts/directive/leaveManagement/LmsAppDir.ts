/**
 * Created by My Pc on 09-May-17.
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
    public appId: string = "";

    public showStatusSection: boolean;
    public showHistorySection: boolean;
    public showApplicationSection: boolean;
    public fromPendingApplicationSection: boolean;
    public fromHistorySection: boolean;
    public showRemainingLeaves:boolean;


    public static $inject = ['appConstants', '$scope', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'userService', 'attachmentService'];

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
                private userService: UserService,
                private attachmentService: AttachmentService) {

      this.leaveApplication = <LmsApplication>{};
      this.showStatusSection = false;
      this.showHistorySection = false;
      this.fromHistorySection = false;
      this.fromPendingApplicationSection = true;
      this.showApplicationSection = true;
      this.showRemainingLeaves = false;
      this.data = {};
      this.data.totalLeaveDurationInDays = 0;
      this.pageNumber = 1;
      this.pagination = {};
      this.pagination.currentPage = 1;
      this.itemsPerPage = 50;

      $scope.fileInserted = this.fileInserted.bind(this);

      this.initializeDatePickers();
      this.getLeaveTypes();
      this.getPendingApplications();

      $("#leaveType").focus();
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


    private saveAttachments(id: string) {
      for (var i = 0; i < this.files.length; i++) {
        this.getFormData(this.files[i], id).then((formData) => {
          this.leaveApplicationService.uploadFile(formData);
        });

      }

      this.files = {};
    }

    private showHistory() {
      console.log("Showing file");
      console.log(this.files);


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
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.leaveApplicationService.user.employeeId, this.leaveApprovalStatus.id, this.pageNumber, this.itemsPerPage).then((leaveApplications) => {
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
      console.log(this.leaveApplication.fromDate);
      console.log(this.leaveApplication.toDate);
      let thisScope = this;
      setTimeout(function () {
        thisScope.getTotalDuration();
      }, 200);
    }

    private downloadAttachment(file: Attachment) {
      this.attachmentService.downloadFile(file.id, file.fileName);
    }

    private setStatusModalContent(lmsApplicationStatus: LmsApplicationStatus) {
      this.statusModal = lmsApplicationStatus;
    }


    private updateLeaveType(lmsType: LmsType) {
      this.leaveType = lmsType;
    }

    private getTotalDuration() {
      if (this.leaveApplication.toDate != null && this.leaveApplication.fromDate != null) {

        let timeDiff: any = Math.abs(this.leaveApplication.toDate.getTime() - this.leaveApplication.fromDate.getTime());
        let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
        this.leaveApplication.duration = diffDays + 1;
        if (this.leaveApplicationService.remainingLeavesMap[this.leaveType.id].daysLeftNumber < this.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.leaveApplication.duration + " days left for the leave type");
        }

        this.$scope.$apply();

      }


    }

    private getPendingApplications() {
      this.pendingApplications = [];

      this.leaveApplicationStatusService.fetchPendingLeaves(this.leaveApplicationService.employeeId).then((pendingLeaves) => {
        this.pendingApplications = pendingLeaves;
        this.totalItems = pendingLeaves.length;
        //this.leaveApplicationService.employeeId = angular.copy(this.pendingApplications[0].applicantsId);
      });
    }



    private save() {
      this.showRemainingLeaves = false;
      this.convertToJson(Utils.LEAVE_APPLICATION_SAVED).then((json) => {
        this.leaveApplicationService.saveLeaveApplication(json).then((message) => {

          if (message.message == null) {
            this.leaveApplication = <LmsApplication>{};
            this.leaveType = this.leaveTypes[0];
            this.data.totalLeaveDurationInDays = 0;
          }

          this.showRemainingLeaves = true;
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
        else if (this.leaveApplicationService.remainingLeavesMap[this.leaveType.id].daysLeftNumber < this.leaveApplication.duration) {
          this.notify.error("Please select proper duration, you don't have " + this.leaveApplication.duration + " days of the leave type");
        }
        else if (foundOccurance) {
          this.notify.error("Date overlapping is not allowed! Please check your approved applications in  pending leaves or histories.");
        }
        else {
          this.convertToJson(Utils.LEAVE_APPLICATION_PENDING).then((json) => {
            console.log("Jsonsss....");
            console.log(json);
            this.leaveApplicationService.saveLeaveApplication(json).then((message) => {

              console.log("********");
              console.log(message);
              if (message[0].message == "") {
                this.appId = message[0].id;
                this.saveAttachments(message[0].id);
                this.leaveApplication = <LmsApplication>{};
                this.leaveType = this.leaveTypes[0];
                this.getPendingApplications();
              } else {
                this.leaveApplication = <LmsApplication>{};
              }

            });
          });
        }
      });

    }

    private findIfThereIsAnyOvalapping(foundOccurance: boolean): ng.IPromise<any> {
      let defer = this.$q.defer();
      let fromDate:string = moment(this.leaveApplication.fromDate).format("DD-MM-YYYY");
      let toDate: string = moment(this.leaveApplication.toDate).format("DD-MM-YYYY");
      this.leaveApplicationService.fetchApprovedLeavesWithDateRange(fromDate, toDate).then((applications: any) => {
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
      this.showRemainingLeaves=false;

      this.attachmentService.fetchAttachments(Utils.APPLICATION_TYPE_LEAVE.toString(), pendingApplication.appId).then((attachments) => {
        this.fileAttachments = [];
        this.fileAttachments = attachments;
      });

      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.applicationStatusList = statusList;
        this.showRemainingLeaves = true;
      });
    }

    private pageChanged(currentPage: number) {
      console.log("current page: " + currentPage);
      this.pagination.currentPage = currentPage;
      this.pageNumber = currentPage;
      if (this.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } /*else {
        this.getRemainingLeaves();
      }*/

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

    private getFormData(file, id): ng.IPromise<any> {
      var formData = new FormData();
      formData.append('files', file);
      console.log(this.files[0].name);
      formData.append('name', file.name);
      formData.append("id", id);
      console.log(formData);
      var defer = this.$q.defer();
      defer.resolve(formData);
      return defer.promise;

    }

    public getStatusLabel(lmsAppStatus: LmsApplicationStatus): string {
      if (lmsAppStatus.actionStatus == 1)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-default\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else if (lmsAppStatus.actionStatus == 2)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-primary\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else if (lmsAppStatus.actionStatus == 3)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-danger\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else if (lmsAppStatus.actionStatus == 4)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-info\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else if (lmsAppStatus.actionStatus == 5)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-danger\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else if (lmsAppStatus.actionStatus = 6)
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i><span class=\"label label-danger\">" + lmsAppStatus.actionStatusLabel + "</span></i>";
      else
        return "By " + lmsAppStatus.actionTakenByName + " on " + lmsAppStatus.actionTakenOn + " <i> <span class=\"label label-success\">" + lmsAppStatus.actionStatusLabel + "</span></i>";

    }

    private convertToJson(appType: number): ng.IPromise<any> {
      let application: LmsApplication = this.leaveApplication;
      let defer = this.$q.defer();
      let completeJson = {};
      let jsonObject = [];
      console.log("Lms application");
      console.log(this.leaveApplicationService.employeeId);
      let item: any = {};
      item['id'] = application.id;
      item['employeeId'] = application.employeeId;
      item['typeId'] = this.leaveType.id;
      let momentFromDate = moment(application.fromDate);
      let momentToDate = moment(application.toDate);
      item['fromDate'] = moment(application.fromDate).format("DD-MM-YYYY");
      item['toDate'] = moment(application.toDate).format("DD-MM-YYYY");
      item['reason'] = application.reason;
      item['appStatus'] = appType;
      item['employeeId'] = this.leaveApplicationService.employeeId;
      jsonObject.push(item);
      completeJson["entries"] = jsonObject;

      defer.resolve(completeJson);
      return defer.promise;
    }


  }

  class LmsAppDir implements ng.IDirective {

    constructor() {

    }

    public restrict: string = "A";
    public scope = {
    };

    public controller = LeaveApplicationManagement;
    public controllerAs = 'vm';

    public link = (scope: any, element: any, attributes: any) => {
      console.log("In the directive");
    };

    public templateUrl: string = "./views/directive/leave-application.html"; //ums-webapps/ums-web/src/main/webapp/iums/views/leave-management/leave-application-management.html
  }

  UMS.directive("lmsAppDir", [() => {
    return new LmsAppDir();
  }])
}