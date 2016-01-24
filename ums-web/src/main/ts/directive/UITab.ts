module ums {
  export class UITab implements ng.IDirective {
    public restrict = "A";
    public link = ($scope: any, element: JQuery, attr: any)=> {
      $("a", element).click(function (e) {
        e.preventDefault();
      });
    }
  }

  UMS.directive("uiTab", ()=> new UITab());
}