module ums {
  interface BranchScope extends ng.IScope {
    branch: BankBranch;
    ok: Function;
    addBranch: Function;
    editMode: boolean
  }

  export class EditBranch {
    public static $inject = ['$scope', '$q', 'BankService', '$modalInstance', 'bank', 'branch', 'notify', 'reload'];

    constructor(private $scope: BranchScope,
                private $q: ng.IQService,
                private bankService: BankService,
                private $modalInstance: any,
                private bank: Bank,
                private branch: BankBranch,
                private notify: Notify,
                private reload: ReloadRef) {
      if (branch) {
        this.$scope.editMode = true;
        this.$scope.branch = branch;
      }
      else {
        this.$scope.branch = {
          code: undefined,
          location: undefined,
          contactNo: undefined,
          id: undefined,
          bankId: bank.id,
          name: undefined
        };
      }

      this.$scope.ok = this.ok.bind(this);
      this.$scope.addBranch = this.addBranch.bind(this);
    }

    private ok(): void {
      this.reload.reloadList = true;
      this.$modalInstance.dismiss('cancel');
    }

    public addBranch(): void {
      if (this.$scope.editMode) {
        this.updateBranch();
      }
      else {
        this.bankService.createBranch(this.$scope.branch).then((branch: BankBranch) => {
          if (branch) {
            this.$scope.branch = branch;
            this.$scope.editMode = false;
          }
          else {
            this.notify.error('Failed to update');
          }
        });
      }
    }

    private updateBranch(): void {
      this.bankService.updateBranch(this.$scope.branch).then((branch: BankBranch) => {
        if (branch) {
          this.$scope.branch = branch;
          this.$scope.editMode = false;
        }
        else {
          this.notify.error('Failed to update');
        }
      });
    }
  }

  UMS.service("EditBranch", EditBranch);
}