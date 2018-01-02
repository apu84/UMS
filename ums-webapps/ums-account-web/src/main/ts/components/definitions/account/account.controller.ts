module ums {
  export class AccountController {

    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService'];

    private groups: IGroup[];
    private selectedGroup: IGroup;
    private totalAccountSize: number;
    private groupMapWithGroupid: any;
    private account: IAccount;
    private existingAccounts: IAccount[];
    private itemsPerPage: number;
    private pageNumber: number;
    private searchBar: boolean;
    private searchValue: string;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService) {

      this.initialize();

    }

    public pageChanged(pageNumber: number) {
      this.pageNumber = pageNumber;
    }

    public initialize() {
      this.searchBar = false;
      this.itemsPerPage = 15;
      this.pageNumber = 1;
      this.searchValue = "";
      this.loadAllGroups();
      this.getTotalAccountSize();
      this.selectedGroup = <IGroup>{};
      this.getPaginatedAccounts();
    }

    public loadAllGroups() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        this.groups = groups.filter((g: IGroup) => g.mainGroup != "0");
        this.groupMapWithGroupid = {};
        this.groups.forEach((g: IGroup) => this.groupMapWithGroupid[g.groupCode] = g);
        console.log("Map");
        console.log(this.groupMapWithGroupid);
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
      this.getPaginatedAccounts();
    }

    public add() {
      this.account.accGroupCode = this.selectedGroup.groupCode;
      this.accountService.saveAccountPaginated(this.account, 10, 1).then((accounts: IAccount[]) => {
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
      this.accountService.getAllPaginated(this.itemsPerPage, this.pageNumber).then((accounts: IAccount[]) => {
        if (accounts == undefined)
          this.notify.error("Error in fetching data");
        else {
          this.existingAccounts = [];
          accounts.forEach((a: IAccount) => a.accGroupName = this.groupMapWithGroupid[a.accGroupCode].groupName);
          this.existingAccounts = accounts;
        }
      })
    }
  }

  UMS.controller("AccountController", AccountController);
}