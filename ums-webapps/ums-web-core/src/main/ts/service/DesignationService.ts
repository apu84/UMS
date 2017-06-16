module ums{

    export class DesignationService {


        public static $inject = ['HttpClient','$q','notify','$sce','$window'];

        constructor(private bHttpClient: HttpClient,
                    private b$q: ng.IQService, private bNotify: Notify,
                    private b$sce: ng.ISCEService, private b$window: ng.IWindowService) {
        }
        public getAll(): ng.IPromise<any> {
            var defer = this.b$q.defer();
            this.bHttpClient.get("designation/all", 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

    }
    UMS.service("designationService",DesignationService);
}