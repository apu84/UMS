module ums {


  interface IOptions {
    id: number;
    name: string;
  }


  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService', 'userService', 'appConstants'];
    public certificateOptions: IOptions[];
    public certificateOption: IOptions;
    public feeCategories: FeeCategory[];
    public feeCategory: FeeCategory;
    public user: LoggedInUser;

    constructor(private certificateFeeService: CertificateFeeService,
                private certificateStatusService: CertificateStatusService,
                private feeCategoryService: FeeCategoryService,
                private userService: UserService,
                private appConstants: any) {

      this.certificateOptions = appConstants.certificateStatus;
      this.certificateOption = appConstants.certificateStatus[0];
      this.getLoggedUserAndFeeCategories();
    }

    private getLoggedUserAndFeeCategories() {
      this.userService.fetchCurrentUserInfo().then((user: LoggedInUser) => {
        this.user = user;
        this.getFeeCategories();
      });
    }


    private getFeeCategories() {
      var feeType: number = 0;
      if (this.user.departmentId == Utils.DEPT_COE)
        feeType = Utils.CERTIFICATE_FEE;

      this.feeCategoryService.getFeeCategories(feeType).then((feeCategories: FeeCategory[]) => {
        console.log(feeCategories);
      });
    }

    private getCertificateStatus() {
      /*var filters: SelectedFilter[];
      var filter: SelectedFilter = <SelectedFilter>{};
      filter.id = 1;
      var filterOption = <FilterOption>{};
      filterOption.label = "";*/
    }

  }

  UMS.controller("CertificateApprovalController", CertificateApprovalController);
}