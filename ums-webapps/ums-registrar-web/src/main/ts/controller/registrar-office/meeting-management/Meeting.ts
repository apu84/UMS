module ums{

    interface IMeeting extends ng.IScope{

        showScheduleAndMeetingDiv: boolean;
        showAgendaAndResolutionDiv: boolean;
        showSearchDiv: boolean;
        changeNav: Function;



        //ArrangeMeeting.ts
        meetingMembers: string;
        selectedMembers: string;
        selectedMemberList: Function;
        saveAndPrepareAgenda: Function;
        saveScheduleAndMembers: Function;



        //InsertResolution.ts
        showCKEditorDiv: boolean;
        showCKEditorBtn: boolean;
        showHideCKEditorBtn: boolean;
        showResetCKEditorBtn: boolean;
        showTextAreaAgendaDiv: boolean;
        showInlineCKEditorAgendaDiv: boolean;
        showInlineCKEditorLinkButtonDiv: boolean;
        showHideInlineCKEditorLinkButtonDiv: boolean;
        showAgendaAndResolutionListDiv: boolean;
        modelAgendaNo: string;
        modelAgendaDescription: string;
        agendaDescription: Array<IAgendaDescription>;
        clickToSaveCKEditorDataBtn: Function;
        clickToShowCKEditor: Function;
        clickToHideCKEditor: Function;
        clickToResetCKEditor: Function;
        switchTextareaToCKEditor: Function;
        switchCKEditorToTextarea: Function;
        editAgendaRow: Function;
        deleteAgendaRow: Function;
        send: Function;


    }



    //InsertResolution.ts
    interface IAgendaDescription{
        agendaNo: string;
        agendaDescription: string;
        resolution: string;
    }

    class Meeting{
        constructor(private appConstants: any,
                    private $scope: IMeeting,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService) {
            console.log("I am in searchMeeting.ts 1");

            $scope.showScheduleAndMeetingDiv = true;
            $scope.showAgendaAndResolutionDiv = false;
            $scope.showSearchDiv = false;

            $scope.changeNav = this.changeNav.bind(this);


            //ArrangeMeeting.ts
            this.addDate();
            $scope.selectedMemberList = this.selectedMemberList.bind(this);
            $scope.saveAndPrepareAgenda = this.saveAndPrepareAgenda.bind(this);
            $scope.saveScheduleAndMembers = this.saveScheduleAndMembers.bind(this);




            //InsertResolution.ts
            CKEDITOR.replace('CKEditor');
            CKEDITOR.replace('inlineCKEditor');
            CKEDITOR.disableAutoInline = true;
            CKEDITOR.config.resize_enabled = false;
            CKEDITOR.instances['CKEditor'].setData("");
            CKEDITOR.config.removePlugins = 'elementspath';

            $scope.showCKEditorDiv = false;
            $scope.showCKEditorBtn = true;
            $scope.showHideCKEditorBtn = false;
            $scope.showResetCKEditorBtn = false;
            $scope.showAgendaAndResolutionListDiv = false;
            $scope.showTextAreaAgendaDiv = true;
            $scope.showInlineCKEditorAgendaDiv = false;
            $scope.showInlineCKEditorLinkButtonDiv = true;
            $scope.showHideInlineCKEditorLinkButtonDiv = false;

            $scope.agendaDescription = [];

            $scope.clickToSaveCKEditorDataBtn = this.clickToSaveCKEditorDataBtn.bind(this);
            $scope.clickToShowCKEditor = this.clickToShowCKEditor.bind(this);
            $scope.clickToHideCKEditor = this.clickToHideCKEditor.bind(this);
            $scope.clickToResetCKEditor = this.clickToResetCKEditor.bind(this);
            $scope.switchTextareaToCKEditor = this.switchTextareaToCKEditor.bind(this);
            $scope.switchCKEditorToTextarea = this.switchCKEditorToTextarea.bind(this);
            $scope.editAgendaRow = this.editAgendaRow.bind(this);
            $scope.deleteAgendaRow = this.deleteAgendaRow.bind(this);
            $scope.send = this.send.bind(this);

            console.log("I am in searchMeeting.ts");
        }

        private changeNav(nav: number){

            console.log("I am in ChangeNav()");

            this.$scope.showAgendaAndResolutionDiv = false;
            this.$scope.showSearchDiv = false;
            this.$scope.showScheduleAndMeetingDiv = false;

            if(nav == 1){
                console.log("I am in nav 1");
                this.$scope.showScheduleAndMeetingDiv = true;
            }
            else if(nav == 2){
                console.log("I am in nav 2");
                this.$scope.showAgendaAndResolutionDiv = true;
            }
            else if(nav == 3){
                console.log("I am in nav 3");
                this.$scope.showSearchDiv = true;
            }
        }



        //ArrangeMeeting.ts
        private addDate(): void {
            setTimeout(function () {
                $('.datepicker-default').datepicker();
                $('.datepicker-default').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 100);
        }

        private selectedMemberList() {
            console.log("I am in selectedMemberList()");
            console.log(this.$scope.selectedMembers);

        }

        private saveAndPrepareAgenda(){
            console.log("I am in saveAndPrepareAgenda()");
            this.$scope.changeNav(2);
            this.notify.success("Data Saved");
        }

        private saveScheduleAndMembers(){
            console.log("I am in saveScheduleAndMembers()");
            this.notify.success("Data Saved");
        }





        //InsertResolution.ts
        private clickToSaveCKEditorDataBtn(){
            console.log("I am in clickToSaveCKEditorDataBtn()");
            this.$scope.showAgendaAndResolutionListDiv = true;
            if(CKEDITOR.instances['CKEditor'].getData() == ""){
                console.log("Data Empty");
            }
            else{
                console.log("Data Not Empty");
            }

            var obj: IAgendaDescription = <IAgendaDescription>{};
            obj.agendaNo = this.$scope.modelAgendaNo;
            if(this.$scope.modelAgendaDescription == '' || this.$scope.modelAgendaDescription == null){
                obj.agendaDescription = CKEDITOR.instances['inlineCKEditor'].getData();
            }
            else if(CKEDITOR.instances['inlineCKEditor'].getData() == ''){
                obj.agendaDescription = this.$scope.modelAgendaDescription;
            }
            else {
                obj.agendaDescription = this.$scope.modelAgendaDescription;
            }
            obj.resolution = CKEDITOR.instances['CKEditor'].getData();
            this.$scope.agendaDescription.push(obj);

            CKEDITOR.instances['CKEditor'].setData("");
            CKEDITOR.instances['inlineCKEditor'].setData("");
            this.$scope.modelAgendaNo = "";
            this.$scope.modelAgendaDescription = "";
        }

        private clickToShowCKEditor(){
            console.log("I am in clickToShowCKEditor()");
            this.$scope.showCKEditorBtn = false;
            this.$scope.showCKEditorDiv = true;
            this.$scope.showHideCKEditorBtn = true;
            this.$scope.showResetCKEditorBtn = true;
        }

        private clickToHideCKEditor(){
            console.log("I am in clickToHideCKEditor()");
            this.$scope.showCKEditorDiv = false;
            this.$scope.showHideCKEditorBtn = false;
            this.$scope.showResetCKEditorBtn = false;
            this.$scope.showCKEditorBtn = true;
        }

        private clickToResetCKEditor(){
            console.log("I am in clickToResetCKEditor()");
            CKEDITOR.instances['CKEditor'].setData("");
        }

        private switchTextareaToCKEditor(){
            console.log("I am in switchTextareaToCKEditor()");
            this.$scope.showTextAreaAgendaDiv = false;
            this.$scope.showInlineCKEditorLinkButtonDiv = false;
            this.$scope.showInlineCKEditorAgendaDiv = true;
            this.$scope.showHideInlineCKEditorLinkButtonDiv = true;
        }

        private switchCKEditorToTextarea(){
            console.log("I am in switchCKEditorToTextarea()");
            this.$scope.showInlineCKEditorAgendaDiv = false;
            this.$scope.showHideInlineCKEditorLinkButtonDiv = false;
            this.$scope.showInlineCKEditorLinkButtonDiv = true;
            this.$scope.showTextAreaAgendaDiv = true;

            CKEDITOR.instances['inlineCKEditor'].setData("");
        }

        private editAgendaRow(){
            console.log("I am in editAgendaRow()");
        }

        private deleteAgendaRow(agenda: IAgendaDescription){
            console.log("I am in deleteAgendaRow()");
        }

        private send(){
            console.log("I am in send()");
            this.notify.success("Meeting Proposal Sent");
        }

    }

    UMS.controller("Meeting", Meeting);
}