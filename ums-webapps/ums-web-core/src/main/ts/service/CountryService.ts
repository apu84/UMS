module ums{
  export class CountryService {
    public static $inject = ['HttpClient','$q','notify','$sce','$window'];
    constructor(private HttpClient: HttpClient,
                private $q: ng.IQService, private Notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {
    }

    public getCountryList(): ng.IPromise<any> {
        let url = "country/all";
      let defer = this.$q.defer();
      this.HttpClient.get(url, 'application/json',
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