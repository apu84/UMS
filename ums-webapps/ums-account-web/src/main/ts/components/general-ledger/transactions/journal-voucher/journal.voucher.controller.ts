module ums {
  enum SubmissionType {
    save = "Save",
    post = "Post"
  }

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
    private poststatus: boolean;
    private accounting: any;
    static JOURNAL_VOUCHER_GROUP_FLAG = GroupFlag.NO;
    static JOURNAL_VOUCHER_ID = '1';
    private baseCurrency: ICurrency;



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

      this.itemsPerPage = 20;
      this.type = AccountTransactionType.SELLING;
      this.accountService.getAccountsByGroupFlag(JournalVoucherController.JOURNAL_VOUCHER_GROUP_FLAG).then((accounts: IAccount[]) => {
        console.log("Accounts")
        console.log(accounts);
        this.accounts = accounts;
      });
      this.getCurrencyConversions();
      this.getCurrencies();
      this.getPaginatedJournalVouchers();
    }


    public getPaginatedJournalVouchers() {
      this.journalVoucherService.getAllVouchersPaginated(this.itemsPerPage, this.pageNumber, this.searchVoucherNo).then((paginatedVouchers: IPaginatedVouchers) => {
        this.existingJournalVouchers = paginatedVouchers.vouchers;
        this.totalVoucherNumber = paginatedVouchers.totalNumber;
      });
    }

    public searchVoucher() {
      console.log("In the search voucher");
      console.log(this.searchVoucherNo)
      if (this.searchVoucherNo != null) {
        this.getPaginatedJournalVouchers();
      }
    }

    public formatCurrency(currency: number): any {
      return accounting.formatMoney(currency, this.selectedCurrency.notation + " ");
    }

    public formatBaseCurrency(currency: number): any {
      let baseCurrencyConversion = currency * (this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor);

      return accounting.formatMoney(baseCurrencyConversion, this.baseCurrency.notation + " ");
    }
    public pageChanged(pageNumber: number) {
      this.pageNumber = pageNumber;
      this.getPaginatedJournalVouchers();
    }

    public changeDateFormat(date: string) {
      return Utils.convertFromJacksonDate(date);
    }

    public saveJournalVoucher() {
      this.checkVoucherBeforeSaveOrUpdate(SubmissionType.save);
    }

    public checkVoucherBeforeSaveOrUpdate(submissionType: SubmissionType) {
      if (this.journalVouchers.length == 0)
        this.notify.warn("No journal voucher data");
      else if (this.totalCredit != this.totalDebit)
        this.notify.warn("Total debit and credit must be same");
      else {
        if (submissionType == SubmissionType.save) {
          this.journalVoucherService.saveVoucher(this.journalVouchers).then((response) => {
            this.configureAddVoucherSection(response);
          });
        } else {
          this.journalVoucherService.postVoucher(this.journalVouchers).then((response) => {

            this.configureAddVoucherSection(response);
          });
        }

      }
    }

    public postJournalVoucher() {
      this.checkVoucherBeforeSaveOrUpdate(SubmissionType.post);
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
      if (this.journalVouchers.filter((f: IJournalVoucher) => f.serialNo === this.journalVoucherOfAddModal.serialNo).length === 0) {
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
      }

      this.calculateTotalDebitAndCredit();
    }

    public calculateTotalDebitAndCredit() {
      this.totalCredit = 0;
      this.totalDebit = 0;
      for (var i = 0; i < this.journalVouchers.length; i++) {
        if (this.journalVouchers[i].balanceType === BalanceType.Dr)
          this.totalDebit += this.journalVouchers[i].amount;
        else
          this.totalCredit += this.journalVouchers[i].amount;
      }
    }

    public showListView() {
      console.log("in the show list view");
      this.showAddSection = false;
    }

    public fetchDetails(journalVoucher: IJournalVoucher) {
      this.journalVoucherService.getVouchersByVoucherNoAndDate(journalVoucher.voucherNo, journalVoucher.postDate == null ? journalVoucher.modifiedDate : journalVoucher.postDate).then((vouchers: IJournalVoucher[]) => {
        this.configureAddVoucherSection(vouchers);
      });
    }

    private configureAddVoucherSection(vouchers: IJournalVoucher[]) {
      console.log("In the configure add vocher section");
      console.log(vouchers);
      this.journalVouchers = vouchers;
      this.showAddSection = true;
      this.voucherNo = this.journalVouchers[0].voucherNo;
      console.log(vouchers[0].voucherNo);
      console.log("This voucher no: " + this.voucherNo);
      this.voucherDate = vouchers[0].postDate == null ? moment(new Date()).format("DD-MM-YYYY") : vouchers[0].postDate;
      this.poststatus = vouchers[0].postDate == null ? false : true;
      this.selectedCurrency = vouchers[0].currency;
      this.type = vouchers[0].accountTransactionType;
      this.calculateTotalDebitAndCredit();
    }

    public edit(voucher: IJournalVoucher) {
      this.journalVoucherOfAddModal = voucher;
    }
    public addButtonClicked() {
      this.poststatus = false;
      this.showAddSection = true;
      this.totalCredit = 0;
      this.totalDebit = 0;
      this.type = AccountTransactionType.SELLING;
      this.voucherNo = "";
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
        this.baseCurrency = currencies.filter((c: ICurrency) => c.currencyFlag == CurrencyFlag.BASE)[0];
      });
    }
  }

  UMS.controller("JournalVoucherController", JournalVoucherController);
}