module ums {
  export class NewCoursePg {

    public static $inject = ['appConstants','$scope', '$http'];
    constructor(private appConstants:any,private $scope:any,private $http:any) {
      $scope.data = {
        courseTypeOptions: appConstants.courseType,
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester,
        ugDeptOptions:appConstants.ugDept
      };
    }
  }
  UMS.controller('NewCoursePg',NewCoursePg);
}

