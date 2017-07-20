module ums {
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
  }

  UMS.service('PaymentStatusService', PaymentStatusService);
}
