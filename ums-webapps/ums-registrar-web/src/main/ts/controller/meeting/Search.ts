module ums{
    interface ISearch extends ng.IScope{
        pageChanged: Function;
        pagination: any;
        data: any;
    }
    interface IAgendaResolutionModel{
        id: string;
        agendaNo: string;
        agenda: string;
        agendaEditor: string;
        resolution: string;
        resolutionEditor: string;
        scheduleId: string;
        showExpandButton: boolean;
    }

    class Search{
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', 'meetingService'];
        private queryText: string = "";
        private searchType: any = "basic";
        private searchResults: IAgendaResolutionModel[] = [];
        private agendaResolutionDetail: IAgendaResolutionModel;
        private itemPerPage: number = 10;
        private currentPage: number = 1;
        private totalRecord: number = 0;
        private meetingScheduleId: string = "";
        private overflow: boolean[] = [];


        //agendaResolution.ts
        private showEditorForAgenda: boolean = false;
        private showEditorForResolution: boolean = false;
        private showAgendaAndResolutionDiv: boolean = false;
        private showResolutionDiv: boolean = true;
        private showMasterExpandButton: boolean = true;
        private showRightDiv: boolean = false;
        private showCancelButton: boolean = false;
        private showUpdateButton: boolean = false;
        private agendaAndResolutions: IAgendaResolutionModel[] = [];
        private tempAgendaAndResolution: IAgendaResolutionModel[] = [];
        private agendaNo: string = "";
        private agenda: string = "";
        private resolution: string = "";

        constructor(private registrarConstants: any,
                    private $scope: ISearch,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private meetingService: MeetingService) {

            $scope.pagination = {};
            $scope.pagination.currentPage = 1;

            CKEDITOR.replace('CKEditorForAgenda');
            CKEDITOR.replace('CKEditorForResolution');
            CKEDITOR.instances['CKEditorForAgenda'].setData("");
            CKEDITOR.instances['CKEditorForResolution'].setData("");

            $("#fullMeetingDiv").hide();
        }

        private searchSelection(): void{

        }

        private search(): void{
            this.searchResults = [];

            this.meetingService.getMeetingAgendaResolution("-7694807427984587141").then((result: any)=>{
                this.searchResults = result;
                this.totalRecord = this.searchResults.length;
            })
        }

        private pageChanged(newPageNumber: number): void{

        }

        private view(agendaResolution: any): void{
            this.agendaResolutionDetail =  <IAgendaResolutionModel> {};
            this.agendaResolutionDetail = agendaResolution;
        }

        private showFullMeetingDetails(agendaResolution: any): void{
            this.meetingScheduleId = agendaResolution.scheduleId;
            this.getAgendaResolution();

            $("#searchDiv").hide(10);
            $("#fullMeetingDiv").show(200);
        }

        private showSearchDiv(): void{
            $("#fullMeetingDiv").hide(10);
            $("#searchDiv").show(200);

        }

        private textOverflowController(index: number): void{
            if(this.overflow[index] == true){
                this.overflow[index] = false;
            }
            else {
                this.overflow[index] = true;
            }
        }

        // AgendaResolution.ts

        private getAgendaResolution(): void{
            this.agendaAndResolutions = [];
            this.meetingService.getMeetingAgendaResolution(this.meetingScheduleId).then((response: any)=>{
                this.agendaAndResolutions = response;
                Utils.expandRightDiv();
                this.showRightDiv = true;
            });
        }

        private toggleEditor(type: string, param: string): void{
            if(type === 'agenda') {
                if (param == "editor") {
                    this.showEditorForAgenda = true;
                }
                else {
                    this.showEditorForAgenda = false;
                }
            }
            else if(type === "resolution") {
                if (param == "editor") {
                    this.showEditorForResolution = true;
                }
                else {
                    this.showEditorForResolution = false;
                }
            }
        }

        private toggleAgendaAndResolutionDiv(param: string): void{
            if(param === "show"){
                this.showAgendaAndResolutionDiv = true;
            }
            else{
                this.showAgendaAndResolutionDiv = false;
            }
        }

        private toggleResolutionDiv(param: string): void{
            if(param === "show"){
                this.showResolutionDiv = true;
            }
            else{
                this.showResolutionDiv = false;
            }
        }

        private toggleExpand(param: string, index: number): void{
            if(param == "hide"){
                this.agendaAndResolutions[index].showExpandButton = false;
            }
            else{
                this.agendaAndResolutions[index].showExpandButton = true;
            }
        }

        private toggleExpandAll(param: string): void{
            if(param === "hide"){
                this.showMasterExpandButton = false;
                for(let i = 0; i < this.agendaAndResolutions.length; i++){
                    this.agendaAndResolutions[i].showExpandButton = false;
                }
            }
            else{
                this.showMasterExpandButton = true;for(let i = 0; i < this.agendaAndResolutions.length; i++){
                    this.agendaAndResolutions[i].showExpandButton = true;
                }

            }
        }

        private save(): void{
            let editorForAgenda: string = "N";
            let editorForResolution: string = "N";
            if(CKEDITOR.instances['CKEditorForAgenda'].getData() != ""){
                this.agenda = CKEDITOR.instances['CKEditorForAgenda'].getData();
                editorForAgenda = "Y";
            }
            if(CKEDITOR.instances['CKEditorForResolution'].getData() != ""){
                this.resolution = CKEDITOR.instances['CKEditorForResolution'].getData();
                editorForResolution = "Y";
            }
            let agendaResolutionEntry: IAgendaResolutionModel;
            agendaResolutionEntry = {
                id: "",
                agendaNo: this.agendaNo,
                agenda: this.agenda,
                agendaEditor: editorForAgenda,
                resolution: this.resolution,
                resolutionEditor: editorForResolution,
                scheduleId: this.meetingScheduleId,
                showExpandButton: true
            };
            this.convertToJson(agendaResolutionEntry).then((json: any) => {
                this.meetingService.saveAgendaResolution(json).then(() =>{
                    this.getAgendaResolution();
                    this.reset();
                });
            });
        }

        private update(): void{
            let editorForAgenda: string = "N";
            let editorForResolution: string = "N";
            let index = this.tempAgendaAndResolution[0].id;
            if(CKEDITOR.instances['CKEditorForAgenda'].getData() != ""){
                this.agenda = CKEDITOR.instances['CKEditorForAgenda'].getData();
                editorForAgenda = "Y";
            }
            if(CKEDITOR.instances['CKEditorForResolution'].getData() != ""){
                this.resolution = CKEDITOR.instances['CKEditorForResolution'].getData();
                editorForResolution = "Y";
            }
            let agendaResolutionEntry: IAgendaResolutionModel;
            agendaResolutionEntry = {
                id: index,
                agendaNo: this.agendaNo,
                agenda: this.agenda,
                agendaEditor: editorForAgenda,
                resolution: this.resolution,
                resolutionEditor: editorForResolution,
                scheduleId: this.meetingScheduleId,
                showExpandButton: true
            };
            this.convertToJson(agendaResolutionEntry).then((json: any) => {
                this.meetingService.updateMeetingAgendaResolution(json).then(() => {
                    this.getAgendaResolution();
                    this.reset();
                });
            });
        }

        private edit(index: number): void{
            this.tempAgendaAndResolution = [];
            this.toggleAgendaAndResolutionDiv('show');
            this.showCancelButton = true;
            this.showUpdateButton = true;
            this.agendaNo = this.agendaAndResolutions[index].agendaNo;
            if(this.agendaAndResolutions[index].agendaEditor == "Y"){
                CKEDITOR.instances['CKEditorForAgenda'].setData(this.agendaAndResolutions[index].agenda);
                this.toggleEditor('agenda', 'editor');
            }
            else {
                this.agenda = this.agendaAndResolutions[index].agenda;
                this.toggleEditor('agenda','text');
            }
            if(this.agendaAndResolutions[index].resolutionEditor == "Y"){
                CKEDITOR.instances['CKEditorForResolution'].setData(this.agendaAndResolutions[index].resolution);
                this.toggleEditor('resolution', 'editor');
            }
            else {
                this.resolution = this.agendaAndResolutions[index].resolution;
                this.toggleEditor('resolution', 'text');
            }
            this.tempAgendaAndResolution = this.agendaAndResolutions.splice(index, 1);
        }

        private delete(index: number): void{
            this.meetingService.deleteMeetingAgendaResolution(this.agendaAndResolutions[index].id).then(() => {
                this.getAgendaResolution();
            });
        }

        private reset(): void{
            this.agendaNo = "";
            this.agenda = "";
            this.resolution = "";
            CKEDITOR.instances['CKEditorForAgenda'].setData("");
            CKEDITOR.instances['CKEditorForResolution'].setData("");
            this.showUpdateButton = false;
            this.showCancelButton = false;
        }

        private cancel(): void{
            this.showCancelButton = false;
            this.showUpdateButton = false;
            this.agendaAndResolutions.push(this.tempAgendaAndResolution[0]);
            this.tempAgendaAndResolution = [];
            this.reset();
        }

        private convertToJson(convertingObject: IAgendaResolutionModel): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = convertingObject;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("Search", Search);
}