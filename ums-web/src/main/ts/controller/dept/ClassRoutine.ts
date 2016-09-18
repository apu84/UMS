///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>
module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    courseArr:Array<ICourse>;
    semesterId:number;
    programType:number;
    studentsYear:number;
    studentsSemester:number;
    section:string;
    semesterStatus:number;

    getSemesters:Function;
    searchForRoutineData:Function;
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


  export class ClassRoutine  {


    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoutineScope,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private courseService:CourseService, private classRoomService:ClassRoomService, private classRoutineService:ClassRoutineService) {


      $scope.getSemesters = this.getSemesters.bind(this);
    }

    private getSemesters():void{
      this.$scope.semesterArr=[];
      this.semesterService.getAllSemesters().then((semesterArr:Array<ISemester>)=>{
        this.$scope.semesterArr = semesterArr;
      });
    }


    private searchForRoutineData():void{
      var programType = +this.$scope.programType;
      var year = +this.$scope.studentsYear;
      var semester = +this.$scope.studentsSemester;
      //this.courseService.getCourse()
    }


  }

  UMS.controller("ClassRoutine", ClassRoutine);
}
