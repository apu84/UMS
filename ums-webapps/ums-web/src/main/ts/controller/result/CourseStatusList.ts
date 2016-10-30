module ums {
  export class CourseStatusList {
    public static $inject = ['$scope', '$modalInstance', 'marksSubmissionStatusList', 'departmentName', 'yearSemester'];

    constructor(private $scope: any, private $modalInstance: any,
                private marksSubmissionStatusList: Array<MarksSubmissionStatus>,
                private departmentName: string,
                private yearSemester: string) {
      $scope.marksSubmissionStatusList = marksSubmissionStatusList;
      $scope.departmentName = departmentName;
      $scope.yearSemester = yearSemester;
      $scope.ok = this.ok.bind(this);
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
    }
  }
  UMS.controller("CourseStatusList", CourseStatusList);
}
