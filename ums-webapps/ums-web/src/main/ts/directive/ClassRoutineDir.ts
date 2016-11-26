/**
 * Created by My Pc on 22-Nov-16.
 */

module ums{
  interface IClassRoutineDir extends ng.IScope{
    routines:Array<IRoutine>;
    classRoomMapWithRoomId:any;
    weekday:any;
    courseMap:any;
    roomMap:any;
    currentDate:string;

    updateDate:Function;
  }

  class ClassRoutineDirController{
    public static $inject=['$scope','classRoomService','courseService','appConstants'];
    constructor(private $scope:IClassRoutineDir,
                private classRoomService:ClassRoomService,
                private courseService:CourseService,
                private  appConstants:any){
      $scope.routines=[];
      $scope.courseMap={};
      $scope.roomMap={};
      $scope.weekday=appConstants.weekday;
      $scope.updateDate=this.updateDate.bind(this);

      this.getCurrentDate();

    }

    private getCurrentDate():void{
      var d= new Date();
      var date = d.getDay();
      if((date+2)==8){
        date=1;
      }else if((date+2)==9){
        date=2;
      }else{
        date=date+2;
      }


      console.log("date");
      console.log(date);
      this.$scope.currentDate=String(date);
    }


    private updateDate(day:number):void{
      console.log("In the update date");
      this.$scope.currentDate=String(day);
      console.log(this.$scope.currentDate);
    }

  }

  class ClassRoutineDir implements ng.IDirective{
   constructor(){

   }

    public restrict:string="A";
    public scope={
      routines:'=routines',
      courseMap:'=courseMap',
      roomMap:'=roomMap'
    };

    public controller=ClassRoutineDirController;



    public link = (scope:any, element:any, attributes:any)=>{
      for(var i=0;i<scope.routines.length;i++){
        scope.routines[i].day=String(scope.routines[i].day);
      }
    };


    public templateUrl:string="./views/directive/class-routine-dir.html";
  }

  UMS.directive("classRoutineDir",[()=>{
    return new ClassRoutineDir();
  }]);
}