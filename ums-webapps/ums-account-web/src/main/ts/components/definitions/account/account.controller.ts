module ums {
  import IAccountBalance = ums.IAccountBalance;

    export class AccountController {

    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'employeeService', 'AccountBalanceService'];

    private groups: IGroup[];
    private selectedGroup: IGroup;
    private employeeNames: string[];
    private totalAccountSize: number;
    private groupMapWithGroupid: any;
    private account: IAccount;
    private existingAccounts: IAccount[];
    private itemsPerPage: number;
    private pageNumber: number;
    private currentPage: number;
    private searchBar: boolean;
    private searchValue: string;
    private ascendingOrDescendingType: AscendingOrDescendingType;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService,
                private employeeService: EmployeeService,
                private accountBalanceService: AccountBalanceService) {

      this.initialize();

    }

    public pageChanged(pageNumber: number) {
      console.log(pageNumber);
      this.pageNumber = pageNumber;
      if (this.pageNumber != undefined || this.pageNumber!=null)
        this.getPaginatedAccounts();
    }

    public initialize() {
      this.searchBar = false;
      this.itemsPerPage = 15;
      this.pageNumber = 1;
      this.searchValue = "";
      this.currentPage = 1;
      this.ascendingOrDescendingType = AscendingOrDescendingType.DESC;
      this.loadAllGroups();
      this.getTotalAccountSize();
      this.selectedGroup = <IGroup>{};
    }

    public loadAllGroups() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        this.groups = groups;
        this.groupMapWithGroupid = {};
        console.log("Groups");
        this.groups.forEach((g: IGroup) => this.groupMapWithGroupid[g.groupCode] = g);
        this.getPaginatedAccounts();
        this.assignGroupObjectsToGroup();
      });
    }

    private assignGroupObjectsToGroup(){
        this.groups.forEach((g:IGroup)=> g.mainGroupObject=this.groupMapWithGroupid[g.mainGroup]);
        console.log(this.groups);
    }

    public getTotalAccountSize() {
      this.accountService.getSize().then((size: number) => {
        this.totalAccountSize = size;
        console.log(size);
      });
    }

    public addModalClicked() {
      this.account = <IAccount>{};
      this.account.yearOpenBalanceType = BalanceType.Dr;
      this.account.yearOpenBalance=0;
      this.setFocusOnTheModal();
    }

   private setFocusOnTheModal(){
       $("#addModal").on('shown.bs.modal', ()=>{
           $("#accountName").focus();
       });
   }

    public showSearchSection() {
      console.log("In the show search section");
      this.searchBar = true;
    }

    public search() {
      this.accountService.getAccountsByAccountName(this.searchValue).then((accounts: IAccount[]) => {
        if (accounts == undefined)
          this.notify.error("Error in fetching data");
        else {
          this.existingAccounts = [];
          accounts.forEach((a: IAccount) => a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName);
          this.existingAccounts = accounts;
        }
      });
    }

    public fetchAccountsInAscendingOrder(){
        this.ascendingOrDescendingType = AscendingOrDescendingType.ASC;
        this.getPaginatedAccounts();
    }

    public fetchAccountsInDescendingOrder(){
        this.ascendingOrDescendingType = AscendingOrDescendingType.DESC;
        this.getPaginatedAccounts();
    }


    public showListView() {
      this.searchValue = "";
      this.searchBar = false;
      this.currentPage = 1;
      this.pageNumber = 1;
      this.getPaginatedAccounts();
    }

    private setPage(pageNo: number) {
      this.pageNumber = pageNo;
    }

    public add() {
      this.account.accGroupCode = this.selectedGroup.groupCode;
      let accountBalance:IAccountBalance = <IAccountBalance>{};
      accountBalance.yearOpenBalanceType = this.account.yearClosingBalanceType;
      accountBalance.yearOpenBalance = this.account.yearOpenBalance;
      accountBalance.yearOpenBalanceType = this.account.yearOpenBalanceType;
      if (this.account.id == null)
        $("#addButton").focus();
      console.log("Page number");
      console.log(this.pageNumber);
      //todo configure account balance information
      this.accountService.saveAccountPaginated(this.account, accountBalance, this.itemsPerPage, this.pageNumber, this.ascendingOrDescendingType).then((accounts: IAccount[]) => {
        if (accounts != undefined) {
          this.existingAccounts = [];
          accounts.forEach((a: IAccount) => a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName);
          this.existingAccounts = accounts;
          this.getTotalAccountSize();
        }

      });
    }

    public edit(account:IAccount){
      this.account = account;
      this.selectedGroup = this.groupMapWithGroupid[this.account.accGroupCode];
      this.account.yearOpenBalance=0;
      this.account.yearOpenBalanceType = BalanceType.Dr;

      this.accountBalanceService.getAccountBalance(this.account.id).then((balance:number)=>{
        this.account.yearOpenBalance=balance;
      });
    }

    public getPaginatedAccounts() {
        console.log("Getting all paginated accounts");
        console.log(this.ascendingOrDescendingType);
      this.accountService.getAllPaginated(this.itemsPerPage>0?this.itemsPerPage: 15, this.pageNumber, this.ascendingOrDescendingType).then((accounts: IAccount[]) => {
        if (accounts == undefined)
          this.notify.error("Error in fetching data");
        else {
          this.existingAccounts = [];
          accounts.forEach((a: IAccount) => {
            console.log(a.accGroupCode);
            a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName
          });
          this.existingAccounts = accounts;
        }
      })
    }

    public getChartOfAccountsReport() {
      this.accountService.generateChartOfAccountsReport();
    }
  }

  UMS.controller("AccountController", AccountController);
}