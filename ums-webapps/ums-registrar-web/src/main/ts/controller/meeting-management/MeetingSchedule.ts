module ums{
    interface IMeetingScheduleModel{
        type: IConstants,
        refNo: string;
        date: string;
        time: string;
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
        public static $inject = ['registrarConstants', 'notify'];
        private prepareMeetingModel: { meetingSchedule: IMeetingScheduleModel; meetingMember: IMeetingMemberModel[]; };
        private meetingTypes: Array<IConstants> = [];
        private meetingMembers: Array<IMeetingMemberModel> = [];
        private selectedMembers: Array<IMeetingMemberModel> = [];

        constructor(private registrarConstants: any, private notify: Notify) {

            this.meetingTypes = registrarConstants.meetingTypes;
            this.prepareMeetingModel = {
                meetingSchedule: <IMeetingScheduleModel>{},
                meetingMember: Array<IMeetingMemberModel>()
            };
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
                {name: "BOT 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 10},
                {name: "BOT 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 10},
                {name: "BOT 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 10},
                {name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 40},
                {name: "Academic Council Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 40},
                {name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 40},
                {name: "Academic Council Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 40},
                {name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 40},
                {name: "Academic Council Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 40}];
        }

        private getSyndicateMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "ABC", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 20},
                {name: "Syndicate 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 4", designation: "Member", email: "JKL@aust.edu", contactNo: "", memberOf: 20},
                {name: "kawsur", designation: "ABC", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 20},
                {name: "Syndicate 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 4", designation: "Member", email: "JKL@aust.edu", contactNo: "", memberOf: 20},
                {name: "kawsur", designation: "ABC", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 20},
                {name: "Syndicate 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 20},
                {name: "Syndicate 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 20}];
        }

        private getFinanceCommitteeMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 30},
                {name: "Finance Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 30},
                {name: "Finance Member 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 30},
                {name: "Finance Member 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 30},
                {name: "Finance Member 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 30}];
        }

        private getAcademicCouncilMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 40},
                {name: "Academic Council Member 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 40},
                {name: "Academic Council Member 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 40}];
        }

        private getHeadsMeetingMembers(): Array<IMeetingMemberModel>{
            return [{name: "kawsur", designation: "Chairman", email: "kawsur.iums@aust.edu", contactNo: "01672494863", memberOf: 50},
                {name: "Head 1", designation: "Member", email: "ABC@aust.edu", contactNo: "", memberOf: 50},
                {name: "Head 2", designation: "Member", email: "DEF@aust.edu", contactNo: "", memberOf: 50},
                {name: "Head 3", designation: "Member", email: "GHI@aust.edu", contactNo: "", memberOf: 50},
                {name: "Head 4", designation: "Member-Secretary", email: "JKL@aust.edu", contactNo: "", memberOf: 50}];
        }

        private sendInvitationWithoutAgenda(): void{

        }

        private saveAndPrepareAgenda(): any {
            console.log("I am changing the controller");
            return 'ui-serf="views/meeting-management/agenda-resolution.html"';
        }

        private saveScheduleAndMembers(): void{

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

    }

    UMS.controller("MeetingSchedule", MeetingSchedule);
}