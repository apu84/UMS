module ums {
  export interface PaymentStatus {
    id: string;
    transactionId: string;
    account: string;
    methodOfPayment: string;
    receiptNo: string;
    paymentDetails: string;
    amount: number;
    receivedOn: string;
    completedOn: string;
    status: string;
    lastModified;
  }

  export interface PaymentStatusFilter {
    id?: number;
    key?: string;
    value?: any;
    label?: string;
    labelValue?: any;
  }

  export interface PaymentStatusResponse {
    entries: PaymentStatus[];
    next: string;
    previous: string;
  }

  export class PaymentStatusService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getPaymentStatus(filters: PaymentStatusFilter[], url?: string): ng.IPromise<PaymentStatusResponse> {
      const defer: ng.IDeferred<PaymentStatusResponse> = this.$q.defer();
      this.httpClient.post(url ? `/ums-webservice-bank/${url}` : '/ums-webservice-bank/payment-status/paginated/filtered',
          filters ? {"entries": filters} : {},
          HttpClient.MIME_TYPE_JSON)
          .success((response: PaymentStatusResponse) => {
            defer.resolve(response);
          })
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public getFilters(): ng.IPromise<Filter[]> {
      const defer: ng.IDeferred<Filter[]> = this.$q.defer();
      this.httpClient.get('/ums-webservice-bank/payment-status/filters', HttpClient.MIME_TYPE_JSON, (filters: Filter[])=> {
        defer.resolve(filters);
      });
      return defer.promise;
    }

    public concludePayments(payments: PaymentStatus[]): ng.IPromise<boolean> {
      const defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.put(`/ums-webservice-bank/payment-status/conclude-payment`, {
            "entries": payments
          },
          HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }

    public rejectPayments(payments: PaymentStatus[]): ng.IPromise<boolean> {
      const defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.put(`/ums-webservice-bank/payment-status/reject-payment`, {
            "entries": payments
          },
          HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }
  }

  UMS.service('PaymentStatusService', PaymentStatusService);
}
