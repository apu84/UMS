module ums{
    interface IConstants{
        id: number;
        name: string;
    }

    class AgendaAndResolution{
        public static $inject = ['registrarConstants', 'notify'];
        private meetingTypes: Array<IConstants> = [];
        private meetingNumbers: Array<number> = [];
        private meetingType: IConstants = null;

        constructor(private registrarConstants: any,
                    private notify: Notify) {

            this.meetingTypes = registrarConstants.meetingTypes;
            console.log(this.meetingTypes);

            // CKEDITOR.replace('CKEditor');
            // CKEDITOR.replace('inlineCKEditor');
            // CKEDITOR.disableAutoInline = true;
            // CKEDITOR.config.resize_enabled = false;
            // CKEDITOR.instances['CKEditor'].setData("");
            // CKEDITOR.config.removePlugins = 'elementspath';
        }

        private getMeetingNumber(): void{
            if(this.meetingType.id == null){
                this.notify.error("Select A Meeting Type");
            }
            else {
                this.meetingNumbers = [null, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
                Utils.expandRightDiv();
            }
        }

        private getAgendaResolution(): void{
            this.notify.error("fetching Data...");
        }
    }

    UMS.controller("AgendaAndResolution", AgendaAndResolution);
}