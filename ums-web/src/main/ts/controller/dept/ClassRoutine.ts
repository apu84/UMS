///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>
module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    semesterId:number;
    programType:number;
    studentsYear:number;
    studentsSemester:number;
    section:string;


    getSemesters:Function;
  }

  interface ISemester{
    id:number;
    name:string;
    status:number;
  }


  export class ClassRoutine  {


    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoutineScope,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService) {


      $scope.getSemesters = this.getSemesters.bind(this);
    }

    private getSemesters():void{
      this.$scope.semesterArr=[];
      this.semesterService.getAllSemesters().then((semesterArr:Array<ISemester>)=>{
        this.$scope.semesterArr = semesterArr;
      });
    }


  }

  UMS.controller("ClassRoutine", ClassRoutine);
}
