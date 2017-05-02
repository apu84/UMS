module ums {
  class DatePicker implements ng.IDirective {
    static $inject=['$timeout'];
    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "A";
    public scope={
      model:'=dateModel',
      format:'=dateFormat'
    };

    public link = ($scope: any, element: any, attribute:any) => {
      console.log("in the date picker directive");
      console.log($scope);
      console.log("element");
      console.log(element);
      console.log("attribute");
      console.log(attribute);
      this.$timeout(() => {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });


     /*   element.datepicker({
          dateFormat: $scope.format
        });
        element.on('change', function () {
          $('.datepicker').hide();
        });*/
      });


    };

    public templateUrl:string="./views/directive/date-picker.html";
  }
  UMS.directive('datePicker', ['$timeout', ($timeout:ng.ITimeoutService) => new DatePicker($timeout)]);
}