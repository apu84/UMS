module ums {


  interface IOptions {
    id: number;
    name: string;
  }


  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService', 'CertificateService', 'FeeCategoryService', 'userService', 'appConstants', '$q', '$scope', 'notify', 'additionalRolePermissionsService'];
    public certificateOptions: IOptions[];
    public certificateOption: IOptions;
    public selectedFilters: SelectedFilter[] = [];
    public filters: Filter[];
    public reloadReference: ReloadRef = {reloadList: false};
    public feeCategories: FeeCategory[];
    public feeCategory: FeeCategory;
    public certificateStatus: CertificateStatus;
    public certificateStatusList: CertificateStatus[];
    public changedCertificateStatusList: CertificateStatus[];
    public user: LoggedInUser;
    public currentPage: number = 1;
    public totalItems: number = 0;
    public itemsPerPage: number = 10;
    public feeType: number;
    public enableButton: boolean = false;
    public userDeptHead: boolean = false;

    constructor(private certificateFeeService: CertificateFeeService,
                private certificateStatusService: CertificateStatusService,
                private certificateService: CertificateService,
                private feeCategoryService: FeeCategoryService,
                private userService: UserService,
                private appConstants: any,
                private $q: ng.IQService,
                private $scope: ng.IScope,
                private notify: Notify,
                private additionalRolePermissionsService: AdditionalRolePermissionsService) {

      this.certificateOptions = appConstants.certificateStatus;
      this.certificateOption = appConstants.certificateStatus[0];
      this.changedCertificateStatusList = [];
      this.getLoggedUserAndFeeCategories();

      this.certificateStatusService.getFilters().then((filters: Filter[]) => {
        this.filters = filters;

        $scope.$watch(() => this.selectedFilters, () => {
          this.navigate();
        }, true);
      });

    }


    private getLoggedUserAndFeeCategories() {
      this.userService.fetchCurrentUserInfo().then((user: LoggedInUser) => {
        this.user = user;
        console.log("Logged user");
        console.log(this.user);
        this.getFeeCategories();
      });
    }

    private navigate() {

      if (this.selectedFilters.length > 0) {
        this.fetchTotalItemsAndCertificateList();
      }

    }

    private pageChanged(pagenumber: number) {

      this.currentPage = pagenumber;
      this.fetchTotalItemsAndCertificateList();

    }

    private fetchTotalItemsAndCertificateList() {
      if (this.userDeptHead == false) {
        this.certificateStatusService.listCertificateStatus(this.selectedFilters, 'certificate-status/paginated', this.feeType).then((response: any) => {
          this.totalItems = response.entries.length;
        });

        this.certificateStatusService.listCertificateStatus(this.selectedFilters, 'certificate-status/paginated', this.feeType, this.currentPage, this.itemsPerPage).then((response: any) => {
          this.certificateStatusList = response.entries;
        });
      } else {
        this.certificateStatusService.listCertificateStatus(this.selectedFilters, 'certificate-status/paginated', this.feeType, 0, 0, this.user.departmentId).then((response: any) => {
          this.totalItems = response.entries.length;
        });

        this.certificateStatusService.listCertificateStatus(this.selectedFilters, 'certificate-status/paginated', this.feeType, this.currentPage, this.itemsPerPage, this.user.departmentId).then((response: any) => {
          this.certificateStatusList = response.entries;
        });
      }

    }

    private setPage(pageNo: number) {
      this.currentPage = pageNo;
    }


    private getFeeCategories(): ng.IPromise<number> {
      var feeType: number = 0;
      let defer: ng.IDeferred<number> = this.$q.defer();

      if (this.user.departmentId == Utils.DEPT_COE) {
        feeType = Utils.CERTIFICATE_FEE;
      }
      else if (this.user.departmentId == Utils.DEPT_RO) {
        feeType = Utils.REG_CERTIFICATE_FEE;
      }
      else if (this.user.departmentId == Utils.DEPT_PO) {
        feeType = Utils.PROC_CERTIFICATE_FEE;
      }
      else {
        console.log("In the additional role section");
        this.getAdditionalRolePermissions().then((result: boolean) => {
          console.log("result");
          console.log(result);
          if (result) {
            feeType = Utils.REG_CERTIFICATE_FEE;
            console.log("Reg fee type: " + feeType);
          }
          else {
            feeType = Utils.DEPT_CERTIFICATE_FEE;
          }
          defer.resolve(feeType);

          this.feeType = feeType;
          console.log("fee type");
          console.log(feeType);
          return defer.promise;

        });
      }

      defer.resolve(feeType);

      this.feeType = feeType;
      console.log("fee type");
      console.log(feeType);
      return defer.promise;
    }

    private getAdditionalRolePermissions(): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      var result: boolean = false;
      this.additionalRolePermissionsService.fetchLoggedUserAdditionalRolePermissions().then((additionalRolePermissions: AdditionalRolePermissions[]) => {
        if (additionalRolePermissions.length >= 1)
          result = true;
        defer.resolve(result);
        this.userDeptHead = result;
      });

      return defer.promise;
    }

    private createCertificateReport(certificateStatus: CertificateStatus) {
      this.certificateService.getCertificateReport(certificateStatus.feeCategory, certificateStatus.studentId, certificateStatus.semesterId);
    }


    private statusChanged(certificateStatus: CertificateStatus) {
      this.enableButton = true;
      this.changedCertificateStatusList.push(certificateStatus);
    }

    private save() {
      this.certificateStatusService.processCertificates(this.changedCertificateStatusList).then((processed: boolean) => {
        if (processed) {
          this.changedCertificateStatusList = [];
          this.notify.success("Successfully Saved");
        } else {
          this.notify.error("Error in updating data");
        }
      });
    }


    private getCertificateStatus() {

      this.certificateStatusService.getFilters().then((filters: Filter[]) => {
      })

    }

  }

  UMS.controller("CertificateApprovalController", CertificateApprovalController);
}