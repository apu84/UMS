module ums {

    export interface IRecordSearchResult {
        id: string;
        mfn: string;
        modifiedBy: string;
        modifiedOn: string;
        modificationType: number;
        previousJson: string;
        modifiedJson: string;
    }

    export class RecordLog {
        public static $inject = ['$q', 'notify', 'recordLogService'];

        private mfn: string = "";
        private modifiedBy: string = "";
        private modifiedOn: string = "";

        private searchResult: IRecordSearchResult[] = [];

        /*private resultTable: boolean = true;*/

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private recordLogService: RecordLogService) {

        }

        public search(): void{
            this.recordLogService.getLog(this.modifiedOn, this.modifiedBy, this.mfn).then((result: IRecordSearchResult[]) =>{
                this.searchResult = result;
                /*this.searchResult.length == 0 ? this.setShowResultTable = false : this.setShowResultTable = true;*/
            });
        }

       /* get getShowResultTable() : boolean {
            return this.resultTable;
        }
        set setShowResultTable(pValue : boolean) {
            this.resultTable = pValue;
        }*/
    }

    UMS.controller("RecordLog", RecordLog);
}