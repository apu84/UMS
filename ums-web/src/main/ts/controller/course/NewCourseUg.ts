///<reference path="../../service/HttpClient.ts"/>
module ums {
  export class NewCourseUg {

    public static $inject = ['appConstants','$scope', '$http'];
    constructor(private appConstants:any,private $scope:any,private $http:any) {
      $scope.data = {
        courseTypeOptions: appConstants.courseType,
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester,
        ugDeptOptions:appConstants.ugDept
      };

      $scope.selectedProgramType="11";
      $scope.selectedDept="";
      $scope.programs = [{id: '', longName: 'Select a Program'}];
      $scope.selectedProgram="";

      $scope.getPrograms=function(deptId){
        if(deptId !="" && $scope.selectedProgramType=="11") {
          var ugProgramsArr:any=appConstants.ugPrograms;
          var ugProgramsJson = $.map(ugProgramsArr, function(el) { return el });
          var resultPrograms:any = $.grep(ugProgramsJson, function(e:any){ return e.deptId ==$scope.selectedDept; });
          $scope.programs= resultPrograms[0].programs;
          $scope.selectedProgram = $scope.programs[0].id;
        }
        else {
          $scope.programs = [{id: '', longName: 'Select a Program'}];
          $scope.selectedProgram = "";
        }
      }


    }
  }
  UMS.controller('NewCourseUg',NewCourseUg);
}

