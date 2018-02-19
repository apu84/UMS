module ums {

  interface IBalanceTypeDir extends ng.IScope {
    balanceType: string;
  }

  class BalanceTypeController {
    public static $inject = ['$scope']

    constructor(private $scope: ng.IScope) {

    }
  }

  class BalanceTypeDirective implements ng.IDirective {
    constructor() {

    }

    public restring: string = "A";
    public scope = {
      balanceType: "=balanceType"
    }
    public controller = BalanceTypeController;

    public link = (scope: any, element: any, attributes: any) => {

    }

    public templateUrl: string = "./views/directive/balance-type-directive.html";

  }

  UMS.directive("balanceTypeDirective", [() => {
    return new BalanceTypeDirective();
  }])
}