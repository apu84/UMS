module ums {
  class DatePicker implements ng.IDirective {
    static $inject = ['$timeout'];

    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "EA";
    public scope = {
      model: '=dateModel',
      format: '=dateFormat',
      disable: '=disable',
      dateChanged: '&dateChanged'
    };

    public link = ($scope: any, element: any, attribute: any) => {

        console.log("Date format");
        console.log($scope.format);
        let dateFormat=$scope.format;

      if ($scope.disable == true) {
        $('.datepicker').disableSelection();
      }
      this.$timeout(() => {

        $('.datepicker-default').datepicker({
          dateFormat: dateFormat
        });

        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });

      });


    };

    public templateUrl: string = "./views/directive/date-picker.html";
  }
  UMS.directive('datePicker', ['$timeout', ($timeout: ng.ITimeoutService) => new DatePicker($timeout)]);
}