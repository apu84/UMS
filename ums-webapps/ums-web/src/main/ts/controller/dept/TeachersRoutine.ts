module ums{

  interface ITeachersRoutine extends ng.IScope{

    routines:Array<IRoutine>;
    getTeachersRoutine:Function;
    pdfFile:any;
    courseMap:any;
    roomMap:any;
    showRoutine:boolean;
    showRoutineDirective:boolean;
    showRoutineReport:Function;
    getRoutines:Function;

  }

  class TeachersRoutine{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','classRoutineService','courseService','classRoomService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ITeachersRoutine,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,private classRoutineService:ClassRoutineService,
                private courseService:CourseService, private classRoomService:ClassRoomService) {

      $scope.routines=[];
      $scope.courseMap={};
      $scope.showRoutineDirective=false;
      $scope.getTeachersRoutine = this.getTeachersRoutine.bind(this);
      $scope.showRoutineReport = this.showRoutineReport.bind(this);
      $scope.getRoutines = this.getRoutines.bind(this);

    }


    private getRoutines(){
      this.createCourseMap();
    }


    private getTeachersRoutine():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.classRoutineService.getRoutineForTeacher().then((routines:Array<IRoutine>)=>{
        this.$scope.routines = routines;



      });
      defer.resolve("success");
      return defer.promise;
    }


    private createCourseMap(){
      this.courseService.getCourseOfTeacher().then((courses:Array<Course>)=>{
        for(var i=0;i<courses.length;i++){
          this.$scope.courseMap[courses[i].id]=courses[i];
        }



        this.createRoomMap();
      });
    }

    private createRoomMap(){
      this.$scope.roomMap={};
      this.classRoomService.getClassRooms().then((rooms:any)=>{
        for(var i=0;i<rooms.length;i++){
          this.$scope.roomMap[rooms[i].id]=rooms[i];
        }

        this.getTeachersRoutine().then((success:string)=>{
          if(success=='success'){
            this.$scope.showRoutineDirective=true;
          }
        });
      });
    }



    private showRoutineReport(){
      this.$scope.pdfFile;
      this.classRoutineService.getClassRoutineReportForTeacher().then((file:any)=>{
        if(file!="failure"){
          this.$scope.pdfFile=file;
        }else{
          this.notify.error("Error in generating routine report");
        }
      });
    }

  }

  UMS.controller("TeachersRoutine",TeachersRoutine);

}