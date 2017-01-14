module ums{
  export class AdmissionCertificateVerificationService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public getNewStudentInfo(programType: string, semesterId: number, receiptId: string): ng.IPromise<any> {
      var url = "academic/admission/programType/" + programType +"/semesterId/" + semesterId + "/receiptId/" + receiptId;
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

    public getCertificateLists(): ng.IPromise<any> {
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

    public setVerificationStatus(json:any):ng.IPromise<any>{
      var url = "academic/admission/verificationStatus"
      var defer = this.$q.defer();
      this.httpClient.put(url, json, 'application/json')
          .success(()=> {
            defer.resolve("Verification Status Set");
          }).error((data)=>{
        defer.resolve("Error In Verification Status");
      });
      return defer.promise;
    }

    public saveCertificates(json:any):ng.IPromise<any>{
      var url = "academic/students/certificateHistory/saveCertificates";
      var defer = this.$q.defer();
      this.httpClient.post(url, json, 'application/json')
          .success(()=> {
            defer.resolve("Successfully Saved the certificates");
          }).error((data)=>{
        defer.resolve("Error in Saving Certificates");
      });
      return defer.promise;
    }

    public saveComments(json:any):ng.IPromise<any>{
      var url = "academic/students/comment/comments";
      var defer = this.$q.defer();
      this.httpClient.post(url, json, 'application/json')
          .success(()=> {
            defer.resolve("Successfully Saved the comments");
          }).error((data)=>{
        defer.resolve("Error in saving Comments");
      });
      return defer.promise;
    }

    public getSavedCertificateLists(semesterId: number, receiptId: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("academic/students/certificateHistory/savedCertificates/semesterId/" + semesterId + "/receiptId/"+ receiptId,
          HttpClient.MIME_TYPE_JSON,
          (json: any)=> {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=> {
            console.error(response);
          });

      return defer.promise;
    }

    public getSavedComments(semesterId: number, receiptId: string): ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get("academic/students/comment/savedComments/semesterId/"+ semesterId + "/receiptId/"+ receiptId, HttpClient.MIME_TYPE_JSON,
          (json:any)=>{
        defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=>{
        console.log(response);
          });
      return defer.promise;
    }

  }
  UMS.service('admissionCertificateVerificationService', AdmissionCertificateVerificationService);
}