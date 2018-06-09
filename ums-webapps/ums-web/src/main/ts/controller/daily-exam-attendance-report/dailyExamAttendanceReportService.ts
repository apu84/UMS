module ums{
    class DailyExamAttendanceReportService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }

    }
    UMS.service("DailyExamAttendanceReportService",DailyExamAttendanceReportService);
}