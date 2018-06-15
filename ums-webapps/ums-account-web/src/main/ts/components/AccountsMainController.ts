module ums {
  export class AccountsMainController {
    public static $inject = ['$scope', 'HttpClient'];

    constructor(private $scope:any,
                private httpClient:HttpClient) {

      httpClient.get("userHome", HttpClient.MIME_TYPE_JSON, (response) => {
        $scope.userHome = response.infoList;
        $scope.userRole = response.userRole;
        if(response.userRole!="Teacher") {
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
