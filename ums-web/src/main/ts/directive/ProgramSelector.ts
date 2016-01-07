module ums {
  export class ProgramSelector implements ng.IDirective {

    constructor() {
    }

    public restrict:string = "A";
    public scope = {
      model: "="
    };

    public link = (scope:any, element:JQuery, attributes:any) => {
    };

    public templateUrl:string = "./views/directive/program-selector.html";
  }

  UMS.directive("programSelector", () => new ProgramSelector());
}