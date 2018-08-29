module ums{
    class OptCourseOffer{
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','examRoutineService','classRoomService','employeeService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private examRoutineService: ExamRoutineService,
                    private classRoomService:ClassRoomService,
                    private employeeService:EmployeeService){

        }
        private doSomething():void{
            alert('hello from another side!!');
        }
    }
    UMS.controller("OptCourseOffer",OptCourseOffer);
}