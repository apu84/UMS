module ums{

    export class DistrictService {


        public static $inject = ['HttpClient','$q','notify','$sce','$window','MessageFactory'];

        constructor(private bHttpClient: HttpClient,
                    private b$q: ng.IQService, private bNotify: Notify,
                    private b$sce: ng.ISCEService, private b$window: ng.IWindowService, private bMessageFactory: MessageFactory) {
        }
        public getDistrictList(): ng.IPromise<any> {
            var url = "district/all"
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
    UMS.service("districtService",DistrictService);
}