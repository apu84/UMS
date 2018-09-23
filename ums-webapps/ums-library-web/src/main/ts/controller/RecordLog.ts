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
        public static $inject = ['$q', 'notify', 'recordLogService', 'employeeService'];

        public static LIBRARY_DEPT_ID = "74";

        private mfn: string = "";
        private modifiedBy: Employee;
        private modifiedOn: string = "";

        private searchResult: IRecordSearchResult[] = [];

        private itemsPerPage = 10;
        private currentPageNumber = 1;

        private employees: Employee[] = [];

        /*private resultTable: boolean = true;*/

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private recordLogService: RecordLogService,
                    private employeeService: EmployeeService) {

            this.employeeService.getEmployees(RecordLog.LIBRARY_DEPT_ID).then((employee: any) =>{
                this.employees = employee;
            });
            this.modifiedOn = Utils.getFormattedCurrentDate();
            this.search();
        }

        public search(): void{
            this.recordLogService.getLog(this.modifiedOn, (this.modifiedBy == undefined || null) ? "" :
                this.modifiedBy.id , this.mfn).then((result: IRecordSearchResult[]) =>{
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