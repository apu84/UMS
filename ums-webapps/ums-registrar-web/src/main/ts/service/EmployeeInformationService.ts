module ums{
    export class EmployeeInformationService{
        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public saveEmployeeInformation(json: any): ng.IPromise<any> {
            var url = "registrar/employee/saveEmployeeInformation";
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


        public getEmployeeInformation(employeeId: number): ng.IPromise<any> {
            var defer = this.$q.defer();
            this.httpClient.get("" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public updateEmployeeInformation(employeeId: number, json:any): ng.IPromise<any>{
            var url = "" + employeeId;
            var defer = this.$q.defer();
            this.httpClient.put(url, json, 'application/json')
                .success(() => {
                    this.notify.success("Updated");
                    defer.resolve("Updated");
                }).error((data) => {
                this.notify.error("Error");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public deleteEmployeeInformation(): ng.IPromise<any>{
            var url = "";
            var defer = this.$q.defer();
            this.httpClient.delete(url)
                .success(() =>{
                this.notify.success("Deleted");
                defer.resolve("Deleted");
                }).error(()=> {
                this.notify.error("Error");
                defer.resolve("Error");
            });
            return defer.promise;
        }
    }

    UMS.service('employeeInformationService', EmployeeInformationService);
}