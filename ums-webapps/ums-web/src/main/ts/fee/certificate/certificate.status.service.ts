module ums {
  export interface CertificateStatus {
    id: string;
    transactionId: string;
    transactionStatus: string;
    studentId: string;
    studentName: string;
    semesterId: number;
    semesterName: string;
    certificateType: string;
    processedBy: string;
    processedOn: string;
    status: string;
    feeCategory: string;
    statusId: number;
    lastModified: string;
  }

  export interface CertificateStatusResponse {
    entries: CertificateStatus[];
    next: string;
    previous: string;
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

    /*public getPaginatedCertificateStatusByStatusAndType(status: number, feeType: number, pageNumber: number, itemsPerPage: number): ng.IPromise<CertificateStatus[]> {
      let defer: ng.IDeferred<CertificateStatus[]> = this.$q.defer();
      this.httpClient.get('certificate-status/status/' + (+status) + '/feeType/' + (+feeType) + '/pageNumber/' + (+pageNumber) + '/itemsPerPage/' + (+itemsPerPage), HttpClient.MIME_TYPE_JSON,
          (response: CertificateStatusResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public getCertificateStatusByStatusAndType(status: number, feeType: number): ng.IPromise<CertificateStatus[]> {
      let defer: ng.IDeferred<CertificateStatus[]> = this.$q.defer();
      this.httpClient.get("certificate-status/status/" + status + "/feeType/" + feeType, HttpClient.MIME_TYPE_JSON, (response: CertificateStatusResponse) => defer.resolve(response.entries));
      return defer.promise;
    }*/

    public listCertificateStatus(filters: SelectedFilter[], url?: string, feeType?: number, pageNumber?: number, itemsPerPage?: number): ng.IPromise<CertificateStatusResponse> {
      let defer: ng.IDeferred<CertificateStatusResponse> = this.$q.defer();
      var completeUrl = "";
      if (pageNumber > 0 && feeType >= 0)
        completeUrl = url + '?pageNumber=' + pageNumber + '&itemsPerPage=' + itemsPerPage + '&feeType=' + feeType;
      else if (pageNumber > 0)
        completeUrl = url + '?pageNumber=' + pageNumber + '&itemsPerPage=' + itemsPerPage;
      else if (feeType >= 0)
        completeUrl = url + '?feeType=' + feeType;
      else
        completeUrl = 'certificate-status/paginated';


      this.httpClient.post(completeUrl, filters ? {"entries": filters} : {},
          HttpClient.MIME_TYPE_JSON)
          .success((response: CertificateStatusResponse) => {
            defer.resolve(response);
          })
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public getFilters(): ng.IPromise<Filter[]> {
      let defer: ng.IDeferred<Filter[]> = this.$q.defer();
      this.httpClient.get('certificate-status/filters', HttpClient.MIME_TYPE_JSON, (filters: Filter[]) => {
        defer.resolve(filters);
      });
      return defer.promise;
    }

    public processCertificates(certificates: CertificateStatus[]): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      console.log("In the process certificates");
      console.log(certificates);
      this.httpClient.put(`certificate-status`, {
            "entries": certificates
          },
          HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }
  }

  UMS.service('CertificateStatusService', CertificateStatusService);
}
