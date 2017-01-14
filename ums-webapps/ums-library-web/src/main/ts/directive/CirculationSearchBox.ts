module ums {
  export class CirculationSearchBox implements ng.IDirective {

    constructor() {
    }

    public restrict:string = "E";
    public scope = {
      data: '='
    };

    public link = (scope:any, element:JQuery, attributes:any) => {
      scope.circulationType=attributes.circulationtype;

    };

    public templateUrl:string = "./views/directive/circulation-search-box.html";
  }

  UMS.directive("circulationSearchBox", () => new CirculationSearchBox());
}