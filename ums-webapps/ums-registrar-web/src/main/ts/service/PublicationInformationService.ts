module ums{
    export class PublicationInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public savePublicationInformation(json: any): ng.IPromise<any> {
            var url = "registrar/employee/publication/savePublicationInformation";
            var defer = this.$q.defer();
            this.httpClient.post(url, json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                }).error((data) => {
                this.notify.error("Error in saving");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public getPublicationInformation(): ng.IPromise<any> {
            console.log("i am here " + "here too");
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

        public getSpecificTeacherPublicationInformation(employeeId: string, status: string): ng.IPromise<any> {
            var url= "registrar/employee/publication/getPublicationInformation/" + employeeId + "/" + status;
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
    }

    UMS.service("publicationInformationService", PublicationInformationService);
}