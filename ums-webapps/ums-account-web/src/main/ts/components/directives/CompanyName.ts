module ums {
  interface CompanyNameScope extends ng.IScope {
    companyName: any;
  }

  export class CompanyName implements ng.IDirective {

    constructor(private httpClient: HttpClient,
                private $compile: ng.ICompileService,
                private $timeout: ng.ITimeoutService,
                private $state: any, private userCompanyMapService: UserCompanyMapService) {

    }

    public restrict = 'AE';

    public scope = true;

    public link = ($scope: any, element: JQuery, attributes: any) => {
      this.httpClient.get("account/user-company-map/company", HttpClient.MIME_TYPE_JSON, (response) => {
        console.log('#########');
        console.log(response);
        this.userCompanyMapService.companyName = response.company;
        if (response.company == '')
          this.$state.go('userHome');
        else {
          $scope.companyName = response.company;
        }

        console.log("company name");
        console.log(this.userCompanyMapService.companyName);
      });

    };
    public templateUrl = "./views/directive/company-name.html";
  }


  UMS.directive("companyName", ['HttpClient', '$compile', '$timeout', '$state', 'UserCompanyMapService', (httpClient, $compile, $timeout, $state, userCompanyMapService) => new CompanyName(httpClient, $compile, $timeout, $state, userCompanyMapService)]);

}