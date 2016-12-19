/**
 * Created by My Pc on 17-Dec-16.
 */

module ums{
  import Semester = ums.Semester;
  interface IAdmissionTaletalkData extends ng.IScope{

    data:any;
    semesters:Array<Semester>;
    programTypes:Array<IProgramType>;
    programType:IProgramType;

    getSemesters:Function;
  }


  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionTaletalkData{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionTaletalkData,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.programTypes = appConstants.programType;
      console.log($scope.programTypes[0]);
      $scope.programType = $scope.programTypes[0];


      $scope.getSemesters = this.getSemesters.bind(this);
    }




    private getSemesters(programType:IProgramType){
      console.log("This is program type");
      console.log(this.$scope.programType);
    }

  }

  UMS.controller("AdmissionTaletalkData", AdmissionTaletalkData);
}