module ums {
  export class ChangePassword {
    public static $inject = ['$scope'];

    constructor(private $scope:any) {
      $scope.submit = this.submit();
    }

    private submit():void {
      $(".form-horizontal").validate({
        errorPlacement: function (error, element) {
          error.insertAfter(element);
        }
      });
    }
  }

  UMS.controller("changePassword", ChangePassword);
}

