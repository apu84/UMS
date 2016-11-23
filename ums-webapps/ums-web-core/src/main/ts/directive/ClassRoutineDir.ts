/**
 * Created by My Pc on 22-Nov-16.
 */

module ums{
  interface IClassRoutineDir extends ng.IScope{
    routines:Array<IRoutine>;
    classRoomMapWithRoomId:any;
    createClassRoomMapWithRoomId:Function;
    createCourseMapWithCourseId:Function;
  }

  class ClassRoutineDirController{
    public static $inject=['$scope','classRoomService','courseService'];
    constructor(private $scope:IClassRoutineDir,
                private classRoomService:ClassRoomService,
                private courseService:CourseService){
      $scope.routines=[];
      $scope.classRoomMapWithRoomId={};
      $scope.createClassRoomMapWithRoomId = this.createClassRoomMapWithRoomId.bind(this);
    }

    private createClassRoomMapWithRoomId(){
      this.classRoomService.getClassRooms().then((rooms:Array<ClassRoom>)=>{
        for(var i=0;i<rooms.length;i++){
          this.$scope.classRoomMapWithRoomId[rooms[i].roomId]=rooms[i];
        }
      });
    }

    private createCourseIdMapWithCourseId(){

    }
  }

  class ClassRoutineDir implements ng.IDirective{
   constructor(){

   }

    public restrict:string="A";
    public scope={
      routines:'=routines'
    };

    public controller=ClassRoutineDirController;



    public link = (scope:any, element:any, attributes:any)=>{

      attributes.routines=scope.routines;
      console.log(attributes.routines);
    };


    public templateUrl:string="./views/directive/class-routine-dir.html";
  }

  UMS.directive("classRoutineDir",[()=>{
    return new ClassRoutineDir();
  }]);
}