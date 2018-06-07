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
    currentSemester:Semester;
    showByDay:boolean;
    routineMapWithDayAndTime:any;
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
    private anchorPrefix=".btn.btn-xs.btn-default.";
    public static $inject=['$scope','classRoomService','courseService','appConstants','$timeout', 'semesterService', '$q'];
    constructor(private $scope:IClassRoutineDir,
                private classRoomService:ClassRoomService,
                private courseService:CourseService,
                private  appConstants:any,
                public $timeout:ng.ITimeoutService, private semesterService: SemesterService, private $q: ng.IQService){
      $scope.days = appConstants.weekDays;
      $scope.checker = appConstants.timeChecker;
      var times:string[]=['08:00 am'];
      $scope.routineTime = appConstants.routineTime;
      $scope.routineStore=[];
      $scope.weekday=appConstants.weekday;
      $scope.showByDay=false;
      $scope.routineMapWithDayAndTime={};
      $scope.updateDate=this.updateDate.bind(this);
      $scope.viewByDay = this.viewByDay.bind(this);
      $scope.viewByCompleteTable = this.viewByCompleteTable.bind(this)

      $scope.times=["08:00 AM","08:50 AM","09:40 AM","10:30 AM","11:20 AM","12:10 PM","01:00 PM","01:50 PM","02:40 PM","03:30 PM","04:20 PM","05:10 PM"];
      $scope.timeChecker="08.00 AM";
      $scope.colspan=1;
      this.getCurrentDate();
      this.getCurrentSemester();
    }

    private getCurrentSemester(){
      let underGraduateProgramType:number=11;
      let active:number =1;
      this.semesterService.fetchSemesters(underGraduateProgramType).then((semester:Semester[])=>{
        this.$scope.currentSemester = semester.filter((s:Semester)=> s.status==active)[0];
      });
    }

    private viewByDay():void{
      this.resetSelection();
      $(this.anchorPrefix+"listView").css({"background-color":"black"});
      $(".fa.fa-bars").css({"color":"white"});

      this.$scope.showByDay=true;
    }

    private viewByCompleteTable():void{
      this.resetSelection();
      $(this.anchorPrefix+"detailView").css({"background-color":"black"});
      $(".fa.fa-table").css({"color":"white"});

      this.$scope.showByDay=false;
      this.createRoutine();
    }


    private resetSelection(){
      $(this.anchorPrefix+"detailView").css({"background-color":"white"});
      $(this.anchorPrefix+"listView").css({"background-color":"white"});
      $(".fa.fa-table").css({"color":"black"});
      $(".fa.fa-bars").css({"color":"black"});
    }



    private createRoutineMap():ng.IPromise<any>{
      this.$scope.routineMapWithDayAndTime={};
      let defer:ng.IDeferred<any> = this.$q.defer();
      let routineList:IRoutine[] = this.$scope.routines;
      routineList.forEach((r:IRoutine)=>{
        if(this.$scope.routineMapWithDayAndTime[r.day+r.startTime]==null){
          this.$scope.routineMapWithDayAndTime[r.day+r.startTime]=r.section;
        }else{
          this.$scope.routineMapWithDayAndTime[r.day+r.startTime] = this.$scope.routineMapWithDayAndTime[r.day+r.startTime]+"+"+r.section;
        }
      });
      defer.resolve(this.$scope.routineMapWithDayAndTime);
      return defer.promise;
    }


    private createRoutine(){

      this.createRoutineMap().then((routineSectionMapWIthDateAndTime:any)=>{
          var routine:Array<IRoutine>=this.$scope.routines;
          console.log("All routines");
          console.log(routine);
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
                          let tmpRoutine:IRoutine = routine[routines];
                          routineStore.section="("+routineSectionMapWIthDateAndTime[tmpRoutine.day+tmpRoutine.startTime]+")";
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
      });


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