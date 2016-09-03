module ums {
  export class ConvertToNumber implements ng.IDirective {

    constructor() {
    }
    public restrict: string = 'A';
    public require: string = 'ngModel';

    public link = (scope, element, attrs, ngModel: ng.INgModelController)=> {
      ngModel.$parsers.push((val) => {
        return parseInt(val, 10);
      });

      ngModel.$formatters.push(function (val) {
        return '' + val;
      });
  };

  }

  UMS.directive("convertToNumber", [() => new ConvertToNumber()]);
}