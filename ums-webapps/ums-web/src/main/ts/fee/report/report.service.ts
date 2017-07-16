module ums {
  export class FeeReportService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private notify: Notify) {

    }

    public receipt(transactionId: string): void {
      this.httpClient.get(`fee-receipt/${transactionId}`, 'application/pdf',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = (e) => {
              this.saveAsFile(reader.result, `fee-receipt`);
            };
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

    private saveAsFile(url, fileName) {
      var a: any = document.createElement("a");
      document.body.appendChild(a);
      a.style = "display: none";
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
      $(a).remove();
    }
  }

  UMS.service('FeeReportService', FeeReportService);
}
