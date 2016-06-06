module ums {
  export class ConvertToNumber implements ng.IDirective {

    constructor(private $ngModel:any) {
    }
    public restrict: string = 'A';

    public link=(scope, element, attrs, ngModel)=> {
    this.$ngModel.$parsers.push(function(val) {
      return parseInt(val, 10);
    });
    ngModel.$formatters.push(function (val) {
      return '' + val;
    });
  };

  }

  UMS.directive("convertToNumber",['$ngModel', ($ngModel) => new ConvertToNumber($ngModel)]);
}