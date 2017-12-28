module ums {
  export class FinancialAccountYearController {
    public static $inject = ['$scope', 'notify', 'FinancialAccountYearService']

    private financialAccountYears: IFinancialAccountYear[];
    private currentFinancialAccountYear: IFinancialAccountYear;

    constructor(private $scope: ng.IScope, private notify: Notify, private financialAccountYearService: FinancialAccountYearService) {
      this.initialize();
    }

    public initialize() {
      this.financialAccountYearService.getAllFinalcialYears().then((years: IFinancialAccountYear[]) => {
        this.currentFinancialAccountYear = <IFinancialAccountYear>{};
        this.financialAccountYears = [];
        this.financialAccountYears = years;
        this.currentFinancialAccountYear = years.filter((f: IFinancialAccountYear) => f.bookClosingFlag == BookClosingFlagType.OPEN)[0];
      });

    }
  }

  UMS.controller("FinancialAccountYearController", FinancialAccountYearController);
}