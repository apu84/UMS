module ums{
    export class MeetingService{

        public static $inject = ['HttpClient', '$q', 'notify'];
        private scheduleUrl: string = "meeting/schedule";
        private agendaResolutionUrl: string = "meeting/agendaResolution";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {}



        //Meeting Schedule
        public saveMeetingSchedule(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.scheduleUrl + "/save", json, 'application/json')
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
            this.httpClient.put(this.scheduleUrl + "/update", json, HttpClient.MIME_TYPE_JSON)
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
            this.httpClient.get(this.scheduleUrl+"/get/meetingType/"+ meetingType + "/meetingNo/" + meetingNo, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }

        public getMeetingNumber(meetingType: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.scheduleUrl+"/get/meetingType/"+ meetingType, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }


        // AgendaResolution
        public saveAgendaResolution(json: any): ng.IPromise<any> {
            console.log(json);
            let defer = this.$q.defer();
            this.httpClient.post(this.agendaResolutionUrl + "/save", json, 'application/json')
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
    }
    UMS.service("meetingService", MeetingService);
}