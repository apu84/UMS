module ums{
    export interface IMeetingScheduleModel{
        id: string
        type: IConstants,
        meetingNo: number;
        refNo: string;
        datetime: string;
        room: string;
    }
    interface IConstants{
        id: number;
        name: string;
    }

    class Schedule{
        public static $inject = ['registrarConstants', '$q', 'notify', '$scope', 'meetingService'];
        private meetingSchedule: IMeetingScheduleModel;
        private meetingTypes: IConstants[] = [];
        private showPreviousMeetingList: boolean = false;

        constructor(private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private meetingService: MeetingService) {

            this.meetingTypes = registrarConstants.meetingTypes;
            this.meetingSchedule = <IMeetingScheduleModel>{};
            this.addDate();
        }

        private getMeetingNoAndMembers(): void{
            this.getNextMeetingNo();
        }

        private getNextMeetingNo(): void{
            this.meetingService.getNextMeetingNo(this.meetingSchedule.type.id).then((nextMeetingNo: any) =>{
                this.meetingSchedule.meetingNo = nextMeetingNo.nextMeetingNumber;
            });
        }

        private save(): void {
            this.convertToJson().then((json: any) => {
                this.meetingService.saveMeetingSchedule(json).then(()=>{
                })
            });
        }

        private getSchedule(): void{
            this.meetingSchedule = <IMeetingScheduleModel>{};
            this.meetingService.getMeetingSchedule(10, 1).then((response: any) =>{
                this.meetingSchedule = response[0];
            })
        }

        private convertToJson(): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.meetingSchedule;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        public showPreviousMeetings(): void{
            this.showPreviousMeetingList = true;
        }

        public hidePreviousMeetings(): void{
            this.showPreviousMeetingList = false;
        }

        private addDate(): void {
            let internalThis:any = this;
            setTimeout(function () {
                $('#datetimepicker-default').datetimepicker();
                $('#datetimepicker-default').blur(function (e) {
                    internalThis.prepareMeetingModel.meetingSchedule.datetime = $(this).val();
                    console.log(internalThis.prepareMeetingModel.meetingSchedule.datetime);
                });
            }, 10);
        }
    }

    UMS.controller("Schedule", Schedule);
}