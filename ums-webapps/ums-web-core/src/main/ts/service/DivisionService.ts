module ums{
    export class DivisionService {
        public static $inject = ['HttpClient','$q','notify','$sce','$window'];
        constructor(private HttpClient: HttpClient,
                    private $q: ng.IQService, private Notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public getAll(): ng.IPromise<any> {
            let url = "division/all";
            let defer = this.$q.defer();
            this.HttpClient.get(url, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
    }
    UMS.service("divisionService",DivisionService);
}