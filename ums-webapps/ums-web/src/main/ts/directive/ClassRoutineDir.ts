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
    days:any;
    checker:any;
    colspan:number;
    times:any;
    timeChecker:any;
    routineTime:any;
    routineStore:Array<IRoutineStore>;
    currentDate:string;

    showByDay:boolean;
    viewByDay:Function;
    viewByCompleteTable:Function;
    updateDate:Function;
  }


  class IRoutineStore{
    day:string;
    colspan:string;
    courseId:string;
    roomNo:string;
    section:string;
  }

  class ClassRoutineDirController{
    public static $inject=['$scope','classRoomService','courseService','appConstants','$timeout'];
    constructor(private $scope:IClassRoutineDir,
                private classRoomService:ClassRoomService,
                private courseService:CourseService,
                private  appConstants:any,
                public $timeout:ng.ITimeoutService){
      $scope.days = appConstants.weekDays;
      $scope.checker = appConstants.timeChecker;
      var times:string[]=['08:00 am'];
      $scope.routineTime = appConstants.routineTime;
      $scope.routineStore=[];
      $scope.weekday=appConstants.weekday;
      $scope.showByDay=false;
      $scope.updateDate=this.updateDate.bind(this);
      $scope.viewByDay = this.viewByDay.bind(this);
      $scope.viewByCompleteTable = this.viewByCompleteTable.bind(this)


      $scope.times=["08:00 AM","08:50 AM","09:40 AM","10:30 AM","11:20 AM","12:10 PM","01:00 PM","01:50 PM","02:40 PM","03:30 PM","04:20 PM","05:10 PM"];
      $scope.timeChecker="08.00 AM";
      $scope.colspan=1;

      this.getCurrentDate();
      /*$timeout(()=>{
        this.viewByCompleteTable();
      });*/


    }

    private viewByDay():void{
      this.$scope.showByDay=true;
    }

    private viewByCompleteTable():void{
      this.$scope.showByDay=false;
      this.createStudentsRoutine();
    }




    private createStudentsRoutine(){

      var routine:Array<IRoutine>=this.$scope.routines;
      var routineStoreArr: IRoutineStore[] = [];


      for(var d=0;d<this.$scope.days.length;d++){

        for(var i=0;i<12;i++){
          var found:boolean = false;
          var tmpRoutineStore:IRoutineStore=new IRoutineStore();
          for(var routines=0;routines<routine.length; routines++){

            if(routine[routines].startTime == this.$scope.times[i] && routine[routines].day== Number(this.$scope.days[d].id)){
              var routineStore = new IRoutineStore();
              routineStore.day = "0"+routine[routines].day.toString();
              routineStore.colspan= routine[routines].duration.toString();   //routine[routines].courseId;

              routineStore.courseId = this.$scope.courseMap[routine[routines].courseId].no;

              routineStore.roomNo = this.$scope.roomMap[routine[routines].roomId].roomNo;
              routineStore.section="("+this.$scope.routines[routines].section+")";
              //todo remove comments if there is a case, where a teacher takes more than section for a course on the same day and at the same time
              /*if(tmpRoutineStore!=null){
                if(tmpRoutineStore.courseId==routineStore.courseId && tmpRoutineStore.section!=routineStore.section){
                  routineStore.section=tmpRoutineStore.section+"+"+routineStore.section;
                }else{
                  routineStore.section="("+this.$scope.routines[routines].section+")";

                }
              }else{
                routineStore.section="("+this.$scope.routines[routines].section+")";

              }*/
              //routineStore.roomNo=this.$scope.roomMap[routine[routines].id].roomNo;
              routineStoreArr.push(routineStore);
              i=i+(routine[routines].duration-1);
              found = true;
              break;
            }
          }
          if(found==false){
            var routineStore = new IRoutineStore();
            routineStore.day="0"+(d+1).toString();
            routineStore.colspan="1";
            routineStore.courseId="";
            routineStore.roomNo="";
            routineStoreArr.push(routineStore);
          }
        }
      }


      this.$scope.routineStore = routineStoreArr;



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


      this.$scope.currentDate=String(date);
    }


    private updateDate(day:number):void{
      this.$scope.currentDate=String(day);
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

      console.log("scope");
      console.log(scope);
      scope.$watch('routines',(newVal,oldVal)=>{
        if(newVal){
          scope.viewByCompleteTable();
        }
      });
    };


    public templateUrl:string="./views/directive/class-routine-dir.html";
  }

  UMS.directive("classRoutineDir",[()=>{
    return new ClassRoutineDir();
  }]);
}