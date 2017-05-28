module ums{
    export class PersonalInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        url:string="employee/personal";

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public savePersonalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.url+"/savePersonalInformation", json, 'application/json')
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
            let defer = this.$q.defer();
            this.httpClient.put(this.url+"/updatePersonalInformation", json, HttpClient.MIME_TYPE_JSON)
                .success(()=> {
                    this.notify.success("Update Successful");
                    defer.resolve();
                }).error((data) =>{
                this.notify.error("Error in updating");
            });

            return defer.promise;
        }

        // public getPersonalInformation(): ng.IPromise<any> {
        //     let defer = this.$q.defer();
        //     this.httpClient.get(this.url+"/getPersonalInformation/33333", HttpClient.MIME_TYPE_JSON,
        //         (json: any) => {
        //             defer.resolve(json.entries);
        //         },
        //         (response: ng.IHttpPromiseCallbackArg<any>) => {
        //             console.log(response);
        //         });
        //     return defer.promise;
        // }

        public getPersonalInformation(): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getPersonalInformation", HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                console.log("In Service");
                console.log(json);
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