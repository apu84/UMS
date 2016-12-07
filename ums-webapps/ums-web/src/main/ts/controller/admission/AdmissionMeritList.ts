/**
 * Created by My Pc on 01-Dec-16.
 */

module ums{

  interface IAdmissionMerit extends ng.IScope{

    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;

    getSemesters:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  class AdmissionMeritList{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionMerit,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService) {

      $scope.programTypes=appConstants.programType;
    }

    private getSemesters(programType:IProgramType):void{
      this.semesterService.fetchSemesters(+programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        this.$scope.semesters=semesters;
        console.log(this.$scope.semesters);
      });
    }

  }

  UMS.controller("AdmissionMeritList",AdmissionMeritList);
}