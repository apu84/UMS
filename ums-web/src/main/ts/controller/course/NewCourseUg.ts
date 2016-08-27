///<reference path="../../model/master_data/Course.ts"/>
///<reference path="../../service/HttpClient.ts"/>
module ums {
  interface INewCourseUgScope extends ng.IScope {
    submit: Function;
    pdfDownloadTest:Function;
    course:Course;
    data:any;
    programs:any;
    semesterOptions: any;
    syllabusOptions: any;
    getPrograms: any;
  }
  export class NewCourseUg {

    public static $inject = ['appConstants', 'HttpClient','$scope','$http'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:INewCourseUgScope,private $http:any) {
      $scope.data = {
        courseTypeOptions: appConstants.courseType,
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester,
        ugDeptOptions:appConstants.ugDept
      };

      //$scope.programType="11";
      $scope.programs =appConstants.initProgram;
      $scope.syllabusOptions=appConstants.initSyllabus;

      $scope.getPrograms=function(deptId){
        if(deptId !="") {
          var ugProgramsArr:any=appConstants.ugPrograms;
          var ugProgramsJson = $.map(ugProgramsArr, function(el) { return el });
          var resultPrograms:any = $.grep(ugProgramsJson, function(e:any){ return e.deptId ==$scope.course.offerToDeptId; });
          $scope.programs= resultPrograms[0].programs;
          $scope.course.programId = $scope.programs[0].id;
        }
        else {
          $scope.programs =appConstants.initProgram;
          $scope.course.programId=appConstants.Empty;
        }
      }

      //TODO: A common changeListener for resetting changes.
      $scope.$watch('course.programId', function() {
        $scope.syllabusOptions=appConstants.initSyllabus;
        /**--------Program Load----------------**/
        if($scope.course.programId!="") {
          httpClient.get('academic/syllabus/program-id/' + $scope.course.programId, 'application/json',
              function (json:any, etag:string) {
                var entries:any = json.entries;
                $scope.syllabusOptions = entries;
                $scope.course.syllabusId = entries[0].id;
              }, function (response:ng.IHttpPromiseCallbackArg<any>) {
                alert(response);
              });
        }

      });
      $scope.submit = this.submit.bind(this);
      $scope.pdfDownloadTest=this.pdfDownloadTest.bind(this);
    }

    private submit():void {
      //this.$scope. course.syllabusId= this.$scope. syllabus.semesterId+"_"+this.$scope. syllabus.programId;
      this.httpClient.post('academic/course/', this.$scope.course, 'application/json')
          .success(() => {
          }).error((data) => {
          });
    }
    private pdfDownloadTest() : void {
//this.$http.post('https://localhost/ums-webservice-common/report/password', {
      this.$http.post('ums-webservice-common/report/password', {},{ cache: false,responseType: 'arraybuffer' }).success(function (data, $scope) {
            var blob=new Blob([data],{ type: 'application/pdf' });
            var fileURL = URL.createObjectURL(blob);
            console.log(fileURL);
            window.open(fileURL);
          })
          .error(function () {
            console.info("fail on download");
          });
    }

/*
          (responsePdf:any, etag:string) => {

            var blob=new Blob([responsePdf],{ type: 'application/pdf' });


            var fileURL = URL.createObjectURL(blob);
            console.log(fileURL);
            window.open(fileURL);

          },
          (error) => {
            console.error(error);
          });
  */

  }
  UMS.controller('NewCourseUg',NewCourseUg);
}

