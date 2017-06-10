module ums {
  export interface SemesterFee {
    name: string;
    amount: string;
  }

  interface SemesterFeeResponse {
    entries: SemesterFee[];
  }

  export class SemesterFeeService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private notify: Notify) {

    }

    public getSemesterFeeStatus(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(`semester-fee/status/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: string) => defer.resolve(response));
      return defer.promise;
    }

    public getSemesterInstallmentStatus(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(`semester-fee/semester-installment-status/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: string) => defer.resolve(response));
      return defer.promise;
    }

    public installmentAvailable(semesterId: number): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.get(`semester-fee/installment-available/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: boolean) => defer.resolve(response));
      return defer.promise;
    }

    public getInstallmentStatus(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(`semester-fee/installment-status/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: string) => defer.resolve(response));
      return defer.promise;
    }

    public getAdmissionStatus(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get(`semester-fee/admission-status/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: string) => defer.resolve(response));
      return defer.promise;
    }

    public withInAdmissionSlot(semesterId: number): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.get(`semester-fee/within-admission-slot/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: boolean) => defer.resolve(response));
      return defer.promise;
    }

    public withInFirstInstallmentSlot(semesterId: number): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.get(`semester-fee/within-first-installment-slot/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: boolean) => defer.resolve(response));
      return defer.promise;
    }

    public withInSecondInstallmentSlot(semesterId: number): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.get(`semester-fee/within-second-installment-slot/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: boolean) => defer.resolve(response));
      return defer.promise;
    }

    public fee(semesterId: number): ng.IPromise<SemesterFee[]> {
      let defer: ng.IDeferred<SemesterFee[]> = this.$q.defer();
      this.httpClient.get(`semester-fee/fee/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: SemesterFeeResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public firstInstallment(semesterId: number): ng.IPromise<SemesterFee[]> {
      let defer: ng.IDeferred<SemesterFee[]> = this.$q.defer();
      this.httpClient.get(`semester-fee/first-installment/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: SemesterFeeResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public secondInstallment(semesterId: number): ng.IPromise<SemesterFee[]> {
      let defer: ng.IDeferred<SemesterFee[]> = this.$q.defer();
      this.httpClient.get(`semester-fee/second-installment/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: SemesterFeeResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public payFee(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.post(`pay/${semesterId}`, {}, HttpClient.MIME_TYPE_JSON)
          .success((response: string) => defer.resolve(response))
          .error((data: string) => {
            this.notify.error(data);
            defer.resolve(null);
          });
      return defer.promise;
    }

    public payFirstInstallment(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.post(`pay/first-installment/${semesterId}`, {}, HttpClient.MIME_TYPE_JSON)
          .success((response: string) => defer.resolve(response))
          .error((data: string) => {
            this.notify.error(data);
            defer.resolve(null);
          });
      return defer.promise;
    }

    public paySecondInstallment(semesterId: number): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.post(`pay/second-installment/${semesterId}`, {}, HttpClient.MIME_TYPE_JSON)
          .success((response: string) => defer.resolve(response))
          .error((data: string) => {
            this.notify.error(data);
            defer.resolve(null);
          });
      return defer.promise;
    }
  }

  UMS.service("SemesterFeeService", SemesterFeeResponse);
}
