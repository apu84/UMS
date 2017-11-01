module ums {


  interface IOptions {
    id: number;
    name: string;
  }


  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService', 'FeeCategoryService', 'userService', 'appConstants', '$q'];
    public certificateOptions: IOptions[];
    public certificateOption: IOptions;
    public feeCategories: FeeCategory[];
    public feeCategory: FeeCategory;
    public certificateStatus: CertificateStatus;
    public certificateStatusList: CertificateStatus[];
    public user: LoggedInUser;
    public currentPage: number = 1;
    public totalItems: number = 0;
    public feeType: number;

    constructor(private certificateFeeService: CertificateFeeService,
                private certificateStatusService: CertificateStatusService,
                private feeCategoryService: FeeCategoryService,
                private userService: UserService,
                private appConstants: any,
                private $q: ng.IQService) {

      this.certificateOptions = appConstants.certificateStatus;
      this.certificateOption = appConstants.certificateStatus[0];
      this.getLoggedUserAndFeeCategories();
    }


    private getLoggedUserAndFeeCategories() {
      this.userService.fetchCurrentUserInfo().then((user: LoggedInUser) => {
        this.user = user;
        this.getFeeCategories().then((feeType: number) => {
          this.certificateStatusService.getCertificateStatusByStatusAndType(this.certificateOption.id, feeType).then((certificates: CertificateStatus[]) => {
            this.totalItems = certificates.length;
          });

          this.certificateStatusService.getPaginatedCertificateStatusByStatusAndType(this.certificateOption.id, feeType, 1, 2).then((certificates: CertificateStatus[]) => {
            this.certificateStatusList = certificates;
          });


        });
      });
    }

    private pageChanged(pageNumber: number) {
      this.currentPage = pageNumber;


      this.certificateStatusService.getCertificateStatusByStatusAndType(this.certificateOption.id, this.feeType).then((certificates: CertificateStatus[]) => {
        this.totalItems = certificates.length;
      });

      this.certificateStatusService.getPaginatedCertificateStatusByStatusAndType(this.certificateOption.id, this.feeType, 1, 2).then((certificates: CertificateStatus[]) => {
        this.certificateStatusList = [];
        this.certificateStatusList = certificates;
      });

    }


    private getFeeCategories(): ng.IPromise<number> {
      var feeType: number = 0;
      let defer: ng.IDeferred<number> = this.$q.defer();
      if (this.user.departmentId == Utils.DEPT_COE)
        feeType = Utils.CERTIFICATE_FEE;

      defer.resolve(feeType);

      this.feeType = feeType;
      return defer.promise;
    }

    private getCertificateStatus() {

      this.certificateStatusService.getFilters().then((filters: Filter[]) => {
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