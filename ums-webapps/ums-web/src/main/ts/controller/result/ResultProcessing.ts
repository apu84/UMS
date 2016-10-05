module ums {
  export class ResultProcessing {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient'];

    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient) {

      $scope.showGif = true;
      $scope.showAllDept=false;


      this.$scope.fetchStatusInfo = this.fetchStatusInfo.bind(this);
    }

    private fetchStatusInfo(): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.showGif = false;
      this.$scope.showAllDept=true;

    }


  }

  UMS.controller("ResultProcessing", ResultProcessing);
}