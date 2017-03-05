module ums {
  export class DatePicker implements ng.IDirective {
    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "A";
    public require: string = 'ngModel';

    public link = ($scope: any, element: JQuery, attrs, ngModelCtrl: ng.INgModelController) => {

      this.$timeout(() => {
        element.datepicker({
          dateFormat: 'dd/mm/yy'
        });
        element.on('change', function () {
          $('.datepicker').hide();
        });
      });


    };
  }
  UMS.directive('datePicker', () => ['$timeout', ($timeout) => new DatePicker($timeout)]);
}