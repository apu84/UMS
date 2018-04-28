module ums {
  interface DesignationScope extends ng.IScope {
    designation: BankDesignation;
    ok: Function;
    addDesignation: Function;
    editMode: boolean
  }

  export class EditBankDesignation {
    public static $inject = ['$scope', '$q', 'BankService', '$modalInstance', 'designation', 'notify', 'reload'];

    constructor(private $scope: DesignationScope,
                private $q: ng.IQService,
                private bankService: BankService,
                private $modalInstance: any,
                private designation: BankDesignation,
                private notify: Notify,
                private reload: ReloadRef) {
      if (designation) {
        this.$scope.designation = designation;
      }
      else {
        this.$scope.designation = {
          code: undefined,
          name: undefined,
          id: undefined
        };
      }
      this.$scope.editMode = true;
      this.$scope.ok = this.ok.bind(this);
      this.$scope.addDesignation = this.addDesignation.bind(this);
    }

    private ok(): void {
      this.reload.reloadList = true;
      this.$modalInstance.dismiss('cancel');
    }

    public addDesignation(): void {
      if (this.$scope.designation.id) {
        this.updateDesignation();
      }
      else {
        this.bankService.createDesignation(this.$scope.designation).then((designation: BankDesignation) => {
          if (designation) {
            this.$scope.designation = designation;
            this.$scope.editMode = false;
          }
          else {
            this.notify.error('Failed to update');
          }
        });
      }
    }

    private updateDesignation(): void {
      this.bankService.updateDesignation(this.$scope.designation).then((designation: BankDesignation) => {
        if (designation) {
          this.$scope.designation = designation;
          this.$scope.editMode = false;
        }
        else {
          this.notify.error('Failed to update');
        }
      });
    }
  }

  UMS.service("EditBankDesignation", EditBankDesignation);
}
