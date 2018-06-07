module ums {

  export interface IPaginatedPaymentVoucher {
    totalNumber: number;
    vouchers: IPaymentVoucher[];
  }


  export interface IPaymentVoucher {
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


  export interface IPaginatedPaymentVoucher {
    totalNumber: number;
    vouchers: IPaymentVoucher[];
  }
  
  export class PaymentVoucherService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger/transaction/payment-voucher";
    }

    public saveVoucher(vouchers: IPaymentVoucher[]): ng.IPromise<any> {
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

    public postVoucher(vouchers: IPaymentVoucher[]): ng.IPromise<any> {
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

    public getAllVouchersPaginated(itemPerPage: number, pageNumber: number, voucherNo?: string): ng.IPromise<IPaginatedPaymentVoucher> {
      let defer: ng.IDeferred<IPaginatedPaymentVoucher> = this.$q.defer();
      this.httpClient.get(this.url + "/paginated?itemPerPage=" + itemPerPage + "&pageNumber=" + pageNumber + "&voucherNo=" + voucherNo, HttpClient.MIME_TYPE_JSON,
          (respose: IPaginatedPaymentVoucher) => {
            console.log("Voucher response");
            console.log(respose);
            defer.resolve(respose)
          });
      return defer.promise;
    }

    public getVouchersByVoucherNoAndDate(voucherNo: string, date: string): ng.IPromise<IPaymentVoucher[]> {
      let defer: ng.IDeferred<IPaymentVoucher[]> = this.$q.defer();
      this.httpClient.get(this.url + "/voucher-no/" + voucherNo + "/date/" + date, HttpClient.MIME_TYPE_JSON,
          (response: IPaymentVoucher[]) => defer.resolve(response),
          (error: any) => console.error(error));
      return defer.promise;
    }

      public generateVoucherReport(voucherNo: string, voucherDate: string): ng.IPromise<string> {
          let defer: ng.IDeferred<string> = this.$q.defer();
          var contentType: string = UmsUtil.getFileContentType("pdf");
          this.httpClient.get(this.url + "/paymentVoucherReport/voucherNo/"+voucherNo+"/voucherDate/"+voucherDate, undefined, (data: any, etag: string) => {
                  UmsUtil.writeFileContent(data, contentType, 'Payment Voucher ('+voucherDate +').pdf');
              },
              (response: any) => {
                  defer.resolve("success");
                  console.error(response);
              }, 'arraybuffer');
          return defer.promise;
      }

  }


  UMS.service("PaymentVoucherService", PaymentVoucherService);
}