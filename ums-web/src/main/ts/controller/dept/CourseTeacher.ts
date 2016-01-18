///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/bootstrap.selectpicker.d.ts"/>
///<reference path="../../model/CourseTeacherSearchParamModel.ts"/>
///<reference path="../../model/CourseTeacherSearchParamModelImp.ts"/>
module ums {

  interface ICourseTeacherScope extends ng.IScope {
    submit: Function;
    courseTeacherSearchParamModel:CourseTeacherSearchParamModel;
    data:any;
    loadingVisibility:boolean;
    fetchCourseTeacherInfo:Function;
  }

  export class CourseTeacher {
    public static $inject = ['appConstants', 'HttpClient','$scope'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:ICourseTeacherScope) {
      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModelImp(this.appConstants, this.httpClient);
      $scope.data = {
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester
      };
      $scope.loadingVisibility=false;
      $scope.fetchCourseTeacherInfo = this.fetchCourseTeacherInfo.bind(this);

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });

    }



    private fetchCourseTeacherInfo():void {
      this.$scope.loadingVisibility=true;
    }
  }
  UMS.controller('CourseTeacher', CourseTeacher);
}

