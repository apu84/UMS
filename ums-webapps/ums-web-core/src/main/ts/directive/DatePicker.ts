module ums {
  class DatePicker implements ng.IDirective {
    static $inject=['$timeout'];
    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "A";
    public scope={
      model:'=dateModel',
      format:'=dateFormat',
      disable:'=disable'
    };

    public link = ($scope: any, element: any, attribute:any) => {
      if($scope.disable){
        $(element).disableSelection();
      }
      this.$timeout(() => {
        $(element).datepicker();
        // $('.datepicker-default').on('change', function () {
        //   $('.datepicker').hide();
        // });
      });
    };
  }
  UMS.directive('datePicker', ['$timeout', ($timeout:ng.ITimeoutService) => new DatePicker($timeout)]);
}
