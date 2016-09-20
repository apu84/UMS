module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    courseArr:Array<ICourse>;
    roomArr:Array<IClassRoom>;
    routineArr:Array<IClassRoutine>;
    semesterId:number;
    programType:number;
    studentsYear:number;
    studentsSemester:number;
    section:string;
    semesterStatus:number;
    dates:any;

    getSemesters:Function;
    searchForRoutineData:Function;
    resetDivs:Function;
  }

  interface ISemester{
    id:number;
    name:string;
    status:number;
  }

  interface ICourse{
    id:string;
    no:string;
  }

  interface IClassRoom{
    id:number;
    roomNo:string;
  }

  interface IClassRoutine{
    id:number;
    semesterId:number;
    courseId:string;
    programId:number;
    day:number;
    academicYear:number;
    academicSemester:number;
    startTime:string;
    endTime:string;
    duration:string;
    roomNo:string;
  }


  export class ClassRoutine  {


    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoutineScope,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private courseService:CourseService, private classRoomService:ClassRoomService, private classRoutineService:ClassRoutineService) {


      $scope.dates={
        1:"Saturday",
        2:"Sunday",
        3:"Monday",
        4:"Tuesday",
        5:"Wednesday",
        6:"Thursday"
      };
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.searchForRoutineData = this.searchForRoutineData.bind(this);
      $scope.resetDivs = this.resetDivs.bind(this);
    }

    private getSemesters():void{
      this.$scope.semesterArr=[];
      this.semesterService.getAllSemesters().then((semesterArr:Array<any>)=>{
        this.$scope.semesterArr = semesterArr;
        console.log(semesterArr);
      });
    }


    private searchForRoutineData():void{

      $("#leftDiv").hide();
      $("#arrowDiv").show();
      $("#rightDiv").removeClass("orgRightClass").addClass("newRightClass");

      var programType = +this.$scope.programType;
      var year = +this.$scope.studentsYear;
      var semester = +this.$scope.studentsSemester;
      this.courseService.getCourseBySemesterAndProgramType(this.$scope.semesterId,programType).then((courseArr:Array<ICourse>)=>{
        this.$scope.courseArr=[];
        this.$scope.courseArr=courseArr;



        this.classRoomService.getClassRooms().then((rooms:Array<IClassRoom>)=>{
          this.$scope.roomArr=[];
          this.$scope.roomArr = rooms;



          this.classRoutineService.getClassRoutineForEmployee(this.$scope.semesterId,year,semester,this.$scope.section)
              .then((routineArr:Array<IClassRoutine>)=>{
                this.$scope.routineArr=[];
                this.$scope.routineArr = routineArr;

              });
        });

      });
    }

    private resetDivs() {
      $("#arrowDiv").hide();
      $("#leftDiv").show();
      $("#rightDiv").removeClass("newRightClass").addClass("orgRightClass");
      //this.decoratedScope.grid.api.resize();
    }


  }

  UMS.controller("ClassRoutine", ClassRoutine);
}
