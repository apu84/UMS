module ums {
  export class BranchController {
    public static $inject = ['$stateParams', '$modal', 'BankService'];
    private bank: Bank;
    private branches: BankBranch[];
    public reloadReference: ReloadRef = {reloadList: false};

    constructor($stateParams: any,
                private $modal: any,
                private bankService: BankService) {
      this.bankService.getBank($stateParams["1"]).then((bank: Bank) => {
        this.bank = bank;
        this.populateBranches(this.bank.id);
      });
    }

    private populateBranches(bankId: string): void {
      this.bankService.getBranches(bankId).then((branches: BankBranch[]) => {
        this.branches = branches;
      });
    }

    public editBranch(branch?: BankBranch): void {
      this.$modal.open({
        templateUrl: 'views/admin/editBranch.html',
        controller: EditBranch,
        resolve: {
          bank: () => this.bank,
          branch: () => branch,
          reload: () => this.reloadReference
        }
      });
    }
  }

  UMS.controller('BranchController', BranchController);
}