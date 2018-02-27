
module ums {


  export class PaymentVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'PaymentVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService', 'AccountBalanceService', 'ChequeRegisterService', '$q'];
    private showAddSection: boolean;
    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private baseCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private voucherMapWithId: any;
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
    private dateFormat: string;

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
                private accountBalanceService: AccountBalanceService,
                private chequeRegisterService: ChequeRegisterService, private $q: ng.IQService) {
      this.initialize();
    }

    public initialize() {
      this.dateFormat = "dd-mm-yyyy";
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
      this.paymentVoucherMain.balanceType = BalanceType.Dr;
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
      this.voucherOfAddModal = this.addNecessaryAttributesToVoucher(this.voucherOfAddModal);
      this.detailVouchers.push(this.voucherOfAddModal);
      this.countTotalAmount();
    }

    private addNecessaryAttributesToVoucher(voucher: IPaymentVoucher): IPaymentVoucher {
      console.log("*****************");
      console.log(voucher);
      voucher.accountId = voucher.account.id;
      voucher.voucherNo = this.voucherNo;
      voucher.voucherId = PaymentVoucherController.PAYMENT_VOUCHER_ID;
      voucher.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
      voucher.foreignCurrency = voucher.amount * voucher.conversionFactor;
      voucher.voucherDate = this.voucherDate;
      voucher.currencyId = this.selectedCurrency.id;
      return voucher;
    }


    public saveVoucher() {
      if (this.paymentVoucherMain == null)
        this.notify.error("Account Name is not selected");
      else {
        this.paymentVoucherMain = this.addNecessaryAttributesToVoucher(this.paymentVoucherMain);
        this.paymentVoucherMain.amount = this.selectedPaymentAccountCurrentBalance + this.totalAmount;
        this.detailVouchers.push(this.paymentVoucherMain);
        this.paymentVoucherService.saveVoucher(this.detailVouchers).then((vouchers: IPaymentVoucher[]) => {
          this.extractMainAndDetailSectionFromVouchers(vouchers).then((updatedVouchers: IPaymentVoucher[]) => {
            this.assignChequeNumberToVouchers(vouchers);
          });
        });
      }
    }

    private extractMainAndDetailSectionFromVouchers(vouchers: IPaymentVoucher[]): ng.IPromise<IPaymentVoucher[]> {
      let defer: ng.IDeferred<IPaymentVoucher[]> = this.$q.defer();
      this.detailVouchers = [];
      this.voucherMapWithId = {};
      vouchers.forEach((v: IPaymentVoucher) => {
        this.voucherMapWithId[v.id] = v;
        if (v.serialNo == null)
          this.paymentVoucherMain = v;
        else
          this.detailVouchers.push(v);
      });
      defer.resolve(vouchers);

      return defer.promise;
    }

    private assignChequeNumberToVouchers(vouchers: IPaymentVoucher[]) {
      let transactionIdList: string[] = [];
      vouchers.forEach((v: IPaymentVoucher) => transactionIdList.push(v.id));

      this.chequeRegisterService.getChequeRegisterList(transactionIdList).then((chequeList: IChequeRegister[]) => {
        chequeList.forEach((c: IChequeRegister) => {
          let voucher: IPaymentVoucher = this.voucherMapWithId[c.accountTransactionId];
          voucher.chequeNo = c.chequeNo;
          voucher.chequeDate = c.chequeDate;
        });
      });
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