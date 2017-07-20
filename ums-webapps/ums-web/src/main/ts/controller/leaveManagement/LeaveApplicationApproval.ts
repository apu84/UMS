/**
 * Created by My Pc on 22-May-17.
 */
module ums {


  interface IConstants {
    id: any;
    name: string;
  }

  class LeaveApplicationApproval {

    public leaveApprovalStatusList: Array<IConstants>;
    public leaveApprovalStatus: IConstants;
    public fileAttachments: Array<Attachment> = [];
    public itemsPerPage: number;
    public resultsPerPage: string;
    public totalItems: number;
    public pageNumber: number;
    public applicationStatusList: Array<LmsApplicationStatus>;
    public statusModal: LmsApplicationStatus;
    public pagination: any = {};
    public user: User;
    public backgroundColor: string;
    public disableApproveAndRejectButton: boolean;
    public additionalRoles: Array<AdditionalRolePermissions>;
    public pendingApplications: Array<LmsApplicationStatus>;
    public pendingApplication: LmsApplicationStatus;
    public remainingLeaves: Array<RemainingLmsLeave>;
    public approveOrRejectionComment: string;
    public data: any;
    public applicantsId: string;
    public deptOffices: Array<IConstants>;
    public deptOffice: IConstants;
    public approveButtonClicked: boolean;
    public rejectButtonClicked: boolean;
    public showHistorySection: boolean;
    public showApprovalSection: boolean;
    public showStatusSection: boolean;
    public fromHistory: boolean;
    public activeLeaveSection: boolean;
    public fromActiveLeaveSection: boolean;


    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'employeeService', 'additionalRolePermissionsService', 'userService', 'commonService', 'attachmentService'];

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
                private employeeService: EmployeeService,
                private additionalRolePermissionsService: AdditionalRolePermissionsService,
                private userService: UserService,
                private commonservice: CommonService, private attachmentService: AttachmentService) {

      this.resultsPerPage = "3";
      this.showApprovalSection = true;
      this.backgroundColor = "white";
      this.showHistorySection = false;
      this.showStatusSection = false;
      this.activeLeaveSection = false;
      this.fromActiveLeaveSection = false;
      this.fromHistory = false;
      this.pagination.currentPage = 1;
      this.itemsPerPage = +this.resultsPerPage;
      this.disableApproveAndRejectButton = true;
      this.approveOrRejectionComment = "";
      this.data = {};
      this.pageNumber = 1;
      this.leaveApprovalStatusList = [];
      this.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.leaveApprovalStatus = this.leaveApprovalStatusList[0];


      this.initializeDepartmentOffice = this.initializeDepartmentOffice.bind(this);
      //this.getLeaveApplications();
      this.getUsersInformation();
      this.getAdditionaPermissions();
    }

    private showActiveLeaveSection() {
      this.activeLeaveSection = true;
      this.showHistorySection = false;
      this.showStatusSection = false;
      this.showApprovalSection = false;
      this.itemsPerPage = 10;
      this.initializeDepartmentOffice().then((deptOffice: IConstants) => {
        this.fetchActiveLeaves(deptOffice);
      });
    }

    private fetchActiveLeaves(deptOffice: IConstants) {
      this.pagination.currentPage = 1;
      this.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsActiveOnTheDay(deptOffice.id, this.pagination.currentPage, this.itemsPerPage).then((apps) => {
        this.pendingApplications = apps.statusList;
        this.totalItems = apps.totalSize;
        console.log("active leaves");
        console.log(apps);
      });
    }

    private closeActiveLeaveSection() {
      this.activeLeaveSection = false;
      this.showHistorySection = false;
      this.showStatusSection = false;
      this.showApprovalSection = true;
      this.getLeaveApplications();
    }

    private downloadAttachment(file: Attachment) {
      this.attachmentService.downloadFile(file.id, file.fileName);
    }

    private initializeDepartmentOffice(deptOffice?: IConstants): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.deptOffices = [];
      this.deptOffices = this.appConstants.departmentOffice;
      if (deptOffice != null) {
        this.deptOffice = deptOffice;
        console.log("Dept office");
        console.log(this.deptOffice);
        this.fetchActiveLeaves(this.deptOffice);
      }
      else {
        for (var i = 0; i < this.deptOffices.length; i++) {
          if (this.deptOffices[i].id == Utils.DEPT_ALL)
            this.deptOffice = this.deptOffices[i];
        }
      }

      defer.resolve(this.deptOffice);
      return defer.promise;
    }

    private showHistory() {
      this.leaveApprovalStatusList = this.appConstants.leaveApprovalStatus;
      this.leaveApprovalStatus = this.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_ALL - 1];
      this.pageNumber = 1;
      this.itemsPerPage = 10;
      this.getAllLeaveApplicationsForHistory();
      this.showHistorySection = true;
      this.showStatusSection = false;
      this.showApprovalSection = false;
      this.backgroundColor = "#FAFAD2";

    }

    private closeHistory() {
      this.showHistorySection = false;
      this.showStatusSection = true;
      this.showApprovalSection = false;
      this.getUsersInformation();
      this.backgroundColor = "white";
    }


    private getAllLeaveApplicationsForHistory() {
      this.pendingApplications = [];
      if (this.pagination.currentPage == null)
        this.pagination.currentPage = 1;
      console.log(this.pagination.currentPage);
      this.leaveApplicationStatusService.fetchAllLeaveApplicationsOfEmployeeWithPagination(this.applicantsId, this.leaveApprovalStatus.id, this.pagination.currentPage, this.itemsPerPage).then((leaveApplications) => {
        this.pendingApplications = leaveApplications.statusList;
        this.totalItems = leaveApplications.totalSize;
        console.log("Histories...");
        console.log(this.pendingApplications);
      });
    }

    private approve() {
      this.approveButtonClicked = true;
    }

    private reject() {
      this.rejectButtonClicked = true;
    }


    private getAdditionaPermissions() {
      this.additionalRolePermissionsService.fetchLoggedUserAdditionalRolePermissions().then((additionalRolePermissions) => {
        this.additionalRoles = [];
        this.additionalRoles = additionalRolePermissions;

        console.log("permissions");
        console.log(additionalRolePermissions);
      });
    }

    private saveAction() {
      this.convertToJson().then((json) => {
        this.leaveApplicationStatusService.saveLeaveApplicationStatus(json).then((message) => {
          this.getRemainingLeaves(this.pendingApplication.applicantsId);
          this.disableApproveAndRejectButton = true;
          this.fetchApplicationStatus(this.pendingApplication, this.pagination.currentPage);
        });
      });
    }

    private getUsersInformation(): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.userService.fetchCurrentUserInfo().then((user) => {
        this.user = user;
        if (this.user.roleId == Utils.VC)
          this.leaveApprovalStatus = this.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_VC_APPROVAL - 1];
        else if (this.user.roleId == Utils.REGISTRAR)
          this.leaveApprovalStatus = this.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_REGISTRARS_APPROVAL - 1];
        else
          this.leaveApprovalStatus = this.leaveApprovalStatusList[Utils.LEAVE_APPLICATION_WAITING_FOR_HEADS_APPROVAL - 1];

        this.fetchLeaveApplications();
        defer.resolve(this.leaveApprovalStatus);

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
      this.statusModal = lmsApplicationStatus;
    }

    private statusChanged(leaveApplicationStatus: IConstants) {

      console.log("In the status change section");
      this.leaveApprovalStatus = leaveApplicationStatus;
      if (this.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.getLeaveApplications();
      }
    }


    private closeStatusSection() {
      this.showStatusSection = false;
      if (this.fromHistory == true && this.showHistorySection == true) {
        this.showHistory();
      }
      else if (this.fromActiveLeaveSection == true) {
        this.showActiveLeaveSection();
      }
      else {
        this.showApprovalSection = true;
        this.showHistorySection = false;
        this.getLeaveApplications();
      }


    }

    private setResultsPerPage(resultsPerPage: number) {
      if (resultsPerPage >= 1) {
        this.itemsPerPage = resultsPerPage;
        if (this.showHistorySection) {
          this.getAllLeaveApplicationsForHistory();
          console.log("In the history section");
        }
        else if (this.activeLeaveSection) {
          this.fetchActiveLeaves(this.deptOffice);
          console.log("In the active leave section");
        }
        else
          this.getLeaveApplications();
      }
    }


    private getRemainingLeaves(employeeId: string) {
      this.remainingLeaves = [];
      this.leaveApplicationService.fetchRemainingLeavesByEmployeeId(employeeId).then((remainingLeaves) => {
        this.remainingLeaves = remainingLeaves;
      });
    }


    private getLeaveApplications() {
      this.fetchLeaveApplications();
    }


    private fetchLeaveApplications() {
      if (this.pagination.currentPage == null)
        this.pagination.currentPage = 1;
      this.pendingApplications = [];
      this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.leaveApprovalStatus.id, this.pagination.currentPage, this.itemsPerPage).then((apps) => {
        this.pendingApplications = apps.statusList;
        this.totalItems = this.pendingApplications.length > 0 ? apps.totalSize : 0;
      });
    }

    private setCurrent(currentPage: number) {
      console.log("In set current");
      this.pagination.currentPage = currentPage;
      this.pendingApplications = [];
      if (this.showHistorySection) {
        this.getAllLeaveApplicationsForHistory();
      } else {
        this.leaveApplicationStatusService.fetchLeaveApplicationsWithPagination(this.leaveApprovalStatus.id, currentPage, this.itemsPerPage).then((apps) => {
          this.pendingApplications = apps.statusList;
          this.totalItems = apps.totalSize;
        });
      }
    }

    private fetchApplicationStatus(pendingApplication: LmsApplicationStatus, currentPage: number) {
      if (this.showHistorySection == true) {
        this.fromHistory = true;
      } else {
        this.fromHistory = false;
      }
      if (this.activeLeaveSection == true) {
        this.fromActiveLeaveSection = true;
      } else {
        this.fromActiveLeaveSection = false;
      }
      this.applicantsId = angular.copy(pendingApplication.applicantsId);
      this.pagination.currentPage = currentPage;
      this.showStatusSection = true;
      this.showApprovalSection = false;
      this.showHistorySection = false;
      this.activeLeaveSection = false;
      this.pendingApplication = pendingApplication;
      this.applicationStatusList = [];
      this.approveButtonClicked = false;
      this.rejectButtonClicked = false;

      this.getRemainingLeaves(pendingApplication.applicantsId);

      this.attachmentService.fetchAttachments(Utils.APPLICATION_TYPE_LEAVE.toString(), pendingApplication.appId).then((attachments) => {
        this.fileAttachments = [];
        this.fileAttachments = attachments;
      });

      console.log("disableApproveAndRejectButton:" + this.disableApproveAndRejectButton);
      this.leaveApplicationStatusService.fetchApplicationStatus(pendingApplication.appId).then((statusList: Array<LmsApplicationStatus>) => {
        this.applicationStatusList = statusList;
        this.decideWhetherToEnableOrDisableActionButtons(this.applicationStatusList[this.applicationStatusList.length - 1]);
      });
    }

    private decideWhetherToEnableOrDisableActionButtons(pendingApplication: ums.LmsApplicationStatus) {
      if (pendingApplication.actionStatus == Utils.LEAVE_APPLICATION_WAITING_FOR_HEADS_APPROVAL) {
        for (var i = 0; i < this.additionalRoles.length; i++) {
          if (this.additionalRoles[i].roleId == Utils.DEPT_HEAD || this.user.roleId == Utils.LIBRARIAN || this.user.roleId == Utils.COE) {
            this.disableApproveAndRejectButton = false;
            break;
          }
        }
      } else if (pendingApplication.actionStatus == Utils.LEAVE_APPLICATION_WAITING_FOR_REGISTRARS_APPROVAL) {
        if (this.user.roleId === Utils.REGISTRAR) {
          this.disableApproveAndRejectButton = false;
        }
      } else if (pendingApplication.actionStatus === Utils.LEAVE_APPLICATION_WAITING_FOR_VC_APPROVAL) {
        if (this.user.roleId === Utils.VC) {
          this.disableApproveAndRejectButton = false;
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
      item['appId'] = this.pendingApplication.appId;
      item['comments'] = this.data.comment;
      console.log("approved button clicked or not ***");
      console.log(this.approveButtonClicked);
      if (this.approveButtonClicked) {
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