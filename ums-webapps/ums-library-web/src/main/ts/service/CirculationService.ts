module ums {

    export class CirculationService {
        public static $inject = ['libConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'MessageFactory'];

        constructor(private libConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService, private messageFactory: MessageFactory) {
        }

        public getCirculation(patronId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get("circulation/id/" + patronId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Previous Checkouts");
                });
            return defer.promise;
        }

        public getCirculationCheckInItems(patronId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get("circulation/id/" + patronId + "/checkedInItems", HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Checked In Items");
                });
            return defer.promise;
        }

        public getAllCirculation(patronId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get("circulation/all/id/" + patronId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Previous Checkouts");
                });
            return defer.promise;
        }

        public getSingleCirculation(accessionNumber: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get("circulation/accessionNumber/" + accessionNumber, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    defer.resolve(response);
                });
            return defer.promise;
        }

        public saveCirculation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post("circulation/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public updateCirculation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put("circulation/update", json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }

        public updateCirculationForCheckIn(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put("circulation/update/checkIn", json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }
    }

    UMS.service("circulationService", CirculationService);
}

