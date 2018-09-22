module ums {

    export class RecordLogService {
        public static $inject = ['libConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private libConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }


        public getLog(pModifiedDate: string, pModifiedBy: string, pMfn: string): ng.IPromise<any>{
            let defer = this.$q.defer();
            let resourceUrl="record/log/filter/query?modifiedDate=" + pModifiedDate + "&modifiedBy=" + pModifiedBy + "&mfn=" + pMfn;

            this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
    }

    UMS.service("recordLogService", RecordLogService);
}