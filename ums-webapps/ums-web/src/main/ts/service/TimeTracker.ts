module ums{
  export class TimeTracker{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public reviewListLoaded():any{
      /*We need to make a service, which stores Dates of the tests and the time taken.*/
    }

  }

  UMS.service("timeTracker",TimeTracker);
}