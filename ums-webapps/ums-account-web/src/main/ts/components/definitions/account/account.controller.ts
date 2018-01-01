module ums {
  export class AccountController {

    public static $inject = ['$scope', '$modal', 'notify', 'AccountService', 'GroupService'];

    private groups: IGroup[];
    private selectedGroup: IGroup;
    private totalAccountSize: number;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private accountService: AccountService,
                private groupService: GroupService) {

      this.initialize();

    }

    public initialize() {
      this.loadAllGroups();
      this.getTotalAccountSize();
      this.selectedGroup = <IGroup>{};
    }

    public loadAllGroups() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        console.log("Groupos");
        console.log(groups);
        this.groups = groups.filter((g: IGroup) => g.mainGroup != "0");
      });
    }

    public getTotalAccountSize() {
      this.accountService.getSize().then((size: number) => this.totalAccountSize = size);
    }
  }

  UMS.controller("AccountController", AccountController);
}