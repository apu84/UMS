module ums{
    export class EmployeeServiceInformationService{
        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public saveServiceInformation(json: any): ng.IPromise<any> {
            console.log("I am in saveServiceInformation() service.ts file");
            var url = "registrar/employee/saveServiceInformation";
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

    }

    UMS.service("employeeServiceInformationService", EmployeeServiceInformationService);
}