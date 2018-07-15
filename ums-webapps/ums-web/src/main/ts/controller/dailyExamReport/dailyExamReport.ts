module  ums{
    class DailyExamReport{
        private state: any;
        private stateParams: any;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService','$state', '$stateParams'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private $state : any,
                    private $stateParams: any){
            this.state = $state;
            this.stateParams = $stateParams;
        }

        private doSomething():void{
            this.state.current.url === '/dailyExamAttendanceReport';
            this.state.go('dailyExamReport.dailyExamAttendanceReport');
            alert('Hello From Another Side');
        }

    }
    UMS.controller("DailyExamReport",DailyExamReport);
}