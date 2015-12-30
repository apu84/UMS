///<reference path="../../model/master_data/Syllabus.ts"/>
///<reference path="../../service/HttpClient.ts"/>
module ums {
  interface INewSyllabusScope extends ng.IScope {
    submit: Function;
    syllabus:Syllabus;
    data:any;
    depts:any;programs:any;
    getDepts:Function;getPrograms:Function;
    semesterOptions:any;
  }
  export class NewSyllabus {
    public static $inject = ['appConstants', 'HttpClient','$scope'];
      constructor(private appConstants:any,private httpClient:HttpClient,private $scope:INewSyllabusScope) {
      $scope.data = {
        programTypeOptions:appConstants.programType,
        ugPrograms:appConstants.ugPrograms
      };

        $scope.depts= [{id:'',name:'Select Dept./School'}];
        $scope.programs = [{id: '', longName: 'Select a Program'}];
        $scope.semesterOptions = [{id: '', name: 'Select a Semester'}];


      $scope.getDepts=function(programType) {
        $scope.programs = [{id: '', longName: 'Select a Program'}];
        $scope.syllabus.programId = appConstants.Empty;

        if (programType == "11")
          $scope.depts = appConstants.ugDept;
        else if (programType == "22")
          $scope.depts = appConstants.pgDept;
        else
          $scope.depts = [{id: '', name: 'Select Dept./School'}];

        $scope.syllabus.deptId = appConstants.Empty;
        /**--------Semester Load----------------**/
        if($scope.syllabus.programTypeId!=appConstants.Empty) {
          httpClient.get('academic/semester/program-type/' + $scope.syllabus.programTypeId + "/limit/0", 'application/json',
              function (json:any, etag:string) {
                var entries:any = json.entries;
                $scope.semesterOptions = entries;
                $scope.syllabus.semesterId = entries[0].id;
              }, function (response:ng.IHttpPromiseCallbackArg<any>) {
                alert(response);
              });
        }
      }
      $scope.getPrograms=function(deptId){
        if(deptId !="" && $scope.syllabus.programTypeId=="11") {
          var ugProgramsArr:any=appConstants.ugPrograms;
          var ugProgramsJson = $.map(ugProgramsArr, function(el) { return el });
          var resultPrograms:any = $.grep(ugProgramsJson, function(e:any){ return e.deptId ==$scope.syllabus.deptId; });
          $scope.programs= resultPrograms[0].programs;
          $scope.syllabus.programId = $scope.programs[0].id;
        }
        else {
          $scope.programs = [{id: '', longName: 'Select a Program'}];
          $scope.syllabus.programId=appConstants.Empty;
        }
      }
        $scope.submit = this.submit.bind(this);
    }
    private submit():void {
      this.$scope. syllabus.syllabusId= this.$scope. syllabus.semesterId+"_"+this.$scope. syllabus.programId;
      this.httpClient.post('academic/syllabus/', this.$scope.syllabus, 'application/json')
          .success(() => {
          }).error((data) => {
          });

    }
  }
  UMS.controller('NewSyllabus', NewSyllabus);
}

