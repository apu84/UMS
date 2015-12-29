module ums {
  interface ProgramSelectorScope extends ng.IScope {
    model: {
      programType: string;
      department: string;
      program: string;
    };
    getDepartment(programType: string): string;

  }
  export class ProgramSelector implements ng.IDirective {
    constructor() {

    }

    public restrict:string = "A";
    public scope = {
      model: "="
    };

    public link = (scope:ProgramSelectorScope, element:JQuery, attributes:any) => {

    }
  }

  UMS.directive("programSelector", () => new ProgramSelector());
}