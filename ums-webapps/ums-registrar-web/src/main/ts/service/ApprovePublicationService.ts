module ums {
    export class ApprovePublicationService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public getPublicationInformation(): ng.IPromise<any> {
            let url= "employee/publication/getPublicationInformation";
            let defer = this.$q.defer();
            this.httpClient.get(url, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public getTeachersList(publicationStatus: string): ng.IPromise<any>{
            const url ="/ums-webservice-academic/academic/employee/getEmployee/publicationStatus/" + publicationStatus;
            let defer = this.$q.defer();
            this.httpClient.get(url, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log();
                });
            return defer.promise;
        }

        public updatePublicationStatus(json: any):ng.IPromise<any>{
            const url = "employee/publication/update/publicationStatus";
            let defer = this.$q.defer();
            this.httpClient.put(url , json, HttpClient.MIME_TYPE_JSON)
                .success(()=>{
                    if(json.entries[0]['publication'].status === '1') {
                        this.notify.success("Successfully Approved");
                    }
                    else if(json.entries[0]['publication'].status === '2'){
                        this.notify.success("Successfully Rejected");
                    }
                    defer.resolve("Successfully saved");
                }).error((data)=>{
                this.notify.error("Error in Updating");
                console.log(data);
            });

            return defer.promise;
        }
    }

    UMS.service('approvePublicationService', ApprovePublicationService);
}