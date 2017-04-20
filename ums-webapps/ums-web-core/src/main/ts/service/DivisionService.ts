module ums{

    export class DivisionService {


        public static $inject = ['HttpClient','$q','notify','$sce','$window','MessageFactory'];

        constructor(private bHttpClient: HttpClient,
                    private b$q: ng.IQService, private bNotify: Notify,
                    private b$sce: ng.ISCEService, private b$window: ng.IWindowService, private bMessageFactory: MessageFactory) {
        }
        public getDivisionList(): ng.IPromise<any> {
            var defer = this.b$q.defer();
            this.bHttpClient.get("division/all", 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

    }
    UMS.service("divisionService",DivisionService);
}