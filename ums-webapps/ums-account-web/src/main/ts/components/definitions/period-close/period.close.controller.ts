module ums {
  export class PeriodCloseController {
    public static $inject = ['$scope', '$modal', 'notify', 'FinancialAccountYearService', '$timeout'];

    private currentFinancialAccountYear: IFinancialAccountYear;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private financialAccountYearService: FinancialAccountYearService,
                private $timeout: ng.ITimeoutService) {

      this.initialize();
    }

    public initialize() {

    }

    public getCurrentFinancialAccountYear() {

    }

  }

  UMS.controller("PeriodCloseController", PeriodCloseController);
}