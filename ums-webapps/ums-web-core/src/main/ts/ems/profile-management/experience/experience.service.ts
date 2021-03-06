module ums{
    export class ExperienceService {
        public static $inject = ['HttpClient', '$q', 'notify'];
        private experienceUrl: string = "employee/experience";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public saveExperienceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.experienceUrl, json, 'application/json')
                .success((data: any) => {
                    this.notify.success("Successfully Saved");
                    defer.resolve(data);
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.reject(data);
                });
            return defer.promise;
        }

        public updateExperienceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.experienceUrl, json, 'application/json')
                .success((data: any) => {
                    this.notify.success("Successfully Updated");
                    defer.resolve(data);
                })
                .error((reason: any) => {
                    this.notify.error("Error in Updating");
                    defer.reject(reason);
                });
            return defer.promise;
        }

        public deleteExperienceInformation(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete(this.experienceUrl + "/" + id )
                .success(() => {
                    this.notify.success("Delete Successful");
                    defer.resolve("Delete Successful");
                })
                .error((reason: any) => {
                    this.notify.error("Error in Saving");
                    defer.reject(reason);
                });
            return defer.promise;
        }

        public getExperienceInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.experienceUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Experience Information");
                });
            return defer.promise;
        }

    }

    UMS.service("experienceService", ExperienceService);
}