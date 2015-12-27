module ums {
  export class NewSemester {
    public static $inject = ['appConstants','$scope'];
    constructor(private appConstants:any,private $scope:any) {

      $scope.data = {
        programTypeOptions:appConstants.programType,
        semesterOptions:appConstants.semester
      };

      setTimeout(function () {
        $('.make-switch').bootstrapSwitch();
        $('#TheCheckBox').bootstrapSwitch();
      }, 50);

      $('.datepicker-default').datepicker();
    }
  }
  UMS.controller('NewSemester', NewSemester);
}

