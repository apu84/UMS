module ums {
  export class ChangePassword {
    public static $inject = ['$scope'];

    constructor(private $scope:any) {
      $scope.submit = this.submit;
    }

    private submit():void {
      console.debug('Change Password');
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

