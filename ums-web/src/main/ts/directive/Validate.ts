module ums {
  export class Validate {
    constructor() {

    }

    public restrict:string = 'A';
    public scope = false;

    public link = ($scope:any, element:JQuery, attributes) => {
      $(element).validate({
        errorPlacement: function (error, element) {
          error.insertAfter(element);
        }
      });

      $scope.validate = (submit:Function) => {
        if ($(element).valid()) {
          submit();
        }
      }
    }
  }

  UMS.directive("validate", ()=> new Validate());
}