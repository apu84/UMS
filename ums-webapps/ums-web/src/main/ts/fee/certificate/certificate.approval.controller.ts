module ums {


  interface IOptions {
    id: number;
    name: string;
  }


  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService','FeeCategoryService', 'userService', 'appConstants'];
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
        this.feeCategories = feeCategories;
        this.getCertificateStatus();
      });
    }

    private getCertificateStatus() {

        this.certificateStatusService.getFilters().then((filters:Filter[])=>{
            console.log("Filters");
            console.log(filters);
        })
      /*var filters: SelectedFilter[] = [];
      var filter: SelectedFilter = <SelectedFilter>{};
      filter.id = 1;
      var filterOption = <FilterOption>{};
      filterOption.label = "type";
      filterOption.value="string";
      filter.filter=filterOption;

      var valueOption = <FilterOption>{};
      valueOption.label="fee_id";
      valueOption.value="14";
      filter.value=valueOption;
      filters.push(filter);

      this.certificateStatusService.listCertificateStatus(filters).then((certificateStatus:any)=>{
         console.log("certificate status");
         console.log(certificateStatus);
      });*/
    }

  }

  UMS.controller("CertificateApprovalController", CertificateApprovalController);
}