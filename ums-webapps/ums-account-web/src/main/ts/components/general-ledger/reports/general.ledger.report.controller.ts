module ums {
  class GeneralLedgerReportController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', '$q', 'GeneralLedgerReportService'];

    private accounts: IAccount[];
    private selectedAccount: IAccount;
    private groups: IGroup[];
    private selectedGroup: IGroup;
    private accountType: string;
    private fetchType: string;
    private fromDate: string;
    private toDate: string;

    private ALL: string = "1";
    private SPECIFIC: string = "2";
    private GROUP: string = "3";

    private FETCH_TYPE_ALL: string = "1";
    private FETCH_TYPE_TRANSACTION_SPECIFIC = "2";


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
      this.fetchType = this.FETCH_TYPE_TRANSACTION_SPECIFIC;
      let day = new Date();
      this.fromDate = "01-01-" + day.getFullYear();
      this.toDate = moment(day).format("DD-MM-YYYY");
    }

    public accountTypeChanged() {
      this.selectedAccount = <IAccount>{};
      this.selectedGroup = <IGroup>{};
      if (this.accountType === this.SPECIFIC) {
        this.accountService.getAllAccounts().then((accounts: IAccount[]) => this.accounts = accounts);
        this.selectedGroup = null;
      }
      else if (this.accountType === this.GROUP) {
        this.groupService.getAllGroups().then((groups: IGroup[]) => this.groups = groups);
        this.selectedAccount = null;
      }
      else {
        this.selectedAccount = null;
        this.selectedGroup = null;
      }
    }

    public runReport() {
      console.log("selected account: " + this.selectedAccount);
      console.log("selected Group: " + this.selectedGroup);
      this.generalLedgerReportService.generateReport(this.selectedAccount == null || this.selectedAccount == undefined ? "null" : this.selectedAccount.id,
          this.selectedGroup == null || this.selectedGroup == undefined ? "null" : this.selectedGroup.groupCode,
          this.fromDate, this.toDate, this.fetchType);
    }

  }

  UMS.controller("GeneralLedgerReportController", GeneralLedgerReportController);
}