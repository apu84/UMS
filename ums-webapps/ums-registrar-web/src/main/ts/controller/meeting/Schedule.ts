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
        private upcomingMeetingScheduleList: IMeetingScheduleModel[] = [];
        private previousMeetingScheduleList: IMeetingScheduleModel[] = [];
        private meetingTypes: IConstants[] = [];
        private upcomingMeetingType: IConstants;
        private previousMeetingType: IConstants;
        private scheduleToEdit: IMeetingScheduleModel;
        private showMeetingList: boolean = false;
        private showUpcomingFilterFields: boolean = false;
        private showPreviousFilterFields: boolean = false;
        private currentPageNumberOfUpcoming: number = 1;
        private itemsPerPageOfUpcoming: number = 5;
        private currentPageNumberOfPrevious: number = 1;
        private itemsPerPageOfPrevious: number = 5;

        constructor(private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private meetingService: MeetingService) {

            this.meetingTypes = registrarConstants.meetingTypes;
            this.meetingSchedule = <IMeetingScheduleModel>{};
            this.addDate('default');
        }

        public getNextMeetingNo(): void {
            if (this.meetingSchedule.type) {
                this.meetingService.getNextMeetingNo(this.meetingSchedule.type.id).then((nextMeetingNo: any) => {
                    this.meetingSchedule.meetingNo = nextMeetingNo.nextMeetingNumber;
                });
            }
        }

        public save(form: ng.IFormController): void {
            this.convertToJson(this.meetingSchedule).then((json: any) => {
                this.meetingService.saveMeetingSchedule(json).then(()=>{
                    this.resetForm(form);
                })
            });
        }

        public update(): void {
            this.convertToJson(this.scheduleToEdit).then((json: any) => {
                this.meetingService.updateMeetingSchedule(json).then(()=>{
                    this.getSchedule();
                })
            });
        }

        private getSchedule(): void{
            this.meetingScheduleList = [];
            this.meetingService.getMeetingSchedule().then((response: any) =>{
                this.meetingScheduleList = response;
                this.findUpcomingMeetingScheduleList();
                this.findPreviousMeetingScheduleList();
            });
        }

        private findUpcomingMeetingScheduleList(): void{
            this.upcomingMeetingScheduleList = [];
            this.upcomingMeetingScheduleList = this.meetingScheduleList.filter((val, index, arr) =>{
               return arr[index].edit == true;
            });
        }

        private findPreviousMeetingScheduleList(): void{
            this.previousMeetingScheduleList = [];
            this.previousMeetingScheduleList = this.meetingScheduleList.filter((val, index, arr) =>{
                return arr[index].edit == false;
            });
        }

        public showPreviousMeetings(): void{
            this.showMeetingList = true;
            this.getSchedule();
            this.addDate('');
        }

        public hidePreviousMeetings(): void{
            this.showMeetingList = false;
            this.addDate('default');
        }

        public selectedScheduleToEdit(schedule: IMeetingScheduleModel): void{
            this.scheduleToEdit = <IMeetingScheduleModel> {};
            this.scheduleToEdit = schedule;
        }

        public modifyFilterFields(type: string, val: boolean): void{
           if(type == "upcoming"){
               this.showUpcomingFilterFields = val;
           }
           else if(type == "previous"){
               this.showPreviousFilterFields = val;
           }
        }

        public undoFilter(type: string): void{
            if(type == 'upcoming'){
                this.upcomingMeetingType = <IConstants>{};
                this.findUpcomingMeetingScheduleList();
            }
            else if(type == "previous"){
                this.previousMeetingType = <IConstants>{};
                this.findPreviousMeetingScheduleList();
            }
        }

        public doFilter(type: string): void{
            if(type == 'upcoming') {
                if (this.upcomingMeetingType) {
                    this.upcomingMeetingScheduleList = [];
                    this.upcomingMeetingScheduleList = this.meetingScheduleList.filter((val, index, arr) => {
                        return (arr[index].edit == true && arr[index].type.id === this.upcomingMeetingType.id);
                    });
                }
                else {
                    this.notify.error("Please select a meeting type");
                }
            }
            else if(type == 'previous'){
                if (this.previousMeetingType) {
                    this.previousMeetingScheduleList = [];
                    this.previousMeetingScheduleList = this.meetingScheduleList.filter((val, index, arr) => {
                        return (arr[index].edit == false && arr[index].type.id === this.previousMeetingType.id);
                    });
                }
                else {
                    this.notify.error("Please select a meeting type");
                }
            }
        }

        private resetForm(form: ng.IFormController) {
            this.meetingSchedule = <IMeetingScheduleModel>{};
            form.$setPristine();
        }

        private convertToJson(obj: any): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = obj;
            defer.resolve(JsonObject);
            return defer.promise;
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