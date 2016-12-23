module ums {

  export class MailView {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','$q'];
    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private $q:ng.IQService) {

      console.log("MailView");
    }

    // For downloadUserGuide check in AppController.ts File


  }
  UMS.controller("MailView", MailView);
}
