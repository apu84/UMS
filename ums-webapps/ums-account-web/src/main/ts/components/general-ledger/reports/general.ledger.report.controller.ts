module ums {
  class GeneralLedgerReportController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', '$q', 'GeneralLedgerReportService'];

    private accounts: IAccount[];
    private selectedAccount: IAccount;
    private groups: IGroup[];
    private selectedGroup: IGroup;
    private accountType: string;
    private fromDate: string;
    private toDate: string;

    private ALL: string = "1";
    private SPECIFIC: string = "2";
    private GROUP: string = "3";

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private $q: ng.IQService, private generalLedgerReportService: GeneralLedgerReportService) {
      this.initialize();
    }

    public initialize() {
      this.accountType = this.ALL;
      let day = new Date();
      this.fromDate = "01-01-" + day.getFullYear();
      this.toDate = moment(day).format("DD-MM-YYYY");
    }

    public accountTypeChanged() {
      this.selectedAccount = <IAccount>{};
      this.selectedGroup = <IGroup>{};
      if (this.accountType === this.SPECIFIC) {
        this.accountService.getAllAccounts().then((accounts: IAccount[]) => this.accounts = accounts);
      }
      if (this.accountType === this.GROUP) {
        this.groupService.getAllGroups().then((groups: IGroup[]) => this.groups = groups);
      }
    }

    public runReport() {

      this.generalLedgerReportService.generateReport(this.selectedAccount == null ? "null" : this.selectedAccount.id, this.selectedGroup == null ? "null" : this.selectedGroup.groupCode, this.fromDate, this.toDate);
    }

  }

  UMS.controller("GeneralLedgerReportController", GeneralLedgerReportController);
}