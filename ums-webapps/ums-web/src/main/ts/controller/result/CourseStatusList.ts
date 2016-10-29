module ums {
  export class CourseStatusList {
    public static $inject = ['$scope', '$modalInstance', 'items'];

    constructor(private $scope: any, private $modalInstance: any,
                private items: Array<MarksSubmissionStatus>) {
      $scope.items = items;
    }
  }
  UMS.controller("CourseStatusList", CourseStatusList);
}
