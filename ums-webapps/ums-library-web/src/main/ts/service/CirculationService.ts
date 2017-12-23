module ums {

    export class CirculationService {
        public static $inject = ['libConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'MessageFactory'];

        constructor(private libConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService, private messageFactory: MessageFactory) {
        }

        public fetchPatron(id: string): ng.IPromise<any> {
            var defer = this.$q.defer();
            this.httpClient.get("record/" + id, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
    }

    UMS.service("circulationService", CirculationService);
}

