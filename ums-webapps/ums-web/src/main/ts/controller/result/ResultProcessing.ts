module ums {
  export class ResultProcessing {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient'];

    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient) {

      $scope.showGif = true;
      $scope.showAllDept=false;


      this.$scope.fetchStatusInfo = this.fetchStatusInfo.bind(this);
      this.$scope.showYearSemesterWise=this.showYearSemesterWise.bind(this);
      this.$scope.showDefault=this.showDefault.bind(this);
    }

    private fetchStatusInfo(): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.showGif = false;
      this.$scope.showAllDept=true;

    }

    private showYearSemesterWise(deptId:String):void{
      $("#"+deptId+"_view1").hide('slide', { direction: 'right', easing: 'easeOutBounce' },400);

      if($("#"+deptId+"_download")){
        $("#"+deptId+"_download").hide('slide', { direction: 'right', easing: 'easeOutBounce' },200);
      }

      if($("#"+deptId+"_publish")){
        $("#"+deptId+"_publish").hide('slide', { direction: 'right', easing: 'easeOutBounce' },200);
      }
      setTimeout(function(){
        $("#"+deptId+"_view2").fadeIn(200);
      }, 400);

    }
private showDefault(deptId:String):void{
  $("#"+deptId+"_view2").hide('slide', { direction: 'right', easing: 'easeOutBounce' },200);

  setTimeout(function(){
    $("#"+deptId+"_view1").fadeIn(200);
    if($("#"+deptId+"_download")) {
      $("#" + deptId + "_download").fadeIn(100);
    }
    $("#"+deptId+"").fadeIn(100);
    if($("#"+deptId+"_publish")){
      $("#"+deptId+"_publish").fadeIn(100);
    }
  }, 200);

}

  }

  UMS.controller("ResultProcessing", ResultProcessing);
}