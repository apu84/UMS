module ums{
    export class AcademicService {
        public static $inject = ['HttpClient', '$q', 'notify'];
        private academicUrl: string = "employee/academic";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public saveNewDegreeTitle(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post("degreeTitles", json, 'application/json')
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

        public saveAcademicInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.academicUrl, json, 'application/json')
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

        public updateAcademicInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.academicUrl, json, 'application/json')
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

        public deleteAcademicInformation(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete(this.academicUrl + "/" + id )
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

        public getAcademicInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.academicUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Academic Information");
                });
            return defer.promise;
        }
    }

    UMS.service("academicService", AcademicService);
}