module ums {


  export class FinancialAccountYearController {
    public static $inject = ['$scope', 'notify', 'FinancialAccountYearService']

    private financialAccountYears: IFinancialAccountYear[];
    private currentFinancialAccountYear: IFinancialAccountYear;
    private dateOptions: any;
    private startDate: string;
    private endDate: string;
    private opened: boolean;
    private enableEdit: boolean;
    private dateFormat: string;

    constructor(private $scope: ng.IScope, private notify: Notify, private financialAccountYearService: FinancialAccountYearService) {

      this.initialize();
    }

    public initialize() {
      this.enableEdit = true;
      this.dateFormat = "dd/MM/yyyy";
      this.enableEdit = false;
      this.startDate = "";
      this.endDate = "";

      this.financialAccountYearService.getAllFinalcialYears().then((years: IFinancialAccountYear[]) => {
        this.currentFinancialAccountYear = <IFinancialAccountYear>{};
        this.financialAccountYears = [];
        this.financialAccountYears = years;
        this.currentFinancialAccountYear = years.filter((f: IFinancialAccountYear) => f.bookClosingFlag == BookClosingFlagType.OPEN)[0];

        //this.enableEdit=years.length>0?false:true;
      });

    }

    public undo() {
      this.initialize();
    }

    public edit() {
      this.enableEdit = true;
    }


    public dateChanged() {
      console.log("Date");
      console.log(new Date(this.startDate));
    }
  }

  UMS.controller("FinancialAccountYearController", FinancialAccountYearController);
}