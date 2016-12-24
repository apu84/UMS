module ums {

  export class MailCompose {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','$q'];
    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private $q:ng.IQService) {

      console.log("Main MailCompose");

      $('.wysihtml5').wysihtml5();
    }

    // For downloadUserGuide check in AppController.ts File


  }
  UMS.controller("MailCompose", MailCompose);
}
