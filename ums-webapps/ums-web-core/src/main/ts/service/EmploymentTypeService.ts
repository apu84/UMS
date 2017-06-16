module ums{

    export class EmploymentTypeService {


        public static $inject = ['HttpClient','$q','notify','$sce','$window'];

        constructor(private bHttpClient: HttpClient,
                    private b$q: ng.IQService, private bNotify: Notify,
                    private b$sce: ng.ISCEService, private b$window: ng.IWindowService) {
        }
        public getAll(): ng.IPromise<any> {
            var defer = this.b$q.defer();
            this.bHttpClient.get("employmentType/all", 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

    }
    UMS.service("employmentTypeService",EmploymentTypeService);
}