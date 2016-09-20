module ums{
  export class TimeLogging implements ng.IDirective{
    public static $inject=['$compile','$timeout','$log','timeTracker'];
    constructor(private $compile:ng.ICompileService,
                private $timeout:ng.ITimeoutService,
                private $log:ng.ILogService,
                private timeTracker:TimeTracker
                ){

    }

    public link=(scope:any,element:any,attrs:any)=>{
      if(scope.$last){
          this.$timeout(()=>{
              var timeFinishedLoadingList = this.timeTracker.reviewListLoaded();
              var ref:any = new Date(timeFinishedLoadingList);
            var end:any = new Date();
            this.$log.debug("## DOM rendering list took: " + (end - ref) + " ms");
          });
      }
    };
  }

  UMS.directive("timeLogging",[($compile,$timeout,$log,timeTracker)=>new TimeLogging($compile,$timeout,$log,timeTracker)]);
}

/*
How to use.
* <tr ng-repeat="item in items" post-repeat-directive>…</tr>*/