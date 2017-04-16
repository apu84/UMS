module ums{

  export class CountryService {


    public static $inject = ['HttpClient','$q','notify','$sce','$window','MessageFactory'];

    constructor(private bHttpClient: HttpClient,
                private b$q: ng.IQService, private bNotify: Notify,
                private b$sce: ng.ISCEService, private b$window: ng.IWindowService, private bMessageFactory: MessageFactory) {
    }
    public getCountryList(): ng.IPromise<any> {
      var defer = this.b$q.defer();
      this.bHttpClient.get("country/all", 'application/json',
          (json: any, etag: string) => {
            defer.resolve(json);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

  }
  UMS.service("countryService",CountryService);
}