module ums{
  export class AdmissionCertificateVerificationService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {
    }

    public getCandidateInformation(programType: string, semesterId: number, receiptId: string): ng.IPromise<any> {
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

    public getAllPreviousComments(semesterId: number, receiptId: string): ng.IPromise<any>{
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

    public saveAll(json:any):ng.IPromise<any>{
      var url = "academic/admission/student/saveAll";
      var defer = this.$q.defer();
      this.httpClient.put(url, json, 'application/json')
          .success(() => {
            defer.resolve("Saved");
          }).error((data)=>{
        defer.resolve("Error in saving");
      });
      return defer.promise;
    }

    public getCandidateList(programType:string,semesterId:number):ng.IPromise<any>{
      var url="academic/admission/allCandidates/programType/"+programType + "/semester/" +semesterId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudents:any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }
  }
  UMS.service('admissionCertificateVerificationService', AdmissionCertificateVerificationService);
}