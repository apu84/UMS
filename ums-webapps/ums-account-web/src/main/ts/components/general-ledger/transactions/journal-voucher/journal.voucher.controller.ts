module ums {
  export class JournalVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService'];

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
  }

  UMS.controller("JournalVoucherController", JournalVoucherController);
}