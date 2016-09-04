module ums{

  interface ITeachersRoutine extends ng.IScope{

    getTeachersRoutine:Function;
  }

  class TeachersRoutine{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout','$sce','$window','classRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ITeachersRoutine,
                private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,private classRoutineService:ClassRoutineService) {

      $scope.getTeachersRoutine = this.getTeachersRoutine.bind(this);

    }


    private getTeachersRoutine(){
      this.classRoutineService.getClassRoutineReportForTeacher().then((message:string)=>{
        if(message=="success"){

        }else{
          this.notify.error("Error in generating routine report");
        }
      });
    }

  }

  UMS.controller("TeachersRoutine",TeachersRoutine);

}