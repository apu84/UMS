module ums {
  export class CourseStatusList {
    public static $inject = ['$scope', '$modalInstance', 'marksSubmissionStatusList', 'departmentName',
      'yearSemester', 'appConstants'];

    constructor(private $scope: any, private $modalInstance: any,
                private marksSubmissionStatusList: Array<MarksSubmissionStatus>,
                private departmentName: string,
                private yearSemester: string,
                private appConstants: any) {
      $scope.marksSubmissionStatusList = marksSubmissionStatusList;
      $scope.departmentName = departmentName;
      $scope.yearSemester = yearSemester;
      $scope.ok = this.ok.bind(this);
      $scope.isReadyForResultProcess = this.isReadyForResultProcess.bind(this);
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
    }

    private isReadyForResultProcess(status): boolean {
      return parseInt(status) === this.appConstants.MARKS_SUBMISSION_STATUS.ACCEPTED_BY_COE;
    }
  }
  UMS.controller("CourseStatusList", CourseStatusList);
}
