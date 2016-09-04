module ums {
  export class CourseRegTypeLegend implements ng.IDirective {

    constructor() {
    }

    public restrict:string = "E";
    public scope = {
      data: '='
    };

    public link = (scope:any, element:JQuery, attributes:any) => {
      scope.examType=attributes.examtype;
    };

    public templateUrl:string = "./views/directive/course-reg-type-legend.html";
  }

  UMS.directive("courseRegTypeLegend", () => new CourseRegTypeLegend());
}