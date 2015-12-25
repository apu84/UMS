module ums {
  export class Validate {
    constructor() {

    }

    public restrict:string = 'A';
    public scope = {
      onvalidate: "&"
    };

    public link = ($scope:any, element:JQuery, attributes) => {
      $(element).validate({
        errorPlacement: function (error, element) {
          error.insertAfter(element);
        }
      });

      $(element).find('button').on('click', function(){
        if($(element).valid()){
          $scope.onvalidate();
        }
      });
    }
  }

  UMS.directive("validate", ()=> new Validate());
}