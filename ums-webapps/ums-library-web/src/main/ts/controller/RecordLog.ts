module ums {

    export class RecordLog {
        public static $inject = ['$q', 'notify', 'recordLogService'];

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private recordLogService: RecordLogService) {

            console.log("New World");
            recordLogService.getLog("", "742002", "");

        }
    }

    UMS.controller("RecordLog", RecordLog);
}