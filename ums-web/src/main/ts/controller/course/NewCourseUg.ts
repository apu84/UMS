///<reference path="../../service/HttpClient.ts"/>
module ums {
  export class NewCourseUg {

    public static $inject = ['appConstants','$scope', 'HttpClient'];
    constructor(private appConstants:any,private $scope:any,private httpClient:HttpClient) {
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
      $scope.syllabusOptions=[{id: '', semester_name: 'Select a ',program_name:'Syllabus'}];
      $scope.selectedProgram="";
      $scope.selectedSyllabus="";

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


      $scope.$watch('selectedProgram', function() {
        $scope.syllabusOptions=[{id: '', semester_name: 'Select a ',program_name:'Syllabus'}];
        /**--------Program Load----------------**/
        if($scope.selectedProgram!="") {
          httpClient.get('academic/syllabus/program-id/' + $scope.selectedProgram, 'application/json',
              function (json:any, etag:string) {
                var entries:any = json.entries;
                $scope.syllabusOptions = entries;
                $scope.selectedSyllabus = entries[0].id;
              }, function (response:ng.IHttpPromiseCallbackArg<any>) {
                alert(response);
              });
        }

      });
    }

  }
  UMS.controller('NewCourseUg',NewCourseUg);
}

