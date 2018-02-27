
module ums {


  export class PaymentVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'PaymentVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService', 'AccountBalanceService'];
    private showAddSection: boolean;
    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private baseCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private postStatus: boolean;
    private totalCredit: number;
    private totalDebit: number;
    private paymentVouchers: IPaymentVoucher[];
    private paymentVoucherDetail: IPaymentVoucher;
    private detailVouchers: IPaymentVoucher[];
    private paymentVoucherMain: IPaymentVoucher;
    static PAYMENT_VOUCHER_GROUP_FLAG = GroupFlag.YES;
    static PAYMENT_VOUCHER_ID = '6';
    private paymentAccounts: IAccount[];
    private selectedPaymentAccount: IAccount;
    private selectedPaymentAccountCurrentBalance: number;
    private paymentDetailAccounts: IAccount[];
    private totalAmount: number;
    private voucherOfAddModal: IPaymentVoucher;


    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private paymentVoucherService: PaymentVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService,
                private accountBalanceService: AccountBalanceService) {
      this.initialize();
    }

    public initialize() {
      this.showAddSection = false;
      this.getCurrencyConversions();
      this.getAccounts();
      this.getCurrencies();
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

    public getAccountBalance() {
      this.accountBalanceService.getAccountBalance(this.paymentVoucherMain.account.id).then((currentBalance: number) => {
        this.selectedPaymentAccountCurrentBalance = currentBalance;
        console.log(accounting.formatNumber(10000));
        console.log(accounting.formatColumn([10000], "$ "));
      });
    }

    public formatCurrency(currency: number): any {
      return accounting.formatMoney(currency, this.selectedCurrency.notation + " ");
    }

    public formatBaseCurrency(currency: number): any {
      let baseCurrencyConversion = currency * (this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor);

      return accounting.formatMoney(baseCurrencyConversion, this.baseCurrency.notation + " ");
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


    private getCurrencies() {
      this.currencyService.getAllCurrencies().then((currencies: ICurrency[]) => {
        this.currencies = currencies;
        console.log("Currencies");
        console.log(currencies);
        this.selectedCurrency = currencies[0];
        this.baseCurrency = currencies.filter((c: ICurrency) => c.currencyFlag == CurrencyFlag.BASE)[0];
      });
    }

    public addButtonClicked() {
      this.postStatus = false;
      this.showAddSection = true;
      this.totalCredit = 0;
      this.totalDebit = 0;
      this.totalAmount = 0;
      this.paymentVoucherService.getVoucherNumber().then((voucherNo: string) => this.voucherNo = voucherNo);
      let currDate: Date = new Date();
      this.paymentVouchers = [];
      this.paymentVoucherMain = <IPaymentVoucher>{};
      this.paymentVoucherDetail = <IPaymentVoucher>{};
      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
      this.detailVouchers = [];
    }

    public addData() {
      this.voucherOfAddModal = <IPaymentVoucher>{};
      this.voucherOfAddModal.serialNo = this.detailVouchers.length + 1;
      this.voucherOfAddModal.balanceType = BalanceType.Cr;
    }

    public addDataToVoucherTable() {
      this.voucherOfAddModal.accountId = this.voucherOfAddModal.account.id;
      this.voucherOfAddModal.voucherNo = this.voucherNo;
      this.voucherOfAddModal.voucherId = PaymentVoucherController.PAYMENT_VOUCHER_ID;
      this.voucherOfAddModal.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
      this.voucherOfAddModal.foreignCurrency = this.voucherOfAddModal.amount * this.voucherOfAddModal.conversionFactor;
      this.voucherOfAddModal.voucherDate = this.voucherDate;
      this.voucherOfAddModal.currencyId = this.selectedCurrency.id;
      this.detailVouchers.push(this.voucherOfAddModal);
      this.countTotalAmount();
    }


    public saveVoucher() {

    }



    public countTotalAmount() {
      this.detailVouchers.forEach((v: IPaymentVoucher) => {
        this.totalAmount = v.amount;
        console.log(this.totalAmount);
      });
    }

  }

  UMS.controller("PaymentVoucherController", PaymentVoucherController);
}