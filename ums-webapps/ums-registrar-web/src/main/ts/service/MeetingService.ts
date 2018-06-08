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
            this.httpClient.post(this.scheduleUrl, json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.reject("Error");
                });
            return defer.promise;
        }

        public updateMeetingSchedule(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.scheduleUrl, json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                    defer.reject("Error");
                });
            return defer.promise;
        }

        public getMeetingSchedule(): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.scheduleUrl, HttpClient.MIME_TYPE_JSON,
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
            this.httpClient.get(this.scheduleUrl+"/meetingType/"+ meetingType, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }

        public getNextMeetingNo(meetingType: number): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.httpClient.get(this.scheduleUrl+"/nextMeetingNo/meetingType/"+ meetingType, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }


        // AgendaResolution
        public saveAgendaResolution(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.agendaResolutionUrl, json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.reject("Error");
                });
            return defer.promise;
        }

        public getMeetingAgendaResolution(scheduleId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.agendaResolutionUrl + "/scheduleId/"+ scheduleId, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Meeting Schedule Information");
                });
            return defer.promise;
        }

        public updateMeetingAgendaResolution(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.agendaResolutionUrl, json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }

        public deleteMeetingAgendaResolution(id: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.doDelete(this.agendaResolutionUrl + "/" + id)
                .success(() => {
                    this.notify.success("Delete Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Deleting");
                });
            return defer.promise;
        }


        public getMeetingMinutes(meetingType: number, meetingNo: number, printType: number): void {
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "Minutes Minutes";

            this.httpClient.get(this.agendaResolutionUrl + "/meetingType/" + meetingType + "/meetingNo/" + meetingNo + "/printType/" + printType,
                'application/pdf', (data: any, etag: string) => {
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
        }
        //search

    }
    UMS.service("meetingService", MeetingService);
}