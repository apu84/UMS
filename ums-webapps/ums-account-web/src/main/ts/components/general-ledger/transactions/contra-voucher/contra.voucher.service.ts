module ums {


  export interface IPaginatedContraVoucher {
    totalNumber: number;
    vouchers: IContraVoucher[];
  }


  export interface IContraVoucher {
    id: string;
    company: ICompany;
    companyId: string;
    divisionCode: string;
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
    accountTransactionType: AccountTransactionType;
    modifiedDate: string;
    modifiedBy: string;
    chequeNo: string;
    chequeDate: string;
    message: string;
    billNo: string;
    billDate: string;
    invoiceNo: string;
    invoiceDate: string;
    paidAmount: number;
    modifierName: string;
  }


 
  
  export class ContraVoucherService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger/transaction/contra-voucher";
    }

    public saveVoucher(vouchers: IContraVoucher[]): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.post(this.url + "/save", vouchers, HttpClient.MIME_TYPE_JSON)
          .success((response) => {
            this.notify.success("Payment Voucher Saved Successfully")
            defer.resolve(response);
          })
          .error((error) => {
            console.log(error);
            this.notify.error("Error in saving data");
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public postVoucher(vouchers: IContraVoucher[]): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.post(this.url + "/post", vouchers, HttpClient.MIME_TYPE_JSON)
          .success((response) => {
            this.notify.success("Payment Voucher Posted Successfully");
            defer.resolve(response);
          })
          .error((error) => {
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

    public getAllVouchersPaginated(itemPerPage: number, pageNumber: number, voucherNo?: string): ng.IPromise<IPaginatedContraVoucher> {
      let defer: ng.IDeferred<IPaginatedContraVoucher> = this.$q.defer();
      this.httpClient.get(this.url + "/paginated?itemPerPage=" + itemPerPage + "&pageNumber=" + pageNumber + "&voucherNo=" + voucherNo, HttpClient.MIME_TYPE_JSON,
          (respose: IPaginatedContraVoucher) => {
            console.log("Voucher response");
            console.log(respose);
            defer.resolve(respose)
          });
      return defer.promise;
    }

    public getVouchersByVoucherNoAndDate(voucherNo: string, date: string): ng.IPromise<IContraVoucher[]> {
      let defer: ng.IDeferred<IContraVoucher[]> = this.$q.defer();
      this.httpClient.get(this.url + "/voucher-no/" + voucherNo + "/date/" + date, HttpClient.MIME_TYPE_JSON,
          (response: IContraVoucher[]) => defer.resolve(response),
          (error: any) => console.error(error));
      return defer.promise;
    }
  }

  UMS.service("ContraVoucherService", ContraVoucherService);
}