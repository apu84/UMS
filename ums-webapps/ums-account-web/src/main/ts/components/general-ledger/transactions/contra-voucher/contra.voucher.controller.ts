module ums {
  export class ContraVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'ContraVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService'];
    private showAddSection: boolean;


    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private contraVoucherService: ContraVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService) {
      this.initialize();
    }

    public initialize() {
      this.showAddSection = false;
    }
  }

  UMS.controller("ContraVoucherController", ContraVoucherController);
}