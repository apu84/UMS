module ums {
  export class JournalVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService'];


    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;
    private showAddSection:boolean;
    private type:AccountTransactionType;
    private journalVoucherOfAddModal: IJournalVoucher;
    private journalVouchers: IJournalVoucher[];
    private accounts: IAccount[];

    static JOURNAL_VOUCHER_GROUP_FLAG=GroupFlag.NO;


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
      this.showAddSection=false;
      this.type=AccountTransactionType.SELLING;
      this.accountService.getAccountsByGroupFlag(JournalVoucherController.JOURNAL_VOUCHER_GROUP_FLAG).then((accounts:IAccount[])=>{
        console.log("Accounts")
        console.log(accounts);
        this.accounts = accounts;
      });
    }

    public addData(){
      this.journalVoucherOfAddModal=<IJournalVoucher>{};
      this.journalVoucherOfAddModal.serialNo=this.journalVouchers.length+1;
      this.journalVoucherOfAddModal.balanceType= BalanceType.Dr;
    }

    public addDataToJournalTable(){
      this.journalVoucherOfAddModal.voucherNo = this.voucherNo;
      this.journalVoucherOfAddModal.currency=this.selectedCurrency;
      this.journalVoucherOfAddModal.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
      this.journalVoucherOfAddModal.accountTransactionType=this.type;
      this.journalVouchers.push(this.journalVoucherOfAddModal);
    }

    public addButtonClicked() {
      this.showAddSection=true;
      this.journalVoucherService.getVoucherNumber().then((voucherNo: string) => this.voucherNo = voucherNo);
      this.getCurrencyConversions();
      this.getCurrencies();
      let currDate: Date = new Date();
      this.journalVouchers=[];
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