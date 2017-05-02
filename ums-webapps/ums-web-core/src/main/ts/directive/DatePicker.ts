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

      console.log("attributes");
      console.log(attribute.disable);
      console.log("scope");
      console.log($scope);
      if($scope.disable==true){
        $('.datepicker').disableSelection();
      }
      this.$timeout(() => {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });

      });


    };

    public templateUrl:string="./views/directive/date-picker.html";
  }
  UMS.directive('datePicker', ['$timeout', ($timeout:ng.ITimeoutService) => new DatePicker($timeout)]);
}