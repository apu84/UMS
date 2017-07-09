module ums {
  export class DateRangePickerDirective {
    public bindToController: boolean = true;
    public controller: any = DateRangePickerController;
    public controllerAs: string = 'vm';
    public scope: any = {
      from: '=',
      to: '=',
      required: '@?',
      format: '@?',
      disabled: '=?'
    };
    public templateUrl: string = 'views/directive/date.range.picker.html';
  }

  class DateRangePickerController {
    public static $inject = ['$element'];
    public from: string;
    public to: string;
    public format: string;
    public required: boolean;
    public disabled: boolean;
    public id: number;

    private defaultFormat = 'dd/mm/yyyy';

    constructor($element: any) {
      this.id = Math.abs(Math.random() * 1000);

      if(!this.format) {
        this.format = this.defaultFormat;
      }

      $($element.children()).datepicker({
        format: this.format
      });

    }
  }

  UMS.directive('dateRangePicker', () => new DateRangePickerDirective());
}
