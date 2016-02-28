module ums {
  export class BindHtml implements ng.IDirective {
    constructor(private $compile: ng.ICompileService) {

    }
    public restrict = "A";
    public link = ($scope: any, element: JQuery, attributes: any) => {
      $(element).replaceWith(this.$compile(attributes['umsBindHtml'])($scope));
    };
  }

  UMS.directive("umsBindHtml", ['$compile', ($compile) => new BindHtml($compile)]);
}