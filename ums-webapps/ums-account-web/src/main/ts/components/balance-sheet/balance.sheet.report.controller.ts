module ums {
  class BalanceSheetReportController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', '$q', 'BalanceSheetReportService'];

    private asOnDate: string;
    private fetchType: string;
    private debtorLedgerFetchType: string;
    private DETAILED = "1";
    private SUMMARIZED = "2";

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private $q: ng.IQService, private balanceSheetReportService: BalanceSheetReportService) {
      this.initialize();
    }

    public initialize(): void {
      let day = new Date();
      this.asOnDate = moment(day).format("DD-MM-YYYY");
      this.fetchType = this.SUMMARIZED;
      this.debtorLedgerFetchType = this.SUMMARIZED;
    }

    public runReport(): void {
      this.balanceSheetReportService.generateReport(this.asOnDate, this.fetchType, this.debtorLedgerFetchType);
    }
  }


  UMS.controller("BalanceSheetReportController", BalanceSheetReportController);
}