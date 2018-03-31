module ums {
  export class BalanceSheetReportService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/report/balance-sheet";
    }

    public generateReport(asOnDate: string, fetchType: string, debtorLedgerFetchType: string): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      var contentType: string = UmsUtil.getFileContentType("pdf");
      this.httpClient.get(this.url + "/asOnDate/" + asOnDate + "/fetchType/" + fetchType + "/debtorLedgerFetchType/" + debtorLedgerFetchType, undefined, (data: any, etag: string) => {
            UmsUtil.writeFileContent(data, contentType, 'general-ledger-report.pdf');
          },
          (response: any) => {
            defer.resolve("success");
            console.error(response);
          }, 'arraybuffer');
      return defer.promise;
    }
  }

  UMS.service("BalanceSheetReportService", BalanceSheetReportService);
}