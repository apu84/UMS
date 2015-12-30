///<reference path="../../lib/jquery-maskedinput.d.ts"/>
///<reference path="../../service/HttpClient.ts"/>
module ums {
  export class NewStudent {
    public static $inject = ['appConstants','$scope', 'HttpClient'];
    constructor(private appConstants:any,private $scope:any,private httpClient:HttpClient) {

      $scope.data = {
        genderOptions: appConstants.gender,
        deptOptions: appConstants.deptShort,
        bloodGroupOptions: appConstants.bloodGroup,
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

        /**--------Semester Load----------------**/
        $scope.semesterOptions = [{id: '', name: 'Select a Semester'}];
        $scope.selectedSemester="";
        $scope.$watch('selectedProgramType', function() {
          $scope.semesterOptions = [{id: '', name: 'Select a Semester'}];
          if ($scope.selectedProgramType != "") {
            httpClient.get('academic/semester/program-type/' + $scope.selectedProgramType+"/limit/0", 'application/json',
                function (json:any, etag:string) {
                  var entries:any = json.entries;
                  $scope.semesterOptions = entries;
                  $scope.selectedSemester = entries[0].id;
                }, function (response:ng.IHttpPromiseCallbackArg<any>) {
                  alert(response);
                });
          }
        });

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

      //$('.datepicker-default').datepicker();
      $("#birthDate").mask("99/99/9999");
      $("#fileUpload").on('change', function () {

        if (typeof (FileReader) != "undefined") {
          var image_holder = $("#image-holder");
          image_holder.empty();
          var reader = new FileReader();
          reader.onload = function (e) {
            var targetObject:any = e.target;
            $("<img />", {
              "src": targetObject.result,
              "class": "thumb-image",
              "height":"140px",
              "width":"120px"
            }).appendTo(image_holder);
          };
          image_holder.show();
          var thisObject:any = $(this)[0];
          reader.readAsDataURL(thisObject.files[0]);
        } else {
          alert("This browser does not support FileReader.");
        }
      });
    }
  }
  UMS.controller('NewStudent', NewStudent);
}
