module ums{
    interface IAgendaAndResolution extends ng.IScope{
        getAgendaResolution: Function;
    }
    interface IConstants{
        id: number;
        name: string;
    }
    interface IAgendaResolution{
        id: string;
        agendaNo: string;
        agenda: string;
        resolution: string;
        showExpandButton: boolean;
    }
    class AgendaAndResolution{
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify'];
        private meetingTypes: IConstants[] = [];
        private meetingNumbers: number[] = [];
        private meetingType: IConstants = null;
        private showEditorForAgenda: boolean = false;
        private showEditorForResolution: boolean = false;
        private showAgendaAndResolutionDiv: boolean = false;
        private showResolutionDiv: boolean = true;
        private showMasterExpandButton: boolean = true;
        private showContent: boolean = false;
        private agendaAndResolutions: IAgendaResolution[] = [];
        private agendaNo: string = "";
        private agenda: string = "";
        private resolution: string = "";

        constructor(private registrarConstants: any,
                    private $scope: IAgendaAndResolution,
                    private $q: ng.IQService,
                    private notify: Notify) {

            $scope.getAgendaResolution = this.getAgendaResolution.bind(this);

            this.meetingTypes = registrarConstants.meetingTypes;
            CKEDITOR.replace('CKEditorForAgenda');
            CKEDITOR.replace('CKEditorForResolution');
            CKEDITOR.instances['CKEditorForAgenda'].setData("");
            CKEDITOR.instances['CKEditorForResolution'].setData("");
        }

        private getMeetingNumber(): void{
            if(this.meetingType.id == null){
                this.notify.error("Select A Meeting Type");
            }
            else {
                this.meetingNumbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
            }
        }

        private getAgendaResolution(): void{
            this.agendaAndResolutions = [{id: "1", agendaNo: "BOT 1", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: "", showExpandButton: true}];
            Utils.expandRightDiv();
            this.showContent = true;
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
            else{
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

        private addAgendaAndResolution(): void{
            if(CKEDITOR.instances['CKEditorForAgenda'].getData() != ""){
                this.agenda = CKEDITOR.instances['CKEditorForAgenda'].getData();
            }
            if(CKEDITOR.instances['CKEditorForResolution'].getData() != ""){
                this.resolution = CKEDITOR.instances['CKEditorForResolution'].getData();
            }
            let agendaResolutionEntry: IAgendaResolution;
            agendaResolutionEntry = {
                id: "",
                agendaNo: this.agendaNo,
                agenda: this.agenda,
                resolution: this.resolution,
                showExpandButton: true
            };
            this.agendaAndResolutions.push(agendaResolutionEntry);
            this.reset();
        }

        private edit(index: number): void{
            this.toggleAgendaAndResolutionDiv('show');
            this.agendaNo = this.agendaAndResolutions[index].agendaNo;
            this.agenda = this.agendaAndResolutions[index].agenda;
            this.resolution = this.agendaAndResolutions[index].resolution;
            this.agendaAndResolutions.splice(index, 1);
        }

        private reset(): void{
            this.agendaNo = "";
            this.agenda = "";
            this.resolution = "";
            CKEDITOR.instances['CKEditorForAgenda'].setData("");
            CKEDITOR.instances['CKEditorForResolution'].setData("");
        }

        private convertToJson(): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.agendaAndResolutions;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("AgendaAndResolution", AgendaAndResolution);
}