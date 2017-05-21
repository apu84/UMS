module ums{
    export class PersonalInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public savePersonalInformation(json: any): ng.IPromise<any> {
            var url = "registrar/employee/personal/savePersonalInformation";
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

        public updatePersonalInformation(json: any): ng.IPromise<any>{
            const url = "";
            let defer = this.$q.defer();
            this.httpClient.put(url, json, HttpClient.MIME_TYPE_JSON)
                .success(()=> {
                    this.notify.success("Update Successful");
                    defer.resolve();
                }).error((data) =>{
                this.notify.error("Error in updating");
            });

            return defer.promise;
        }

        public getPersonalInformation(): ng.IPromise<any> {
            console.log("i am in getPersonalInformation()");
            var url= "registrar/employee/personal/getPersonalInformation";
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

    UMS.service("personalInformationService", PersonalInformationService);
}