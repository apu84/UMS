module ums {

  export interface IReceipt {
    id: string;
    name: string;
    shortName: string;
  }

  export class ReceiptService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/receipt";
    }

    public getAllReceipts(): ng.IPromise<IReceipt[]> {
      let defer: ng.IDeferred<IReceipt[]> = this.$q.defer();
      this.httpClient.get(this.url + "/all", HttpClient.MIME_TYPE_JSON,
          (response: IReceipt[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }


  }

  UMS.service("ReceiptService", ReceiptService);
}