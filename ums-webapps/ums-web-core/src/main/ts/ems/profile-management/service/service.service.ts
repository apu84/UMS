module ums{
    export class ServiceService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public saveServiceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.serviceUrl, json, 'application/json')
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

        public saveServiceDetailInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post("employee/serviceDetail", json, 'application/json')
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

        public updateServiceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.serviceUrl, json, 'application/json')
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

        public updateServiceDetailInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put("employee/serviceDetail", json, 'application/json')
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

        public deleteServiceInformation(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete(this.serviceUrl + "/" + id )
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

        public deleteServiceDetailInformation(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete("employee/serviceDetail" + "/" + id )
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

        public getServiceInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.serviceUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Service Information");
                });
            return defer.promise;
        }
    }

    UMS.service("serviceService", ServiceService);
}