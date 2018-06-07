module ums {
  export class BranchController {
    public static $inject = ['$scope', '$stateParams', '$modal', 'BankService'];
    private bank: Bank;
    private branches: BankBranch[];
    public reloadReference: ReloadRef = {reloadList: false};

    constructor($scope: ng.IScope,
                $stateParams: any,
                private $modal: any,
                private bankService: BankService) {
      this.bankService.getBank($stateParams["id"]).then((bank: Bank) => {
        this.bank = bank;
        this.populateBranches(this.bank.id);
      });

      $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
        if (newVal.reloadList) {
          this.populateBranches(this.bank.id);
          this.reloadReference.reloadList = false;
        }
      }, true);
    }

    private populateBranches(bankId: string): void {
      this.bankService.getBranches(bankId).then((branches: BankBranch[]) => {
        this.branches = branches;
      });
    }

    public editBranch(branch?: BankBranch): void {
      this.$modal.open({
        templateUrl: 'views/admin/edit.branch.html',
        controller: EditBranch,
        resolve: {
          bank: () => this.bank,
          branch: () => branch,
          reload: () => this.reloadReference
        }
      });
    }

    public deleteBranch(branch?: BankBranch): void {
      this.bankService.deleteBranch(branch).then((result: boolean) => {
        if (result) {
          this.populateBranches(this.bank.id);
        }
      });
    }
  }

  UMS.controller('BranchController', BranchController);
}
