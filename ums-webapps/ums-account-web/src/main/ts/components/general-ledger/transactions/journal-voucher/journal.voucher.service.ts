module ums {

  export interface ITransactionResponse {
    voucherNo: string;
    message: string;
  }

  export enum AccountTransactionType{
    BUYING="B",
    SELLING="S"
  }

  export interface IJournalVoucher{
    id: string;
    company: ICompany;
    companyId: string;
    divisionCode:string;
    voucherNo: string;
    voucherDate: string;
    serialNo: number;
    account: IAccount;
    accountId: string;
    voucher: IVoucher;
    voucherId: string;
    amount: number;
    balanceType: BalanceType;
    narration: string;
    foreignCurrency: number;
    currency: ICurrency;
    currencyId: string;
    conversionFactor: number;
    projNo: string;
    statFlag: string;
    statUpFlag: string;
    receiptId: string;
    postDate: string;
    accountTransactionType:AccountTransactionType;
    modifiedDate: string;
    modifiedBy: string;
  }

  export class JournalVoucherService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger/transaction/journal-voucher";
    }

    public saveVoucher(vouchers: IJournalVoucher[]):ng.IPromise<any>{
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.post(this.url+"/save", vouchers, HttpClient.MIME_TYPE_JSON)
          .success((response)=>defer.resolve(response))
          .error((error)=>{
            console.log(error);
            this.notify.error("Error in saving data");
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public getVoucherNumber(): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(this.url + "/voucher-number", HttpClient.MIME_TYPE_JSON,
          (response: ITransactionResponse) => {
            if (response.message.length > 1) {
              this.notify.error("Error in fetching voucher number");
              defer.resolve(undefined)
            }
            else {
              defer.resolve(response.voucherNo);
            }

          });
      return defer.promise;
    }
  }

  UMS.service("JournalVoucherService", JournalVoucherService);
}