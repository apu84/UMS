module ums{
    interface IConstants{
        id: number;
        name: string;
    }
    interface IAgendaAndResolution{
        id: number;
        agendaNo: string;
        agenda: string;
        resolution: string;
    }
    class AgendaAndResolution{
        public static $inject = ['registrarConstants', 'notify'];
        private meetingTypes: Array<IConstants> = [];
        private meetingNumbers: Array<number> = [];
        private meetingType: IConstants = null;
        private showEditorForAgenda: boolean = false;
        private showEditorForResolution: boolean = false;
        private showAgendaAndResolutionDiv: boolean = false;
        private showResolutionDiv: boolean = true;
        private agendaAndResolutions: IAgendaAndResolution[];

        constructor(private registrarConstants: any,
                    private notify: Notify) {

            this.meetingTypes = registrarConstants.meetingTypes;
            CKEDITOR.replace('CKEditorForAgenda');
            CKEDITOR.replace('CKEditorForResolution');
        }

        private getMeetingNumber(): void{
            if(this.meetingType.id == null){
                this.notify.error("Select A Meeting Type");
            }
            else {
                this.meetingNumbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
            }
        }

        private getAgendaResolution(): void{
            this.agendaAndResolutions = [{id: 1, agendaNo: "BOT 1", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""},
                {id: 2, agendaNo: "BOT 2", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""},
                {id: 3, agendaNo: "BOT 3", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""},
                {id: 4, agendaNo: "BOT 4", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""},
                {id: 5, agendaNo: "BOT 5", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""},
                {id: 6, agendaNo: "BOT 6", agenda: "Improve Univeristy quality by using automation and automatic monitoring", resolution: ""}];
            Utils.expandRightDiv();
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

        private addAgendaAndResolution(): void{

        }
    }

    UMS.controller("AgendaAndResolution", AgendaAndResolution);
}