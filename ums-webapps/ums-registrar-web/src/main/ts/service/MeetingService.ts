module ums{
    export class MeetingService{

        public static $inject = ['HttpClient', '$q', 'notify'];
        private url: string = "meeting/schedule";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {}

        public saveMeetingSchedule(json: any): ng.IPromise<any> {
            console.log(json);
            let defer = this.$q.defer();
            this.httpClient.post(this.url + "/save", json, 'application/json')
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

        public updateMeetingSchedule(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.url + "/update", json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }

        public getMeetingSchedule(meetingType: number, meetingNo: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/get/meetingType/"+ meetingType + "/meetingNo/" + meetingNo, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }
    }
    UMS.service("meetingService", MeetingService);
}