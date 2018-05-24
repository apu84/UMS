module ums {
    
  export class ClassRoutine  {

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService','$timeout'];
    constructor(private appConstants: any, private httpClient: HttpClient, 
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private courseService:CourseService, private classRoomService:ClassRoomService, private classRoutineService:ClassRoutineService,private $timeout : ng.ITimeoutService) {
        

    }


  }

  UMS.controller("ClassRoutine", ClassRoutine);
}