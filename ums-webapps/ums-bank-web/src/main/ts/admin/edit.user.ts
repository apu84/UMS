module ums {
  interface UserScope extends ng.IScope {
    user: BranchUser;
    ok: Function;
    editUser: Function;
    editMode: boolean;
    designations: BankDesignation[];
    branches: BankBranch[];
    getBranchName: Function;
    getDesignationName: Function;
    getDesignations: Function;
    getBranches: Function;
    userBranchName: string;
    userDesignationName: string;
  }

  export class EditUser {
    public static $inject = ['$scope', '$q', 'BankService', '$modalInstance', 'branch', 'user', 'notify', 'reload'];

    constructor(private $scope: UserScope,
                private $q: ng.IQService,
                private bankService: BankService,
                private $modalInstance: any,
                private branch: BankBranch,
                private user: BranchUser,
                private notify: Notify,
                private reload: ReloadRef) {
      if (user) {
        this.$scope.user = user;
      }
      else {
        this.$scope.user = {
          userId: undefined,
          branchId: branch.id,
          name: undefined,
          bankDesignationId: undefined,
          email: undefined,
          id: undefined
        }
      }

      this.$scope.editMode = true;
      this.$scope.ok = this.ok.bind(this);
      this.$scope.editUser = this.editUser.bind(this);
      this.$scope.getBranchName = this.getBranchName.bind(this);
      this.$scope.getDesignationName = this.getDesignationName.bind(this);

      this.bankService.getDesignations().then((designations: BankDesignation[]) => {
        this.$scope.designations = designations;
      });
      this.bankService.getBranches(branch.bankId).then((branches: BankBranch[]) => {
        this.$scope.branches = branches;
      });
    }

    private ok(): void {
      this.reload.reloadList = true;
      this.$modalInstance.dismiss('cancel');
    }

    public editUser(): void {
      if (this.$scope.user.id) {
        this.updateUser();
      }
      else {
        this.bankService.createUser(this.$scope.user).then((user: BranchUser) => {
          if (user) {
            this.$scope.user = user;
            this.$scope.editMode = false;
            this.setBranchNameAndDesignation(user);
          }
          else {
            this.notify.error('Failed to update');
          }
        });
      }
    }

    private updateUser(): void {
      this.bankService.updateUser(this.$scope.user).then((user: BranchUser) => {
        if (user) {
          this.$scope.user = user;
          this.$scope.editMode = false;
          this.setBranchNameAndDesignation(user);
        }
        else {
          this.notify.error('Failed to update');
        }
      });
    }

    private setBranchNameAndDesignation(user: BranchUser): void {
      this.getBranchName(user.branchId).then((branchName) => {
        this.$scope.userBranchName = branchName;
      });
      this.getDesignationName(user.bankDesignationId).then((designationName) => {
        this.$scope.userDesignationName = designationName;
      });
    }

    private getBranchName(branchId: string): ng.IPromise<string> {
      return this.bankService.getBranch(branchId).then((branch: BankBranch) => {
        return branch.name;
      })
    }

    private getDesignationName(designationId: string): ng.IPromise<string> {
      return this.bankService.getDesignation(designationId).then((desigantion: BankDesignation) => {
        return desigantion.name;
      });
    }
  }

  UMS.service("EditUser", EditUser);
}
