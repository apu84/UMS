module ums {
  export class CompanyController {
    public static $inject = ['$scope', '$modal', '$timeout', 'companyService', 'accountConstants', '$q'];

    private company: ICompany;
    private companyList: ICompany[];
    private companyListDisplay: ICompany[];
    private modalHeader: string;

    constructor(private $scope: ng.IScope,
                private $modal: any,
                private $timeout: ng.ITimeoutService,
                private companyService: CompanyService,
                private accountConstants: any,
                private $q: ng.IQService) {

      this.initialize();
    }

    public initialize() {
      this.getExistingCompanyList();
    }

    public getExistingCompanyList() {
      this.companyService.getAllCompany().then((companyList: ICompany[]) => {
        this.companyList = [];
        this.companyList = companyList;
        this.companyListDisplay = angular.copy(companyList);
      })
    }

    public addModalClicked() {
      this.company = <ICompany>{};
      this.modalHeader = "Create New Company";
    }

    public editButtonClicked(company: ICompany) {
      // $("#addModal").modal('toggle');
      console.log("company for update");
      console.log(company);
      this.company = company;
      this.modalHeader = "Update Company";
    }

    public save() {
      if (this.company.id == null) {
        this.companyService.saveCompany(this.company).then((company: ICompany) => {
          this.company = <ICompany>{};
          this.companyList.push(company);
        })
      } else {
        this.companyService.updateCompany(this.company).then((company: ICompany) => {
          this.company = company;
        })
        $("#addModal").modal('close');
      }
    }
  }

  UMS.controller("CompanyController", CompanyController);
}