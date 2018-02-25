module ums {
  export class PaymentVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'PaymentVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService'];
    private showAddSection: boolean;
    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private postStatus: boolean;
    private totalCredit: number;
    private totalDebit: number;
    private paymentVouchers: IPaymentVoucher[];
    private paymentVoucherDetail: IPaymentVoucher;
    private paymentVoucherMain: IPaymentVoucher;
    static PAYMENT_VOUCHER_GROUP_FLAG = GroupFlag.YES;
    static PAYMENT_VOUCHER_ID = '6';
    private paymentAccounts: IAccount[];
    private paymentDetailAccounts: IAccount[];

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private paymentVoucherService: PaymentVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService) {
      this.initialize();
    }

    public initialize() {
      this.showAddSection = false;
      this.getCurrencyConversions();
      this.getAccounts();
    }

    private getAccounts() {
      this.accountService.getAccountsByGroupFlag(GroupFlag.YES).then((accounts: IAccount[]) => {
        this.paymentAccounts = accounts;
        console.log("Payment accounts");
        console.log(accounts);
      });
      this.accountService.getAccountsByGroupFlag(GroupFlag.NO).then((accounts: IAccount[]) => {
        this.paymentDetailAccounts = accounts;
      });
    }

    private getCurrencyConversions() {
      this.currencyConversionService.getAll().then((currencyConversions: ICurrencyConversion[]) => {
        this.currencyConversions = [];
        this.currencyConversionMapWithCurrency = {};
        currencyConversions.forEach((currencyConversion: ICurrencyConversion) => {
          this.currencyConversions.push(currencyConversion);
          this.currencyConversionMapWithCurrency[currencyConversion.currency.id] = currencyConversion;
        });
      });
    }

    public addButtonClicked() {
      this.postStatus = false;
      this.showAddSection = true;
      this.totalCredit = 0;
      this.totalDebit = 0;
      this.paymentVoucherService.getVoucherNumber().then((voucherNo: string) => this.voucherNo = voucherNo);
      let currDate: Date = new Date();
      this.paymentVouchers = [];
      this.paymentVoucherMain = <IPaymentVoucher>{};
      this.paymentVoucherDetail = <IPaymentVoucher>{};
      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
    }

  }

  UMS.controller("PaymentVoucherController", PaymentVoucherController);
}