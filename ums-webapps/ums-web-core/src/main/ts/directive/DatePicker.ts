module ums {
  class DatePicker implements ng.IDirective {
    static $inject = ['$timeout'];

    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "EA";
    public scope = {
      model: '=dateModel',
      format: '@dateFormat',
      disable: '@disable',
      dateChanged: '&dateChanged'
    };

    public link = ($scope: any, element: any, attribute: any) => {


      console.log("disable: " + $scope.disable);
      let dateFormat = $scope.format;
      if ($scope.disable == true) {
        $('.datepicker-default').enableSelection();
      } else {
        $('.datepicker-default').enableSelection();
      }
      this.$timeout(() => {

        $('.datepicker-default').datepicker({
          dateFormat: $scope.dateFormat
        });


        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
        console.log("------");
      });


    };

    public templateUrl: string = "./views/directive/date-picker.html";
  }

  UMS.directive('datePicker', ['$timeout', ($timeout: ng.ITimeoutService) => new DatePicker($timeout)]);
}