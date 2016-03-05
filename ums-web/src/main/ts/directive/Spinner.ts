module ums {
  export class Spinner implements ng.IDirective {
    constructor() {
    }

    public link = ($scope: any, element: JQuery, attr) => {
    };

    public templateUrl = './views/common/spinner.html';
  }

  UMS.directive('spinner', () => new Spinner());
}