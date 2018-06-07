module ums {
  export class ReceiptVoucherController {
    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'ReceiptVoucherService', 'VoucherService', 'CurrencyService', 'CurrencyConversionService', 'AccountBalanceService', 'ChequeRegisterService', '$q', 'VoucherNumberControlService', 'SystemGroupMapService'];
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
    private pageNumber: number;
    private itemsPerPage: number;
    private totalVoucherNumber: number;
    private paymentVouchers: IReceiptVoucher[];
    private existingVouchers: IReceiptVoucher[];
    private paymentVoucherDetail: IReceiptVoucher;
    private detailVouchers: IReceiptVoucher[];
    private mainVoucher: IReceiptVoucher;
    static PAYMENT_VOUCHER_GROUP_FLAG = GroupFlag.YES;
    private RECEIPT_VOUCHER_ID: string = '7';
    private BANK_GROUP_CODE: string = '';
    private mainAccounts: IAccount[];
    private selectedPaymentAccount: IAccount;
    private selectedMainAccountCurrentBalance: number;
    private accountListForAddModal: IAccount[];
    private totalAmount: number;
    private voucherOfAddModal: IReceiptVoucher;
    private dateFormat: string;
    private searchVoucherNo: string;
    maximumTransaferableAmount: number;
    private customerAccounts: IAccount[];
    private vendorAccounts: IAccount[];
    private customerAccountMapWithId: any;
    private vendorAccountMapWithId: any;
    private studentAccountMapWithId: any;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private receiptVoucherService: ReceiptVoucherService,
                private voucherService: VoucherService,
                private currencyService: CurrencyService,
                private currencyConversionService: CurrencyConversionService,
                private accountBalanceService: AccountBalanceService,
                private chequeRegisterService: ChequeRegisterService,
                private $q: ng.IQService,
                private voucherNumberControlService: VoucherNumberControlService,
                private systemGroupMapService: SystemGroupMapService) {
      this.initialize();
    }

    public initialize() {
      this.pageNumber = 1;
      this.voucherNumberControlService.getAllByCurrentFinancialYear().then((voucherNumberControl: IVoucherNumberControl[]) => {
        this.maximumTransaferableAmount =
            Number(voucherNumberControl.filter((v: IVoucherNumberControl) => v.voucherId == this.RECEIPT_VOUCHER_ID)[0].voucherLimit);
      });
      this.itemsPerPage = 20;
      this.dateFormat = "dd-mm-yyyy";
      this.showAddSection = false;
      this.postStatus=false;
      this.getCurrencyConversions();
      this.getAccounts();
      this.getCurrencies();
      this.getPaginatedVouchers();
      this.assignBankGroupCode();
    }

      private assignBankGroupCode(){
          this.systemGroupMapService.getAll().then((systemGroupMapList: ISystemGroupMap[])=>{
              let systemBankId:string = '5'; //see app constants
              let bankSystemGroupMap: ISystemGroupMap = systemGroupMapList.filter((s:ISystemGroupMap)=>s.groupType==systemBankId)[0];
              this.BANK_GROUP_CODE = bankSystemGroupMap.group.groupCode;
          });
      }


      public print(){
          let voucher: IReceiptVoucher=this.detailVouchers[0];
          this.receiptVoucherService.generateVoucherReport(voucher.voucherNo, voucher.voucherDate);
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

    public searchVoucher() {
      console.log("In the search voucher");
      console.log(this.searchVoucherNo)
      this.searchVoucherNo=this.searchVoucherNo==''?undefined:this.searchVoucherNo;
      if (this.searchVoucherNo != null) {
        this.getPaginatedVouchers();
      }
      if(this.searchVoucherNo==undefined)
        this.getPaginatedVouchers();
    }

    public showListView() {
      this.initialize();
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


    public getPaginatedVouchers() {
      this.receiptVoucherService.getAllVouchersPaginated(this.itemsPerPage, this.pageNumber, this.searchVoucherNo).then((paginatedVouchers: IPaginatedReceiptVoucher) => {
        this.existingVouchers = paginatedVouchers.vouchers;
        this.totalVoucherNumber = paginatedVouchers.totalNumber;
      });
    }

    private getAccounts() {
      this.accountService.getBankAndCostTypeAccounts().then((accounts: IAccount[]) => {
        this.mainAccounts = accounts;
      });
      this.accountService.getExcludingBankAndCostTypeAccounts().then((accounts: IAccount[]) => {
        this.accountListForAddModal = accounts;
      });
    }

    public getAccountBalance() {
      this.mainVoucher.balanceType = BalanceType.Dr;
      this.accountBalanceService.getAccountBalance(this.mainVoucher.account.id).then((currentBalance: number) => {
        this.selectedMainAccountCurrentBalance = currentBalance;
        console.log("Current Balance");
        console.log(this.selectedMainAccountCurrentBalance);
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
      this.voucherNo = "";
      let currDate: Date = new Date();
      this.paymentVouchers = [];
      this.mainVoucher = <IReceiptVoucher>{};
      this.paymentVoucherDetail = <IReceiptVoucher>{};
      this.voucherDate = moment(currDate).format("DD-MM-YYYY");
      this.detailVouchers = [];
    }

    public addData() {
      this.voucherOfAddModal = <IReceiptVoucher>{};
      this.voucherOfAddModal.serialNo = this.detailVouchers.length + 1;
      this.voucherOfAddModal.balanceType = BalanceType.Cr;
    }

    public addDataToVoucherTable() {
      if (this.voucherOfAddModal.id==undefined) {
        this.voucherOfAddModal = this.addNecessaryAttributesToVoucher(this.voucherOfAddModal);
        this.detailVouchers.push(this.voucherOfAddModal);
      }
      else{
        this.voucherMapWithId[this.voucherOfAddModal.id]=this.voucherOfAddModal;
      }
      this.countTotalAmount();
    }

    private addNecessaryAttributesToVoucher(voucher: IReceiptVoucher): IReceiptVoucher {
      voucher.accountId = voucher.account.id;
      voucher.voucherNo = this.voucherNo;
      voucher.voucherId = this.RECEIPT_VOUCHER_ID;
      voucher.conversionFactor = this.currencyConversionMapWithCurrency[this.selectedCurrency.id].baseConversionFactor;
      voucher.foreignCurrency = voucher.amount != null ? voucher.amount * voucher.conversionFactor : this.selectedMainAccountCurrentBalance * voucher.conversionFactor;
      voucher.voucherDate = this.voucherDate;
      voucher.currencyId = this.selectedCurrency.id;
      return voucher;
    }

    public saveVoucher() {
      this.checkWhetherAnyAmountExceedTotalLimit().then((allowed: boolean) => {
        if (allowed) {
          if (this.mainVoucher == null)
            this.notify.error("Account Name is not selected");
          else {
            this.mainVoucher = this.addNecessaryAttributesToVoucher(this.mainVoucher);
            console.log("payment voucher after adding necessary fields");
            console.log(this.mainVoucher);
            //this.mainVoucher.amount = this.selectedMainAccountCurrentBalance + this.totalAmount;
            this.detailVouchers.push(this.mainVoucher);
            this.receiptVoucherService.saveVoucher(this.detailVouchers).then((vouchers: IReceiptVoucher[]) => {
              this.configureVouchers(vouchers);
            });
          }
        }
      });

    }

    public postVoucher() {

      this.checkWhetherAnyAmountExceedTotalLimit().then((allowed: boolean) => {
        if (allowed) {
          if (this.mainVoucher == null)
            this.notify.error("Account Name is not selected");
          else {
            this.mainVoucher = this.addNecessaryAttributesToVoucher(this.mainVoucher);
            this.mainVoucher.amount = this.totalAmount;
            console.log("Payment voucher amount");
            console.log(this.mainVoucher.amount);
            this.detailVouchers.push(this.mainVoucher);
            this.receiptVoucherService.postVoucher(this.detailVouchers).then((vouchers: IReceiptVoucher[]) => {
              this.configureVouchers(vouchers);
            });
          }
        }
      });

    }

    private configureVouchers(vouchers: IReceiptVoucher[]) {
      this.totalAmount = 0;
      this.voucherMapWithId = {};
      this.postStatus=vouchers[0].postDate!=null?true:false;
      this.voucherDate=vouchers[0].voucherDate;
      vouchers.forEach((v: IReceiptVoucher) => {
        this.voucherMapWithId[v.id] = v;
        v.voucherDate=v.voucherDate;
      });
      this.voucherDate = vouchers[0].voucherDate;
      this.extractMainAndDetailSectionFromVouchers(vouchers).then((updatedVouchers: IReceiptVoucher[]) => {
        console.log("Detailed vouchers");
        console.log(this.detailVouchers);
        this.assignChequeNumberToVouchers(vouchers);
      });
      this.voucherNo = vouchers[0].voucherNo;
    }


    public fetchDetails(paymentVoucher: IReceiptVoucher) {
      this.showAddSection = true;
      this.receiptVoucherService.getVouchersByVoucherNoAndDate(paymentVoucher.voucherNo, paymentVoucher.postDate == null ? paymentVoucher.modifiedDate : paymentVoucher.postDate).then((vouchers: IReceiptVoucher[]) => {
        console.log("details fetched----------->");
        console.log(this.detailVouchers);
        this.configureVouchers(vouchers);
      });
    }

    private extractMainAndDetailSectionFromVouchers(vouchers: IReceiptVoucher[]): ng.IPromise<IReceiptVoucher[]> {
      let defer: ng.IDeferred<IReceiptVoucher[]> = this.$q.defer();
      this.detailVouchers = [];
      this.voucherMapWithId = {};
      console.log("************");
      console.log(vouchers);
      vouchers.forEach((v: IReceiptVoucher) => {
        this.voucherMapWithId[v.id] = v;
        if (v.balanceType == BalanceType.Dr) {
          this.mainVoucher = v;
          this.getAccountBalance();
        }
        else {
          this.detailVouchers.push(v);
          this.countTotalAmount();
        }
      });
      defer.resolve(vouchers);

      return defer.promise;
    }

    public edit(voucher: IReceiptVoucher) {
      this.voucherOfAddModal = voucher;
    }

    public changeDateFormat(date: string) {
      return Utils.convertFromJacksonDate(date);
    }
    private assignChequeNumberToVouchers(vouchers: IReceiptVoucher[]) {
      let transactionIdList: string[] = [];
      vouchers.forEach((v: IReceiptVoucher) => transactionIdList.push(v.id));

      this.chequeRegisterService.getChequeRegisterList(transactionIdList).then((chequeList: IChequeRegister[]) => {
        console.log("cheque list");
        console.log(chequeList);
        chequeList.forEach((c: IChequeRegister) => {
          let voucher: IReceiptVoucher = this.voucherMapWithId[c.accountTransactionId];
          console.log("Cheque voucher");
          console.log(voucher);
          voucher.chequeNo = c.chequeNo;
          voucher.chequeDate = Utils.convertFromJacksonDate(c.chequeDate);
        });
      });
    }



    public countTotalAmount() {
      console.log("Detail vouchers");
      console.log(this.detailVouchers);
      this.totalAmount = 0;
      this.detailVouchers.forEach((v: IReceiptVoucher) => {
        this.totalAmount = this.totalAmount + v.amount;
        console.log("total amount");
        console.log(this.totalAmount);
      });
    }

  }

  UMS.controller("ReceiptVoucherController", ReceiptVoucherController);
}