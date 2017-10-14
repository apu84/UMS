module ums {
  export class PaymentStatusService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getPaymentStatus(filters: PaymentStatusFilter[], url?: string): ng.IPromise<PaymentStatusResponse> {
      console.log("Payment filters");
      console.log(filters);
      const defer: ng.IDeferred<PaymentStatusResponse> = this.$q.defer();
      this.httpClient.post(url ? `${url}` : 'payment-status/paginated/filtered',
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
      this.httpClient.get('payment-status/filters', HttpClient.MIME_TYPE_JSON, (filters: Filter[]) => {
        defer.resolve(filters);
      });
      return defer.promise;
    }

    public concludePayments(payments: PaymentStatus[]): ng.IPromise<boolean> {
      const defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.put(`update-payment-status/conclude-payment`, {
            "entries": payments
          },
          HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }

    public rejectPayments(payments: PaymentStatus[]): ng.IPromise<boolean> {
      const defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.put(`update-payment-status/reject-payment`, {
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
