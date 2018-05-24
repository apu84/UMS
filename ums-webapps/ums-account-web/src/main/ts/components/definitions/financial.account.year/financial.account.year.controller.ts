module ums {

  import IFinancialAccountYear = ums.IFinancialAccountYear;

  export interface IConstants{
      id: number;
      name:string;
    }

  export class FinancialAccountYearController {
    public static $inject = ['$scope','accountConstants', 'notify', 'FinancialAccountYearService', '$q', '$state']

    private financialAccountYears: IFinancialAccountYear[];
    private currentFinancialAccountYear: IFinancialAccountYear;
    private newFinancialAccountYear: IFinancialAccountYear;
    private dateOptions: any;
    private startDate: string;
    private endDate: string;
    private opened: boolean;
    private enableEdit: boolean;
    private dateFormat: string;
    private addButtonName: string;
    private dateMap: any;
    private transferTypeList:IConstants;
    private transferType: IConstants;

    constructor(private $scope: ng.IScope,
                private accountConstants: any,
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
      this.transferTypeList = this.accountConstants.financialYearCloseTransferType;
      console.log("Transfer type");
      console.log(this.transferTypeList);
      this.transferType = this.transferTypeList[0];
      console.log(this.transferType);
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
        this.startDate="";
        this.endDate="";
      this.$state.go('financialAccountYear.financialAccountYearClosing');
      this.addButtonName = this.currentFinancialAccountYear == null ? "Add" : "Save Edit";
      this.newFinancialAccountYear=<IFinancialAccountYear>{};
    }



      private add() {
          this.financialAccountYearService.closeCurrentYearAndCreateNewYear(this.startDate, this.endDate, this.transferType.id)
              .then((financialAccountYearList:IFinancialAccountYear[])=>{
                 if(financialAccountYearList!=undefined){
                     this.financialAccountYears=[];
                     this.financialAccountYears = financialAccountYearList;
                 }

              });
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