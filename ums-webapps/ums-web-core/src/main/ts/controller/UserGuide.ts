module ums {

  export class UserGuide {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','$q'];
    constructor(private $scope: IAbc, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private $q:ng.IQService) {
    }
  }
  UMS.controller("UserGuide", UserGuide);
}
