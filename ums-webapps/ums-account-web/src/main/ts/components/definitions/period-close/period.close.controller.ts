module ums {
  import IPeriodClose = ums.IPeriodClose;

  export class PeriodCloseController {
    public static $inject = ['$scope', '$modal', 'notify', 'FinancialAccountYearService', '$timeout', 'PeriodCloseService', 'MonthService'];

    private financialAccountYear: IFinancialAccountYear;
    private financialYearType: FinancialYearType;
    private periodCloseList: IPeriodClose[];
    private disable: boolean;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private financialAccountYearService: FinancialAccountYearService,
                private $timeout: ng.ITimeoutService, private periodCloseService: PeriodCloseService, private monthService: MonthService) {
      this.financialYearType = FinancialYearType.CURRENT_YEAR;
      this.initialize();

    }

    public initialize() {
      this.financialAccountYearService.getOpenedFinancialAccountYear().then((financialAccountYear: IFinancialAccountYear) => this.financialAccountYear = financialAccountYear);
      this.periodCloseService.getPeriodCloseList(this.financialYearType).then((periodCloseList: IPeriodClose[]) => {
        console.log("period close list");
        console.log(periodCloseList);

        if (periodCloseList.length == 0) {
          this.disable = false;
          this.getAllMonths();
        }
        else {
          this.disable = true;
          this.periodCloseList = periodCloseList;
        }

      });

    }

    public edit() {
      this.disable = false;
    }

    public cancel() {
      this.periodCloseList = [];
      this.initialize();
    }

    public save() {
      console.log("sve");
      this.periodCloseService.save(this.periodCloseList).then((periodList: IPeriodClose[]) => {
        this.periodCloseList = [];
        this.periodCloseList = periodList;
        this.disable = true;
      });
    }

    public getAllMonths() {
      this.monthService.getAllMonths().then((months: IMonth[]) => {
        console.log("inthe get all months");
        console.log(months);
        this.periodCloseList = [];
        months.forEach((month: IMonth) => {
          let periodClose: IPeriodClose = <IPeriodClose>{};
          periodClose.monthId = month.id;
          periodClose.month = month;
          periodClose.financialAccountYear = this.financialAccountYear;
          periodClose.financialAccountYearId = this.financialAccountYear.stringId;
          let date = new Date();
          periodClose.closeYear = date.getFullYear();
          periodClose.periodCloseFlag = IOpenCloseType.O;
          this.periodCloseList.push(periodClose);
        });
        console.log(this.periodCloseList);
      });
    }

    public getCurrentFinancialAccountYear() {

    }

  }

  UMS.controller("PeriodCloseController", PeriodCloseController);
}