module ums {
  import IAccount = ums.IAccount;

  enum SubmissionType {
    save = "Save",
    post = "Post"
  }

  export class JournalVoucherController {

    public static $inject = ['$scope', '$q', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService', 'VoucherNumberControlService', 'SystemGroupMapService'];


    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private showAddSection: boolean;
    private type: AccountTransactionType;
    private voucherOfAddModal: IJournalVoucher;
    private detailVouchers: IJournalVoucher[];
    private existingVouchers: IJournalVoucher[];
    private accountListForAddModal: IAccount[];
    private customerAndVendorAccounts: IAccount[];
    private customerAccounts: IAccount[];
    private vendorAccounts: IAccount[];
    private customerAccountMapWithId: any;
    private studentAccountMapWithId: any;
    private vendorAccountMapWithId: any;
    private totalDebit: number;
    private totalCredit: number;
    private pageNumber: number;
    private itemsPerPage: number;
    private totalVoucherNumber: number;
    private searchVoucherNo: string;
    private postStatus: boolean;
    private accounting: any;
    static JOURNAL_VOUCHER_GROUP_FLAG = GroupFlag.NO;
    private JOURNAL_VOUCHER_ID: string = '1';
    private baseCurrency: ICurrency;
    private maximumTransaferableAmount: number;



    constructor($scope: ng.IScope,
                private $q: ng.IQService,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private journalVoucherService: JournalVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService,
                private voucherNumberControlService: VoucherNumberControlService, private systemGroupMapService: SystemGroupMapService) {
      this.initialize();
    }

    public initialize() {
      this.showAddSection = false;
      this.pageNumber = 1;

      this.itemsPerPage = 20;
      this.type = AccountTransactionType.SELLING;
      this.accountService.getExcludingBankAndCostTypeAccounts().then((accountListForAddModal: IAccount[]) => {
        console.log("Accounts")
        console.log(accountListForAddModal);
        this.accountListForAddModal = accountListForAddModal;
      });
      this.voucherNumberControlService.getAllByCurrentFinancialYear().then((voucherNumberControl: IVoucherNumberControl[]) => {
        this.maximumTransaferableAmount =
            Number(voucherNumberControl.filter((v: IVoucherNumberControl) => v.voucherId == this.JOURNAL_VOUCHER_ID)[0].voucherLimit);
      });
      this.getCurrencyConversions();
      this.getCurrencies();
      this.getPaginatedJournalVouchers();
    }




    public getPaginatedJournalVouchers() {
      this.journalVoucherService.getAllVouchersPaginated(this.itemsPerPage, this.pageNumber, this.searchVoucherNo).then((paginatedVouchers: IPaginatedVouchers) => {
        this.existingVouchers = paginatedVouchers.vouchers;
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

    public saveVoucher() {
      this.checkWhetherAnyAmountExceedTotalLimit().then((allow: boolean) => {
        if (allow)
          this.checkVoucherBeforeSaveOrUpdate(SubmissionType.save);
      });
    }

    public checkWhetherAnyAmountExceedTotalLimit(): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      let allow: boolean = true;
      for (let i = 0; i < this.detailVouchers.length; i++) {
        if (this.maximumTransaferableAmount != 0 && this.detailVouchers[i].amount > this.maximumTransaferableAmount) {
          this.notify.error("Total Limit Exceeds at Voucher Serial No : " + this.detailVouchers[i].serialNo);
          allow = false;
          break;
        }
      }
      defer.resolve(allow);
      return defer.promise;
    }

    public checkVoucherBeforeSaveOrUpdate(submissionType: SubmissionType) {
      if (this.detailVouchers.length == 0)
        this.notify.warn("No journal voucher data");
      else if (this.totalCredit != this.totalDebit)
        this.notify.warn("Total debit and credit must be same");
      else {
        if (submissionType == SubmissionType.save) {
          this.journalVoucherService.saveVoucher(this.detailVouchers).then((response) => {
            this.configureAddVoucherSection(response);
          });
        } else {
          this.journalVoucherService.postVoucher(this.detailVouchers).then((response) => {

            this.configureAddVoucherSection(response);
          });
        }

      }
    }

    public postVoucher() {
      this.checkWhetherAnyAmountExceedTotalLimit().then((allow: boolean) => {
        if (allow)
          this.checkVoucherBeforeSaveOrUpdate(SubmissionType.post);
      });
    }

    public addData() {
      this.voucherOfAddModal = <IJournalVoucher>{};
      this.voucherOfAddModal.serialNo = this.detailVouchers.length + 1;
      this.voucherOfAddModal.balanceType = BalanceType.Dr;
      this.getVendorAndCustomerAccounts();
    }

    //todo use accounting library
    public convertToCurrencyFormat(amount: number): any {
      return null;
    }

    public addDataToVoucherTable() {
      if (this.detailVouchers.filter((f: IJournalVoucher) => f.serialNo === this.voucherOfAddModal.serialNo).length === 0) {
        this.voucherOfAddModal.voucherNo = this.voucherNo;
        this.voucherOfAddModal.currency = this.selectedCurrency;
        this.voucherOfAddModal.currencyId = this.selectedCurrency.id.toString();
        this.voucherOfAddModal.accountId = this.voucherOfAddModal.account.id;
        this.voucherOfAddModal.voucherId = this.JOURNAL_VOUCHER_ID;
        this.voucherOfAddModal.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
        this.voucherOfAddModal.accountTransactionType = this.type;
        this.voucherOfAddModal.foreignCurrency = this.voucherOfAddModal.amount * this.voucherOfAddModal.conversionFactor;
        this.voucherOfAddModal.accountTransactionType = this.type;
        this.detailVouchers.push(this.voucherOfAddModal);
      }

      this.calculateTotalDebitAndCredit();
    }

    public calculateTotalDebitAndCredit() {
      this.totalCredit = 0;
      this.totalDebit = 0;
      for (var i = 0; i < this.detailVouchers.length; i++) {
        if (this.detailVouchers[i].balanceType === BalanceType.Dr)
          this.totalDebit += this.detailVouchers[i].amount;
        else
          this.totalCredit += this.detailVouchers[i].amount;
      }
    }

    public showListView() {
      console.log("in the show list view");
      this.showAddSection = false;
      this.getPaginatedJournalVouchers();

    }


    public print(){
        let voucher: IJournalVoucher=this.detailVouchers[0];
        this.journalVoucherService.generateVoucherReport(voucher.voucherNo, voucher.voucherDate);
    }

    public fetchDetails(journalVoucher: IJournalVoucher) {
      console.log("Selected currency");
      console.log(this.selectedCurrency);
      console.log("Fetch details journal voucher");
      console.log(journalVoucher);
      this.journalVoucherService.getVouchersByVoucherNoAndDate(journalVoucher.voucherNo, journalVoucher.postDate == null ? journalVoucher.modifiedDate : journalVoucher.postDate).then((vouchers: IJournalVoucher[]) => {
        console.log("Fetch vouchers");
        console.log(vouchers);
        this.configureAddVoucherSection(vouchers);
      });
    }

    public deleteVoucher(journalVoucher: IJournalVoucher) {
      if (journalVoucher.id === null)
        this.detailVouchers.splice(this.detailVouchers.indexOf(journalVoucher), 1);
      else {
        this.journalVoucherService.deleteVoucher(journalVoucher).then((response) => {
          if (response != undefined) {
            this.notify.success("Successfully Deleted");
            this.detailVouchers.splice(this.detailVouchers.indexOf(journalVoucher), 1);
          }
        });
      }
    }

    private configureAddVoucherSection(vouchers: IJournalVoucher[]) {
      console.log("In the configure add vocher section");
      console.log(vouchers);
      this.detailVouchers = vouchers;
      this.showAddSection = true;
      this.voucherNo = this.detailVouchers[0].voucherNo;
      console.log(vouchers[0].voucherNo);
      console.log("This voucher no: " + this.voucherNo);
      this.voucherDate = vouchers[0].postDate == null ? moment(new Date()).format("DD-MM-YYYY") : vouchers[0].postDate;
      this.postStatus = vouchers[0].postDate == null ? false : true;
      this.selectedCurrency = vouchers[0].currency;
      this.type = vouchers[0].accountTransactionType;
      this.calculateTotalDebitAndCredit();
    }

    public edit(voucher: IJournalVoucher) {
      this.voucherOfAddModal = voucher;
    }
    public addButtonClicked() {
      this.postStatus = false;
      this.showAddSection = true;
      this.totalCredit = 0;
      this.totalDebit = 0;
      this.type = AccountTransactionType.SELLING;
      this.voucherNo = "";
      let currDate: Date = new Date();
      this.detailVouchers = [];
      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
    }

    private getVendorAndCustomerAccounts() {
      this.accountService.getVendorAccounts().then((accountListForAddModal: IAccount[]) => {
        this.vendorAccounts = [];
        this.vendorAccountMapWithId = {};
        this.vendorAccounts = accountListForAddModal;
        this.vendorAccounts.forEach((v: IAccount) => this.vendorAccountMapWithId[v.id] = v);
      });
      this.customerAccounts = [];
      this.customerAccountMapWithId = {};

      this.accountService.getCustomerAccounts().then((accountListForAddModal: IAccount[]) => {
        accountListForAddModal.forEach((a: IAccount) => {
          this.customerAccounts.push(a);
          this.customerAccountMapWithId[a.id] = a;
        });
      });

      this.accountService.getStudentAccounts().then((accountListForAddModal: IAccount[]) => {
        this.studentAccountMapWithId = {};
        accountListForAddModal.forEach((a: IAccount) => {
          this.customerAccounts.push(a);
          this.customerAccountMapWithId[a.id] = a;
          this.studentAccountMapWithId[a.id] = a;
        });
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