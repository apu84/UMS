module ums{
    export class ThanaService {
        public static $inject = ['HttpClient','$q','notify','$sce','$window','MessageFactory'];
        constructor(private HttpClient: HttpClient,
                    private $q: ng.IQService, private Notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService, private MessageFactory: MessageFactory) {
        }

        public getThanaList(): ng.IPromise<any> {
            let url = "thana/all";
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
    UMS.service("thanaService",ThanaService);
}