module ums{
    export class PersonalService {
        public static $inject = ['HttpClient', '$q', 'notify'];
        private personalUrl: string = "employee/personal";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public updatePersonalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.personalUrl + "/update", json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }

        public getPersonalInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.personalUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in fetching Personal Information");
                });
            return defer.promise;
        }
    }

    UMS.service("personalService", PersonalService);
}