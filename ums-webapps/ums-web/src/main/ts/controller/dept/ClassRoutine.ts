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
    dates:Array<IDate>;
    times:Array<ITime>;
    sessionalSections:Array<ISessionalSections>;
    courseType:string;

    addedDate:string;
    addedCourse:string;
    addedSection:string;
    addedStartTime:string;
    addedEndTime:string;
    addedRoomNo:string;

    //map
    courseIdMapCourseNo:any;

    getSemesters:Function;
    searchForRoutineData:Function;
    resetDivs:Function;
    courseSelected:Function;
  }

  interface ISemester{
    id:number;
    name:string;
    status:number;
  }

  interface ISessionalSections{
    id:string;
    name:string;
  }

  interface ICourse{
    id:string;
    no:string;
    type:string;
  }

  interface IDate{
    id:number;
    dateName:string;
  }

  interface ITime{
    id:string;
    val:string;
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




      $scope.courseIdMapCourseNo={};
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.searchForRoutineData = this.searchForRoutineData.bind(this);
      $scope.resetDivs = this.resetDivs.bind(this);
      $scope.courseSelected = this.courseSelected.bind(this);
    }


    private courseSelected():void{

      console.log("Course id");
      console.log(this.$scope.addedCourse);

      this.$scope.courseType="";
      for(var i=0;i<this.$scope.courseArr.length;i++){
        if(this.$scope.courseArr[i].id=this.$scope.addedCourse){
          this.$scope.courseType = this.$scope.courseArr[i].type;
          console.log("In the course type selection");
          console.log(this.$scope.courseArr[i]);
          break;
        }
      }

      if(this.$scope.courseType=="SESSIONAL"){
        this.$scope.sessionalSections=[];
        if(this.$scope.section=='A'){
          this.$scope.sessionalSections=this.appConstants.sessionalSectionsA;
        }
        else if(this.$scope.section=='B'){
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsB;
        }
        else if(this.$scope.section=='C'){
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsC ;
        }
        else{
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsD;
        }
      }

      console.log("---After course changes---");

      console.log(this.$scope.courseType);
      console.log(this.$scope.sessionalSections);


    }

    private getSemesters():void{
      console.log("In the semester");

      this.$scope.semesterArr=[];
      this.semesterService.getAllSemesters().then((semesterArr:Array<any>)=>{
        this.$scope.semesterArr = semesterArr;
        console.log(semesterArr);
      });
    }


    private searchForRoutineData():void{

      this.$scope.dates=[];
      this.$scope.times =[];

      this.$scope.dates = this.appConstants.weekday;
      this.$scope.times = this.appConstants.timeChecker;

      $("#leftDiv").hide();
      $("#arrowDiv").show();
      $("#rightDiv").removeClass("orgRightClass").addClass("newRightClass");

      var programType = +this.$scope.programType;
      var year = +this.$scope.studentsYear;
      var semester = +this.$scope.studentsSemester;
      this.courseService.getCourseBySemesterProgramTypeYearSemester(this.$scope.semesterId,programType,year,semester).then((courseArr:Array<ICourse>)=>{
        this.$scope.courseIdMapCourseNo={};
        this.$scope.courseArr=[];
        this.$scope.courseArr=courseArr;
        console.log(courseArr);

        for(var i=0;i<courseArr.length;i++){
          this.$scope.courseIdMapCourseNo[courseArr[i].id] = courseArr[i].no;
        }


        console.log(this.$scope.courseIdMapCourseNo);

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
