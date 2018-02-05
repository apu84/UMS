module ums {
  export class JournalVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService'];


    private voucherNo: string;
    private voucherDate: string;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private journalVoucherService: JournalVoucherService, private voucherService: VoucherService) {
      this.initialize();
    }

    public initialize() {


    }

    public addModalClicked() {
      this.journalVoucherService.getVoucherNumber().then((voucherNo: string) => {
        this.voucherNo = voucherNo;
      });
      let currDate: Date = new Date();

      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
    }
  }

  UMS.controller("JournalVoucherController", JournalVoucherController);
}