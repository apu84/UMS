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
  }

  UMS.service("PaymentService", PaymentService);
}
