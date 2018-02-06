module ums {
  export class JournalVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'JournalVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService'];


    private voucherNo: string;
    private voucherDate: string;
    private currencies: ICurrency[];
    private selectedCurrency: ICurrency;
    private currencyConversions: ICurrencyConversion[];
    private currencyConversionMapWithCurrency: any;

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


    }

    public addModalClicked() {
      this.journalVoucherService.getVoucherNumber().then((voucherNo: string) => this.voucherNo = voucherNo);
      this.getCurrencyConversions();
      this.getCurrencies();
      let currDate: Date = new Date();
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