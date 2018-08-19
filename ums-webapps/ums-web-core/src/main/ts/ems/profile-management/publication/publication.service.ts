module ums {
    export class PublicationService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public savePublicationInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.publicationUrl, json, 'application/json')
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

        public saveBibPublicationInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.publicationUrl + "/bulk", json, 'application/json')
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

        public updatePublicationInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.publicationUrl, json, 'application/json')
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

        public deletePublicationInformation(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete(this.publicationUrl + "/" + id)
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

        public getPublicationInformationWithPagination(employeeId: string, status: string, pageNumber: number, itemPerPage: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/employeeId/" + employeeId + "/publicationStatus/" + status + "/pageNumber/" + pageNumber + "/ipp/" + itemPerPage, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }

        public getPublicationInformationViewWithPagination(employeeId: string, pageNumber: number, itemPerPage: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/employeeId/" + employeeId + "/pageNumber/" + pageNumber + "/ipp/" + itemPerPage, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }

        public getSpecificTeacherPublicationInformation(employeeId: string, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/" + employeeId + "/" + status, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public getPublicationInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }
    }

    UMS.service("publicationService", PublicationService);
}