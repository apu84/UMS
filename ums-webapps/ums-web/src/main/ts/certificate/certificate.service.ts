module ums {
  export class CertificateService {

    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getCertificateReport(feeCategoryId?: string, studentId?: string, semesterId?: number) {
      var contentType: string = UmsUtil.getFileContentType("pdf");
      var fileName = "certificate";

      this.httpClient.get("certificate/report?feeCategory=" + feeCategoryId + "&studentId=" + studentId + "&semesterId=" + semesterId, contentType,
          (data: any, etag: string) => {
            UmsUtil.writeFileContent(data, contentType, fileName);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }
  }

  UMS.service("CertificateService", CertificateService);
}