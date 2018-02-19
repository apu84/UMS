module ums {
  export class BudgetAllocationController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', '$timeout'];

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private $timeout: ng.ITimeoutService) {

    }
  }

  UMS.controller("BudgetAllocationController", BudgetAllocationController);
}