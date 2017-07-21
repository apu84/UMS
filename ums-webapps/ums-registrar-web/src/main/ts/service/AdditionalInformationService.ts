module ums{
    export class AdditionalInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];
        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public saveAdditionalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let url: string = "employee/additional";
            this.httpClient.post(url+"/saveAdditionalInformation", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                }).error((data) => {
                this.notify.error("Error in saving");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public getAdditionalInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let url: string = "employee/additional";
            this.httpClient.get(url+"/getAdditionalInformation/employeeId/"+ employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                   defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public saveAreaOfInterestInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let url: string = "employee/areaOfInterest";
            this.httpClient.post(url+"/saveAreaOfInterestInformation", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                }).error((data) => {
                this.notify.error("Error in saving");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public getAreaOfInterest(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let url: string = "employee/areaOfInterest";
            this.httpClient.get(url+"/getgetAreaOfInterestInformation/employeeId/"+ employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }
    }

    UMS.service("additionalInformationService", AdditionalInformationService);
}