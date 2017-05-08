module ums {
    import IHttpPromiseCallback = ng.IHttpPromiseCallback;
    export class ApprovePublicationService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public getPublicationInformation(): ng.IPromise<any> {
            var url= "registrar/employee/publication/getPublicationInformation";
            var defer = this.$q.defer();
            this.httpClient.get(url, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public getTeachersList(status: string): ng.IPromise<any>{
            const url="registrar/employee/publication/getTeachersList/" + status;
            let defer = this.$q.defer();
            this.httpClient.get(url, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallback<any>) => {
                console.log();
                });
            return defer.promise;
        }
    }

    UMS.service('approvePublicationService', ApprovePublicationService);
}