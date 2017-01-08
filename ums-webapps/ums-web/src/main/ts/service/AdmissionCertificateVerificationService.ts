module ums{
  export class AdmissionCertificateVerificationService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public getNewStudentInfo(semesterId: number, receiptId: string): ng.IPromise<any> {
      var url = "academic/admission/semesterId/" + semesterId + "/receiptId/" + receiptId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var admissionStudents: any = json.entries;
            console.log(json);
            console.log(json.entries);
            defer.resolve(admissionStudents);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    public getCertificateLists(): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("academic/admission/certificateLists",
          HttpClient.MIME_TYPE_JSON,
          (json: any) => {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
  }
  UMS.service('admissionCertificateVerificationService', AdmissionCertificateVerificationService);
}