module ums{
    export class AdditionalService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public checkDuplicateAcademicInitial(pShortName: string, pDeptId: string):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get(this.additionalUrl + "/initial/" + pShortName + "/deptId/" + pDeptId,'application/json',
                (result:boolean,etag:string)=>{
                    defer.resolve(result);
                },
                (response:ng.IHttpPromiseCallbackArg<any>)=>{
                    console.error(response);
                    this.notify.error("Error");
                });

            return defer.promise;
        }

        public saveAdditionalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.additionalUrl, json, 'application/json')
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

        public getAdditionalInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.additionalUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Additional Information");
                });
            return defer.promise;
        }


        // AreaOfInterest Information
        public saveAoiInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.aoiUrl, json, 'application/json')
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

        public getAoiInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.aoiUrl + "/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Additional Information");
                });
            return defer.promise;
        }
    }

    UMS.service("additionalService", AdditionalService);
}