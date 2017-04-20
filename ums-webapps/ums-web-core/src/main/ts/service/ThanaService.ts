module ums{

    export class ThanaService {


        public static $inject = ['HttpClient','$q','notify','$sce','$window','MessageFactory'];

        constructor(private bHttpClient: HttpClient,
                    private b$q: ng.IQService, private bNotify: Notify,
                    private b$sce: ng.ISCEService, private b$window: ng.IWindowService, private bMessageFactory: MessageFactory) {
        }
        public getThanaList(): ng.IPromise<any> {
            var url = "thana/all";
            var defer = this.b$q.defer();
            this.bHttpClient.get(url, 'application/json',
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