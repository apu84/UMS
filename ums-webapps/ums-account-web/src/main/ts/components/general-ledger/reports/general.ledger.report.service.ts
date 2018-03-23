module ums {
  export class GeneralLedgerReportService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/report/general-ledger-report";
    }

    public generateReport(accountId: string, groupCode: string, fromDate: string, toDate: string):ng.IPromise<string> {
      let defer:ng.IDeferred<string>=this.$q.defer();
      console.log("from date-" + fromDate);
      let jqueryThis: any = this;
      var contentType: string = UmsUtil.getFileContentType("pdf");
      this.httpClient.get(this.url + "/accountId/" + accountId + "/groupCode/" + groupCode + "/fromDate/" + fromDate + "/toDate/" + toDate, undefined, (data: any, etag: string) => {
            UmsUtil.writeFileContent(data, contentType, 'general-ledger-report.pdf');
          },
          (response: any) => {
            defer.resolve("success");
            console.error(response);
          }, 'arraybuffer');
      return defer.promise;  
    }
  }

  UMS.service("GeneralLedgerReportService", GeneralLedgerReportService);
}