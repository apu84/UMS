module ums{
    export class ExperienceInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public saveExperienceInformation(json: any): ng.IPromise<any> {
            var url = "registrar/employee/experience/saveExperienceInformation";
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

        public getExperienceInformation(): ng.IPromise<any> {
            var url= "registrar/employee/experience/getExperienceInformation";
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

    UMS.service("experienceInformationService", ExperienceInformationService);
}