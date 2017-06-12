module ums {
  export interface CertificateStatus {
    transactionId: string;
    transactionStatus: string;
    studentId: string;
    studentName: string;
    certificateType: string;
    processedBy: string;
    processedOn: string;
    status: string;
    lastModified: string;
  }

  interface CertificateStatusResponse {
    entries: CertificateStatus[];
  }

  export class CertificateStatusService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getCertificateStatus(): ng.IPromise<CertificateStatus[]> {
      let defer: ng.IDeferred<CertificateStatus[]> = this.$q.defer();
      this.httpClient.get('certificate-status', HttpClient.MIME_TYPE_JSON,
          (response: CertificateStatusResponse) => defer.resolve(response.entries));
      return defer.promise;
    }
  }

  UMS.service('CertificateService', CertificateStatusService);
}