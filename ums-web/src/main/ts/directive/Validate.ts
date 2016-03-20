module ums {
  export class Validate {
    constructor() {

    }

    public restrict:string = 'A';
    public scope = {
      onvalidate: "="
    };

    public link = ($scope:any, element:JQuery, attributes) => {
      $(element).validate({
        errorPlacement: function (error, element) {
          error.insertAfter(element);
        }
      });

      $(element).on('click', 'button.btn-success', () => {
        if($(element).valid()){
          $scope.onvalidate();
        }
      });
    }
  }

  UMS.directive("validate", ()=> new Validate());
}