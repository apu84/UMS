module ums{

  import IPromise = ng.IPromise;

  export class AdmissionCertificateVerificationService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {
    }

    public getCandidatesList(programType: number, semesterId: number): ng.IPromise<any> {
      var url = "academic/admission/candidatesList/programType/" + programType + "/semester/" + semesterId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var admissionStudents: any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    public getCandidateInformation(programType: number, semesterId: number, receiptId: string): ng.IPromise<any> {
      var url = "academic/admission/semester/"+semesterId+"/programType/"+programType+"/receiptId/"+receiptId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var admissionStudents: any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    public getAllTypesOfCertificates(): ng.IPromise<any> {
      var url = "academic/admission/certificate/certificateLists";
      var defer = this.$q.defer();
      this.httpClient.get(url, HttpClient.MIME_TYPE_JSON,
          (json: any) => {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    public getSavedCertificates(semesterId: number, receiptId: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("academic/students/certificateHistory/savedCertificates/semesterId/" + semesterId + "/receiptId/" + receiptId,
          HttpClient.MIME_TYPE_JSON,
          (json: any) => {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    public getAllPreviousComments(semesterId: number, receiptId: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("academic/students/comment/savedComments/semesterId/" + semesterId + "/receiptId/" + receiptId, HttpClient.MIME_TYPE_JSON,
          (json: any) => {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.log(response);
          });
      return defer.promise;
    }

    public saveAll(json: any): ng.IPromise<any> {
      var url = "academic/admission/student/saveAll";
      var defer = this.$q.defer();
      this.httpClient.put(url, json, 'application/json')
          .success(() => {
            this.notify.success("Successfully Saved");
            defer.resolve("Saved");
          }).error((data) => {
        this.notify.success("Error in saving");
        defer.resolve("Error in saving");
      });
      return defer.promise;
    }

    public getUndertakenReport(programType: number, semesterId: number, receiptId: string): void {
      this.httpClient.get("academic/students/certificateHistory/underTaken/programType/"+ programType +"/semesterId/"+semesterId+"/receiptId/"+receiptId, 'application/pdf', (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }
  }

  UMS.service('admissionCertificateVerificationService', AdmissionCertificateVerificationService);
}