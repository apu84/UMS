module ums {

  export interface IChequeRegister {
    id: string;
    companyId: string;
    accountTransactionId: string;
    chequeNo: string;
    chequeDate: string;
    status: string;
    realizationDate: string;
    statFlag: string;
    statUpFlag: string;
    modificationDate: string;
    modifiedBy: string;
    lastModified: string;
  }

  export class ChequeRegisterService {
    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/cheque";
    }

    public getChequeRegisterList(transactionIdList: string[]): ng.IPromise<IChequeRegister[]> {
      let defer: ng.IDeferred<IChequeRegister[]> = this.$q.defer();
      this.httpClient.post(this.url + "/transactionIdList", transactionIdList, HttpClient.MIME_TYPE_JSON)
          .success((response: any) => {
            console.log(response);
            defer.resolve(response);
          })
          .error((response) => {
            console.log(response);
            this.notify.error("Error in fetching cheque register list");
            defer.resolve(undefined);
          })

      return defer.promise;
    }
  }

  UMS.service("ChequeRegisterService", ChequeRegisterService);
}