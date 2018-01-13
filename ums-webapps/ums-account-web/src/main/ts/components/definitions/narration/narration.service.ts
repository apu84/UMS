module ums {
  export interface INarration {
    id: string;
    voucher: IVoucher;
    voucherId: string;
    statFlag: string;
    statUpFlag: string;
    modifiedDate: string;
    modifiedBy: string;
    narration: string;
  }

  export class NarrationService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    private narrationURL: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.narrationURL = "account/definition/account";
    }

    public getAll(): ng.IPromise<INarration[]> {
      let defer: ng.IDeferred<INarration[]> = this.$q.defer();
      this.httpClient.get(this.narrationURL + "/all", HttpClient.MIME_TYPE_JSON,
          ((response: INarration[]) => defer.resolve(response)),
          ((error: any) => {
            console.error(error);
            this.notify.error("Error in fetching narration data");
            defer.resolve(undefined);
          }));
      return defer.promise;
    }

    public save(narrations: INarration[]): ng.IPromise<INarration[]> {
      let defer: ng.IDeferred<INarration[]> = this.$q.defer();
      this.httpClient.post(this.narrationURL + "/save", narrations, HttpClient.MIME_TYPE_JSON)
          .success((response: INarration[]) => {
            this.notify.success("Successfully saved data");
            defer.resolve(response);
          })
          .error((error) => {
            console.error(error);
            this.notify.error("Error in saving data");
            defer.resolve(undefined);
          });
      return defer.promise;
    }
  }

  UMS.service("NarrationService", NarrationService);
}