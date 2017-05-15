/**
 * Created by My Pc on 09-May-17.
 */

module ums {
  interface ILmsAppDir extends ng.IScope {
    leaveApplication: LmsApplication;
    leaveTypes: Array<LmsType>;
    leaveType: LmsType;
    remainingLeaves: Array<RemainingLmsLeave>;
  }

  class LmsAppController {
    private static $inject = ['$scope'];

    constructor(private $scope: ILmsAppDir) {

    }
  }

  class LmsAppDir implements ng.IDirective {

    constructor() {

    }

    public restrict: string = "A";
    public scope = {
      leaveApplication: '=leaveApplication',
      leaveTypes: '=leaveTypes',
      leaveType: '=leaveType',
      remainingLeaves: '=remainingLeaves'
    };

    public controller = LmsAppController;

    public link = (scope: any, element: any, attributes: any) => {
      console.log("In the directive");
    };

    public templateUrl: string = "./views/directive/lms-app.html";
  }

  UMS.directive("lmsAppDir", [() => {
    return new LmsAppDir();
  }])
}