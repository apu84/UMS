module ums {
  export class JournalVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService'];


    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private showAddSection: boolean;
    private type: AccountTransactionType;
    private journalVoucherOfAddModal: IJournalVoucher;
    private journalVouchers: IJournalVoucher[];
    private existingJournalVouchers: IJournalVoucher[];
    private accounts: IAccount[];
    private totalDebit: number;
    private totalCredit: number;
    private pageNumber: number;
    private itemsPerPage: number;
    private totalVoucherNumber: number;
    private searchVoucherNo: string;
    private accounting: any;
    static JOURNAL_VOUCHER_GROUP_FLAG = GroupFlag.NO;
    static JOURNAL_VOUCHER_ID = '1';


    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private journalVoucherService: JournalVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService) {
      this.initialize();
    }

    public initialize() {
      this.showAddSection = false;
      this.pageNumber = 1;

      this.itemsPerPage = 15;
      this.type = AccountTransactionType.SELLING;
      this.accountService.getAccountsByGroupFlag(JournalVoucherController.JOURNAL_VOUCHER_GROUP_FLAG).then((accounts: IAccount[]) => {
        console.log("Accounts")
        console.log(accounts);
        this.accounts = accounts;
      });

      this.getPaginatedJournalVouchers();
    }

    public getPaginatedJournalVouchers() {
      this.journalVoucherService.getAllVouchersPaginated(this.itemsPerPage, this.pageNumber).then((paginatedVouchers: IPaginatedVouchers) => {
        this.existingJournalVouchers = paginatedVouchers.vouchers;
        this.totalVoucherNumber = paginatedVouchers.totalNumber;
      });
    }

    public pageChanged(pageNumber: number) {
      this.pageNumber = pageNumber;
      this.getPaginatedJournalVouchers();
    }

    public convertDateObjectToDateString(date: Date): string {
      return moment(date).format("DD-MM-YYYY").toString();
    }

    public saveJournalVoucher() {
      if (this.journalVouchers.length == 0)
        this.notify.warn("No journal voucher data");
      else if (this.totalCredit != this.totalDebit)
        this.notify.warn("Total debit and credit must be same");
      else {
        this.journalVoucherService.saveVoucher(this.journalVouchers).then((response) => {
          if (response != undefined)
            this.addButtonClicked();
        });
      }
    }

    public addData() {
      this.journalVoucherOfAddModal = <IJournalVoucher>{};
      this.journalVoucherOfAddModal.serialNo = this.journalVouchers.length + 1;
      this.journalVoucherOfAddModal.balanceType = BalanceType.Dr;
    }

    //todo use accounting library
    public convertToCurrencyFormat(amount: number): any {
      return null;
    }

    public addDataToJournalTable() {
      this.journalVoucherOfAddModal.voucherNo = this.voucherNo;
      this.journalVoucherOfAddModal.currency = this.selectedCurrency;
      this.journalVoucherOfAddModal.currencyId = this.selectedCurrency.id.toString();
      this.journalVoucherOfAddModal.accountId = this.journalVoucherOfAddModal.account.id;
      this.journalVoucherOfAddModal.voucherId = JournalVoucherController.JOURNAL_VOUCHER_ID;
      this.journalVoucherOfAddModal.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
      this.journalVoucherOfAddModal.accountTransactionType = this.type;
      this.journalVoucherOfAddModal.foreignCurrency = this.journalVoucherOfAddModal.amount * this.journalVoucherOfAddModal.conversionFactor;
      this.journalVoucherOfAddModal.accountTransactionType = this.type;
      this.journalVouchers.push(this.journalVoucherOfAddModal);
      this.calculateTotalDebitAndCredit();
    }

    public calculateTotalDebitAndCredit() {
      this.totalCredit = 0;
      this.totalDebit = 0;
      for (var i = 0; i < this.journalVouchers.length; i++) {
        if (this.journalVouchers[i].balanceType == BalanceType.Dr)
          this.totalDebit += this.journalVouchers[i].amount;
        else
          this.totalCredit += this.journalVouchers[i].amount;
      }
    }

    public addButtonClicked() {
      this.showAddSection = true;
      this.totalCredit = 0;
      this.totalDebit = 0;
      this.journalVoucherService.getVoucherNumber().then((voucherNo: string) => this.voucherNo = voucherNo);
      this.getCurrencyConversions();
      this.getCurrencies();
      let currDate: Date = new Date();
      this.journalVouchers = [];
      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
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
        this.selectedCurrency = currencies[0];
      });
    }
  }

  UMS.controller("JournalVoucherController", JournalVoucherController);
}