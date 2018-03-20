module ums {
  export class GeneralLedgerReportService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/general-ledger-report";
    }

    public generateReport(accountId: string, groupId: string, fromDate: string, toDate: string) {
      this.url = this.url = "?accountId=" + accountId + "&groupId=" + groupId + "&fromDate=" + fromDate + "&toDate=" + toDate;
      var contentType: string = UmsUtil.getFileContentType("pdf");
      this.httpClient.get(this.url, undefined, (data: any, etag: string) => {
            UmsUtil.writeFileContent(data, contentType, 'general-ledger-report.pdf');
          },
          (response: any) => {
            console.error(response);
          }, 'arraybuffer');
    }
  }

  UMS.service("GeneralLedgerReportService", GeneralLedgerReportService);
}