module ums {
  export class AccountsMainController {
    public static $inject = ['$scope', 'HttpClient', 'UserCompanyMapService'];

    constructor(private $scope:any,
                private httpClient: HttpClient, private userCompanyMapService: UserCompanyMapService) {

      httpClient.get("userHome", HttpClient.MIME_TYPE_JSON, (response) => {
        $scope.userHome = response.infoList;
        $scope.userRole = response.userRole;
        if(response.userRole=="student") {
          if(document.getElementById("empProfile"))
            document.getElementById("empProfile").style.display="none";
        }
      });

      httpClient.get("account/user-company-map/user", HttpClient.MIME_TYPE_JSON, (response) => {
        $scope.userCompanies = response;
        console.log($scope.userCompanies);
        $('#modalCompanySelection').modal('show');
      });

      $scope.setCompany = this.setCompany.bind(this);
    }

    public setCompany(): any {
      $("#companyDiv").html("<b>Company</b> : "+$("#selectedCompany  option:selected").text());
      this.httpClient.post('account/user-company-map/company/'+$("#selectedCompany").val(), {},
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => {
            console.log("$$$$$$");
            console.log($("#selectedCompany  option:selected").text());
            this.userCompanyMapService.companyName = $("#selectedCompany  option:selected").text();
            console.log(response);
            $('#modalCompanySelection').modal('hide');
          })
          .error((error) => {
            console.log(error);
          });
    }

  }
  UMS.controller('AccountsMainController', AccountsMainController);
}
