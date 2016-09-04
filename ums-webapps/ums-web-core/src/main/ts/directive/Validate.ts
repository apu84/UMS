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
        highlight: function (element, errorClass) {
          var $element = $(element);
          // Add the red outline.
          $element.parent().addClass(errorClass);
          // Add the red cross.
          $element.siblings(".error_status").addClass("check");
        },
        unhighlight: function (element, errorClass) {
          var $element = $(element);
          // Remove the red cross.
          $element.siblings(".error_status").removeClass("check");
          // Remove the red outline.
          $element.parent().removeClass(errorClass);
        },
        errorClass: 'state-error',
        validClass: 'state-success'
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