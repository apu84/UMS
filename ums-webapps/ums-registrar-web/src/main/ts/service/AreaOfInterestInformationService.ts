module ums{
    export class AreaOfInterestInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        url:string = "employee/areaOfInterest";

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public saveAreaOfInterestInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.url+"/saveAreaOfInterestInformation", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                }).error((data) => {
                this.notify.error("Error in saving");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public getAreaOfInterestInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getAreaOfInterestInformation/"+employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }
    }

    UMS.service("areaOfInterestInformationService", AreaOfInterestInformationService);
}