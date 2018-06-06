module ums{
    export interface IMeetingScheduleModel{
        id: string
        type: IConstants,
        meetingNo: number;
        refNo: string;
        datetime: string;
        room: string;
        edit: boolean;
    }
    interface IConstants{
        id: number;
        name: string;
    }

    class Schedule{
        public static $inject = ['registrarConstants', '$q', 'notify', '$scope', 'meetingService'];
        private meetingSchedule: IMeetingScheduleModel;
        private meetingScheduleList: IMeetingScheduleModel[] = [];
        private filteredMeetingScheduleList: IMeetingScheduleModel[] = [];
        private meetingTypes: IConstants[] = [];
        private meetingType: IConstants;
        private scheduleToEdit: IMeetingScheduleModel;
        private showPreviousMeetingList: boolean = false;
        private showFilterFields: boolean = false;

        constructor(private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private meetingService: MeetingService) {

            this.meetingTypes = registrarConstants.meetingTypes;
            this.meetingSchedule = <IMeetingScheduleModel>{};
            this.addDate('default');
        }

        private getNextMeetingNo(): void {
            if (this.meetingSchedule.type) {
                this.meetingService.getNextMeetingNo(this.meetingSchedule.type.id).then((nextMeetingNo: any) => {
                    this.meetingSchedule.meetingNo = nextMeetingNo.nextMeetingNumber;
                });
            }
        }

        private save(): void {
            this.convertToJson(this.meetingSchedule).then((json: any) => {
                this.meetingService.saveMeetingSchedule(json).then(()=>{
                    this.meetingSchedule = <IMeetingScheduleModel>{};
                })
            });
        }

        private update(): void {
            this.convertToJson(this.scheduleToEdit).then((json: any) => {
                this.meetingService.updateMeetingSchedule(json).then(()=>{
                    this.scheduleToEdit = <IMeetingScheduleModel>{};
                })
            });
        }

        private getSchedule(): void{
            this.meetingScheduleList = [];
            this.filteredMeetingScheduleList = [];
            this.meetingService.getMeetingSchedule().then((response: any) =>{
                this.meetingScheduleList = response;
                this.filteredMeetingScheduleList = response;
            });
        }

        private convertToJson(obj: any): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = obj;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        public showPreviousMeetings(): void{
            this.showPreviousMeetingList = true;
            this.getSchedule();
            this.addDate('');
        }

        public hidePreviousMeetings(): void{
            this.showPreviousMeetingList = false;
            this.addDate('default');
        }

        public selectedScheduleToEdit(schedule: IMeetingScheduleModel): void{
            this.scheduleToEdit = <IMeetingScheduleModel> {};
            this.scheduleToEdit = schedule;
        }

        public modifyFilterFields(val: boolean): void{
            this.showFilterFields = val;
        }

        public undoFilter(): void{
            this.filteredMeetingScheduleList = [];
            this.filteredMeetingScheduleList = this.meetingScheduleList;
        }

        public doFilter(): void{
            if(this.meetingType) {
                this.filteredMeetingScheduleList = [];
                this.filteredMeetingScheduleList = this.meetingScheduleList.filter((val, index, arr) => {
                    return arr[index].type.id === this.meetingType.id;
                });
            }
            else{
                this.notify.error("Please select a meeting type");
            }
        }

        private addDate(type: string): void {
            if(type == "default") {
                let internalThis: any = this;
                setTimeout(function () {
                    $('#datetimepicker-default').datetimepicker();
                    $('#datetimepicker-default').blur(function (e) {
                        internalThis.meetingSchedule.datetime = $(this).val();
                    });
                }, 10);
            }
            else{
                let internalThat: any = this;
                setTimeout(function () {
                    $('#datetimepicker-default1').datetimepicker();
                    $('#datetimepicker-default1').blur(function (e) {
                        internalThat.scheduleToEdit.datetime = $(this).val();
                    });
                }, 10);
            }
        }
    }

    UMS.controller("Schedule", Schedule);
}