module ums {
  export class NewSyllabus {
    public static $inject = ['appConstants','$scope'];
    constructor(private appConstants:any,private $scope:any) {
      $scope.data = {
        programTypeOptions:appConstants.programType,
        ugPrograms:appConstants.ugPrograms
      };

      $scope.depts= [{id:'',name:'Select Dept./School'}];
      $scope.programs = [{id: '', longName: 'Select a Program'}];
      $scope.selectedProgramType="";
      $scope.selectedDept="";
      $scope.selectedProgram="";
      $scope.getDepts=function(programType){
        $scope.programs = [{id: '', longName: 'Select a Program'}];
        $scope.selectedProgram = "";

        if(programType=="11")
          $scope.depts= appConstants.ugDept;
        else if(programType=="22")
          $scope.depts= appConstants.pgDept;
        else
          $scope.depts= [{id:'',name:'Select Dept./School'}];

        $scope.selectedDept = "";
      }
      var abc:any;
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
  UMS.controller('NewSyllabus', NewSyllabus);
}

