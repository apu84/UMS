module ums{
    class AbsentLateComingInfo{
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService','DailyExamAttendanceReportService','examRoutineService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private dailyExamAttendanceReportService: DailyExamAttendanceReportService,
                    private examRoutineService: ExamRoutineService) {
        }
        private doSomething():void{
            alert('hello from another side');
        }

    }
    UMS.controller("AbsentLateComingInfo",AbsentLateComingInfo);
}