module ums {
    export class CountryService {
        public static $inject = ['HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private HttpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public getAll(): ng.IPromise<any> {
            let url = "country/all";
            let defer = this.$q.defer();
            this.HttpClient.get(url, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                this.notify.error("Can not load country information. Please inform to IUMS");
                 console.error(response);
                });
            return defer.promise;
        }
    }

    UMS.service("countryService", CountryService);
}