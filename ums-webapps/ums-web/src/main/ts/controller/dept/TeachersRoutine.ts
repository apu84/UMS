module ums{

  interface ITeachersRoutine extends ng.IScope{

    routines:Array<IRoutine>;
    getTeachersRoutine:Function;
    pdfFile:any;
    courseMap:any;
    roomMap:any;
  }

  class TeachersRoutine{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','classRoutineService','courseService','classRoomService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ITeachersRoutine,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,private classRoutineService:ClassRoutineService,
                private courseService:CourseService, private classRoomService:ClassRoomService) {

      $scope.routines=[];
      $scope.courseMap={};
      $scope.getTeachersRoutine = this.getTeachersRoutine.bind(this);

    }


    private getTeachersRoutine(){
      this.classRoutineService.getRoutineForTeacher().then((routines:Array<IRoutine>)=>{
        this.$scope.routines = routines;
      });
      this.createCourseMap();
      this.createRoomMap();
    }


    private createCourseMap(){
      this.courseService.getCourseOfTeacher().then((courses:Array<Course>)=>{
        console.log(courses);
        for(var i=0;i<courses.length;i++){
          this.$scope.courseMap[courses[i].id]=courses[i];
        }

        console.log("***course map****");
        console.log(this.$scope.courseMap);
      });
    }

    private createRoomMap(){
      this.classRoomService.getClassRooms().then((rooms:any)=>{
        console.log(rooms);
        for(var i=0;i<rooms.length;i++){
          this.$scope.roomMap[rooms[i].id]=rooms[i];
        }
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