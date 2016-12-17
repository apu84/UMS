module ums {

  export class UserGuide {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','$q'];
    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private $q:ng.IQService) {


      this.httpClient.get("userGuide", HttpClient.MIME_TYPE_JSON,
          (response: any) => {
            console.log( response.entries);
            $scope.guideList = response.entries;
          });
    }


  }
  UMS.controller("UserGuide", UserGuide);
}
