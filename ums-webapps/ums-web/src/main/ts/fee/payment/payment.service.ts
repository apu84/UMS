module ums {
  export interface Payment {
    id: string;
    amount: string;
    feeCategory: string;
    transactionId: string;
    appliedOn: string;
    status: string;
    feeType: string;
    feeTypeName: string;
    feeTypeDescription: string;
    lastModified: string;
    semesterName: string;
    studentId: string;
  }

  export interface PaymentGroup {
    [key: string] : Payment[];
  }

  interface PaymentResponse {
    entries: Payment[];
  }
  export class PaymentService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private notify: Notify) {

    }

    public getSemesterFeePaymentStatus(semesterId: number): ng.IPromise<Payment[]> {
      let defer: ng.IDeferred<Payment[]> = this.$q.defer();
      this.httpClient.get(`student-payment/semester-fee/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: PaymentResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public getCertificateFeePaymentStatus(): ng.IPromise<Payment[]> {
      let defer: ng.IDeferred<Payment[]> = this.$q.defer();
      this.httpClient.get(`student-payment/certificate-fee`, HttpClient.MIME_TYPE_JSON,
          (response: PaymentResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public getPaymentHistory(): ng.IPromise<PaymentGroup> {
      let defer: ng.IDeferred<PaymentGroup> = this.$q.defer();
      this.httpClient.get(`student-payment/all`, HttpClient.MIME_TYPE_JSON,
          (response: PaymentResponse) => {
            let paymentGroup: PaymentGroup = {};
            defer.resolve(response.entries.reduce((group: PaymentGroup, payment) => {
              if (!group[payment.transactionId]) {
                group[payment.transactionId] = []
              }
              group[payment.transactionId].push(payment);
              return group;
            }, paymentGroup));
          });
      return defer.promise;
    }
  }

  UMS.service("PaymentService", PaymentService);
}
