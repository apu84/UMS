module ums {
  import IAccountBalance = ums.IAccountBalance;

    export class AccountController {

    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService', '$timeout', 'employeeService'];

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

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService,
                private $timeout: ng.ITimeoutService, private employeeService: EmployeeService) {

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
      this.loadAllGroups();
      this.getTotalAccountSize();
      this.selectedGroup = <IGroup>{};
    }

    public loadAllGroups() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        this.groups = groups.filter((g: IGroup) => g.mainGroup != "0");
        this.groupMapWithGroupid = {};
        console.log("Groups");
        console.log(groups);
        this.groups.forEach((g: IGroup) => this.groupMapWithGroupid[g.groupCode] = g);
        this.getPaginatedAccounts();
      });
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
      /* this.employeeService.getAll().then((employees: Employee[]) => {
         this.employeeNames = [];
         employees.forEach((e: Employee) => this.employeeNames.push(e.employeeName));
       });*/
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
      this.accountService.saveAccountPaginated(this.account, accountBalance, this.itemsPerPage, 1).then((accounts: IAccount[]) => {
        if (accounts == undefined)
          this.notify.error("Error in saving data");
        else {
          this.existingAccounts = [];
          accounts.forEach((a: IAccount) => a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName);
          this.existingAccounts = accounts;
          this.getTotalAccountSize();
        }
      });
    }

    public getPaginatedAccounts() {
      this.accountService.getAllPaginated(this.itemsPerPage>0?this.itemsPerPage: 15, this.pageNumber).then((accounts: IAccount[]) => {
        if (accounts == undefined)
          this.notify.error("Error in fetching data");
        else {
          this.existingAccounts = [];
          console.log("Accounts");
          console.log(accounts);
          accounts.forEach((a: IAccount) => a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName);
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