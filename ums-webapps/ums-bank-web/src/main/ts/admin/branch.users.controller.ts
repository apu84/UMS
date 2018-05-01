module ums {
  export class BranchUsersController {
    public static $inject = ['$scope', 'BankService', '$stateParams', '$modal'];
    private branch: BankBranch;
    private users: BranchUser[];
    public reloadReference: ReloadRef = {reloadList: false};

    constructor($scope: ng.IScope,
                private bankService: BankService,
                $stateParams: any,
                private $modal: any) {
      this.bankService.getBranch($stateParams["id"]).then((branch: BankBranch) => {
        this.branch = branch;
        this.populateBranchUsers(this.branch.id);
      });

      // $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
      //   if (newVal.reloadList) {
      //     this.populateBranchUsers(this.branch.id);
      //     this.reloadReference.reloadList = false;
      //   }
      // }, true);
    }

    private populateBranchUsers(branchId: string): void {
      this.bankService.getUsers(branchId).then((users: BranchUser[]) => {
        this.users = users;
      });
    }

    public editUser(user?: BranchUser): void {
      this.$modal.open({
        templateUrl: 'views/admin/edit.user.html',
        controller: EditUser,
        resolve: {
          branch: () => this.branch,
          user:  () => user,
          reload: () => this.reloadReference
        }
      });
    }

    public deleteUser(user?: BranchUser): void {
      this.bankService.deleteUser(user).then((result: boolean) => {
        if (result) {
          this.populateBranchUsers(this.branch.id);
        }
      });
    }
  }
  UMS.controller("BranchUsersController", BranchUsersController);
}
