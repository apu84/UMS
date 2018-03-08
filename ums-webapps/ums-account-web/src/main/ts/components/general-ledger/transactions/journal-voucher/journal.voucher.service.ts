module ums {

  export interface ITransactionResponse {
    voucherNo: string;
    message: string;
  }

  export enum AccountTransactionType {
    BUYING = "BUYING",
    SELLING = "SELLING"
  }

  export interface IPaginatedVouchers {
    totalNumber: number;
    vouchers: IJournalVoucher[];
  }

  export interface IJournalVoucher {
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
    message: string;
  }

  export class JournalVoucherService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger/transaction/journal-voucher";
    }


    public saveVoucher(vouchers: IJournalVoucher[]): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.post(this.url + "/save", vouchers, HttpClient.MIME_TYPE_JSON)
          .success((response: IJournalVoucher[]) => {
            this.notify.success("Journal Voucher Data Saved Successfully")
            console.log("Successss");
            console.log(response);
            defer.resolve(response);
          })
          .error((error) => {
            console.log(error);
            this.notify.error("Error in saving data");
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public postVoucher(vouchers: IJournalVoucher[]): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.post(this.url + "/post", vouchers, HttpClient.MIME_TYPE_JSON)
          .success((response: IJournalVoucher[]) => {
            this.notify.success("Journal Voucher Posted Successfully");
            console.log("posting");
            defer.resolve(response);
          })
          .error((error) => {
            console.log(error);
            this.notify.error("Error in saving data");
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public deleteVoucher(voucher: IJournalVoucher): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      this.httpClient.put(this.url + "/delete", voucher, HttpClient.MIME_TYPE_JSON)
          .success((response) => defer.resolve(response))
          .error((response) => defer.resolve(response));
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

    public getAllVouchersPaginated(itemPerPage: number, pageNumber: number, voucherNo?: string): ng.IPromise<IPaginatedVouchers> {
      let defer: ng.IDeferred<IPaginatedVouchers> = this.$q.defer();
      this.httpClient.get(this.url + "/paginated?itemPerPage=" + itemPerPage + "&pageNumber=" + pageNumber + "&voucherNo=" + voucherNo, HttpClient.MIME_TYPE_JSON,
          (respose: IPaginatedVouchers) => {
            console.log("Voucher response");
            console.log(respose);
            defer.resolve(respose)
          });
      return defer.promise;
    }

    public getVouchersByVoucherNoAndDate(voucherNo: string, date: string): ng.IPromise<IJournalVoucher[]> {
      let defer: ng.IDeferred<IJournalVoucher[]> = this.$q.defer();
      this.httpClient.get(this.url + "/voucher-no/" + voucherNo + "/date/" + date, HttpClient.MIME_TYPE_JSON,
          (response: IJournalVoucher[]) => defer.resolve(response),
          (error: any) => console.error(error));
      return defer.promise;
    }
  }

  UMS.service("JournalVoucherService", JournalVoucherService);
}