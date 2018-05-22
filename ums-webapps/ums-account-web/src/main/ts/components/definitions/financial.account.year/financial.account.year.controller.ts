module ums {


  import IFinancialAccountYear = ums.IFinancialAccountYear;

  export class FinancialAccountYearController {
    public static $inject = ['$scope', 'notify', 'FinancialAccountYearService', '$q', '$state']

    private financialAccountYears: IFinancialAccountYear[];
    private currentFinancialAccountYear: IFinancialAccountYear;
    private dateOptions: any;
    private startDate: string;
    private endDate: string;
    private opened: boolean;
    private enableEdit: boolean;
    private dateFormat: string;
    private addButtonName: string;
    private dateMap: any;

    constructor(private $scope: ng.IScope,
                private notify: Notify,
                private financialAccountYearService: FinancialAccountYearService,
                private $q: ng.IQService,
                private $state:any) {

      this.initialize();
    }

    public initialize() {
      this.enableEdit = true;
      this.dateFormat = "dd-MM-yyyy";
      this.startDate = "";
      this.endDate = "";
      this.addButtonName = "";

      this.financialAccountYearService.getAllFinalcialYears().then((years: any) => {
        console.log("Fetched years");
        this.prepareReturnedYears(years);
        console.log(years);
        //this.enableEdit=years.length>0?false:true;
      });

    }

    private prepareReturnedYears(years: ums.IFinancialAccountYear[]) {
      this.dateMap = {};
      this.currentFinancialAccountYear = <IFinancialAccountYear>{};
      this.financialAccountYears = [];
      this.financialAccountYears = years;
      this.financialAccountYears.forEach((y: IFinancialAccountYear) => {
        y.currentStartDate = Utils.convertFromJacksonDate(y.currentStartDate);
        y.currentEndDate = Utils.convertFromJacksonDate(y.currentEndDate);
        y.previousStartDate = y.previousStartDate != null ? Utils.convertFromJacksonDate(y.previousStartDate) : "";
        y.previousEndDate = y.previousEndDate != null ? Utils.convertFromJacksonDate(y.previousEndDate) : "";
      });


      this.currentFinancialAccountYear = years.length > 0 ? angular.copy(years.filter((f: IFinancialAccountYear) => f.yearClosingFlag == YearClosingFlagType.OPEN)[0]) : <IFinancialAccountYear>{};
      this.startDate = this.currentFinancialAccountYear.currentStartDate;
      this.endDate = this.currentFinancialAccountYear.currentEndDate;
    }

    public undo() {
      this.initialize();
    }

    public edit() {
      this.enableEdit = true;
    }

    private addModalClicked() {
      this.$state.go('financialAccountYear.financialAccountYearClosing');
      this.addButtonName = this.currentFinancialAccountYear == null ? "Add" : "Save Edit";
    }

    private saveFinancialYear() {
      this.currentFinancialAccountYear.currentStartDate = Utils.getDateObject(this.startDate);
      this.currentFinancialAccountYear.currentEndDate = Utils.getDateObject(this.endDate);
      console.log(this.currentFinancialAccountYear);

      this.financialAccountYearService.saveAndGetAllFinancialYears(this.currentFinancialAccountYear).then((years) => {
        if (years == undefined)
          this.notify.error("Error in saving date");
        else
          this.prepareReturnedYears(years);
      });
    }


    public dateChanged() {
      console.log("Date");
      console.log(Utils.getDateObject(this.startDate));
    }


  }

  UMS.controller("FinancialAccountYearController", FinancialAccountYearController);
}