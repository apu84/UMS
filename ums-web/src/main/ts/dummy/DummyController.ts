module ums {
  export class DummyController {
    public static $inject = ['$scope', '$stateParams'];

    constructor($scope: any, $stateParams: any) {
      console.debug("Inside dummy controller");
      console.debug("%o", $stateParams.type);
    }
  }
  UMS.controller("DummyController", DummyController);
}