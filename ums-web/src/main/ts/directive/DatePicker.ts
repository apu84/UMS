module ums {
  export class DatePicker implements ng.IDirective {
    constructor() {
    }

    public restrict: string = "A";
    public link = ($scope: any, element: JQuery, attrs) => {
      element.datepicker();
      element.on('change', function () {
        $('.datepicker').hide();
      });
    };
  }
  UMS.directive('datePicker', () => new DatePicker());
}