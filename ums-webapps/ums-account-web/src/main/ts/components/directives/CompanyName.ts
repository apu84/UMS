module ums {
  interface CompanyNameScope extends ng.IScope {
    companyName : any;
  }
  export class CompanyName implements ng.IDirective {

    constructor(private httpClient: HttpClient,
                private $compile: ng.ICompileService,
                private $timeout: ng.ITimeoutService) {

    }

    public restrict = 'AE';

    public scope = true;

    public link = ($scope:any, element:JQuery, attributes:any) => {
      this.httpClient.get("account/user-company-map/company", HttpClient.MIME_TYPE_JSON, (response) => {
        console.log(response);
        $scope.companyName = response.company;
      });

    };
    public templateUrl = "./views/directive/company-name.html";
  }


  UMS.directive("companyName", ['HttpClient', '$compile', '$timeout', (httpClient, $compile, $timeout) => new CompanyName(httpClient, $compile, $timeout)]);

}