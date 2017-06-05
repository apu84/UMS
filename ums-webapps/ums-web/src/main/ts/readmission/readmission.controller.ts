module ums {
  export class ReadmissionController {
    public static $inject = ['$scope', 'ReadmissionService'];

    constructor(private $scope: ng.IScope,
                private readmissionService: ReadmissionService)

  }
  UMS.controller('ReadmissionController', ReadmissionController);
}