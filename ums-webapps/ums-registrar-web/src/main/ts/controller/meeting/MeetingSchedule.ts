module ums{
    export interface IMeetingScheduleModel{
        id: string
        type: IConstants,
        meetingNo: number;
        refNo: string;
        datetime: string;
        room: string;
    }
    interface IMeetingMemberModel{
        name: string;
        designation: string;
        email: string;
        contactNo: string;
        memberOf: number;
    }
    interface IConstants{
        id: number;
        name: string;
    }

    class MeetingSchedule{
        public static $inject = ['registrarConstants', '$q', 'notify', '$scope', 'meetingService'];
        private prepareMeetingModel: { meetingSchedule: IMeetingScheduleModel; meetingMember: IMeetingMemberModel[]; };
        private meetingTypes: Array<IConstants> = [];
        private meetingMembers: Array<IMeetingMemberModel> = [];
        private selectedMembers: Array<IMeetingMemberModel> = [];
        private datetimeValue: string = "";

        constructor(private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private meetingService: MeetingService) {

            this.meetingTypes = registrarConstants.meetingTypes;
            this.prepareMeetingModel = {
                meetingSchedule: <IMeetingScheduleModel>{},
                meetingMember: Array<IMeetingMemberModel>()
            };

            // this.prepareMeetingModel.meetingSchedule.type = null;
            // this.prepareMeetingModel.meetingSchedule.meetingNo = null;
            // this.prepareMeetingModel.meetingSchedule.refNo = "";
            // this.prepareMeetingModel.meetingSchedule.datetime = "";
            // this.prepareMeetingModel.meetingSchedule.room = "";
            this.addDate();

            // $scope.$watch(() => this.dateValue, (newValue, oldValue) => {
            //     console.log(newValue, oldValue);
            // });
        }

        private getMembers(): void{
            if(this.prepareMeetingModel.meetingSchedule.type.id == 10){
                this.meetingMembers = this.getBoardOfTrusteeMembers();
            }
            else if(this.prepareMeetingModel.meetingSchedule.type.id == 20){
                this.meetingMembers = this.getSyndicateMembers();
            }
            else if(this.prepareMeetingModel.meetingSchedule.type.id == 30){
                this.meetingMembers = this.getFinanceCommitteeMembers();
            }
            else if(this.prepareMeetingModel.meetingSchedule.type.id == 40){
                this.meetingMembers = this.getAcademicCouncilMembers();
            }
            else if(this.prepareMeetingModel.meetingSchedule.type.id == 50){
                this.meetingMembers = this.getHeadsMeetingMembers();
            }
            else{
                this.meetingMembers = [];
                this.notify.error("Please Select A Meeting Type");
            }
        }

        private getBoardOfTrusteeMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 10},
                {name: "BOT 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 10},
                {name: "BOT 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 10}];
        }

        private getSyndicateMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "ABC", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 20},
                {name: "Syndicate 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 20}];
        }

        private getFinanceCommitteeMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 30},
                {name: "Finance Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 30}];
        }

        private getAcademicCouncilMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 40}];
        }

        private getHeadsMeetingMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 50}];
        }

        private sendInvitationWithoutAgenda(): void{

        }

        private save(): void {
            this.convertToJson().then((json: any) => {
                this.meetingService.saveMeetingSchedule(json).then(()=>{
                })
            });
        }

        private getSchedule(): void{
            this.prepareMeetingModel.meetingSchedule = <IMeetingScheduleModel>{};
            this.meetingService.getMeetingSchedule(10, 1).then((response: any) =>{
                console.log(this.prepareMeetingModel.meetingSchedule);
                this.prepareMeetingModel.meetingSchedule = response[0];
                console.log(this.prepareMeetingModel.meetingSchedule);
            })
        }

        private toggleAll(): void{
            if (this.selectedMembers.length === this.meetingMembers.length) {
                this.selectedMembers = [];
            } else if (this.selectedMembers.length === 0 || this.selectedMembers.length > 0) {
                this.selectedMembers = this.meetingMembers.slice(0);
            }
        }

        private isChecked(): number{
            return this.selectedMembers.length = this.meetingMembers.length;
        }

        private exists(item): boolean{
            return this.selectedMembers.indexOf(item) > -1;
        }

        private toggleSelection(item): void{
            let idx = this.selectedMembers.indexOf(item);
            if (idx > -1) {
                this.selectedMembers.splice(idx, 1);
            }
            else {
                this.selectedMembers.push(item);
            }
        }

        private convertToJson(): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.prepareMeetingModel.meetingSchedule;
            defer.resolve(JsonObject);
            return defer.promise;
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

    UMS.controller("MeetingSchedule", MeetingSchedule);
}