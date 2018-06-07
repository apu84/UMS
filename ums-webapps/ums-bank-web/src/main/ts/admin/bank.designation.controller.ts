module ums {
  export class BankDesignationController {
    public static $inject = ['$scope', 'BankService', '$modal'];
    private designations: BankDesignation[];
    public reloadReference: ReloadRef = {reloadList: false};

    constructor($scope: ng.IScope,
                private bankService: BankService,
                private $modal: any) {
      this.populateDesignationList();

      $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
        if (newVal.reloadList) {
          this.populateDesignationList();
          this.reloadReference.reloadList = false;
        }
      }, true);
    }

    private populateDesignationList() {
      this.bankService.getDesignations().then((designations: BankDesignation[]) => {
        this.designations = designations;
      });
    }

    public editDesignation(designation?: BankDesignation): void {
      this.$modal.open({
        templateUrl: 'views/admin/edit.designation.html',
        controller: EditBankDesignation,
        resolve: {
          designation: () => designation,
          reload: () => this.reloadReference
        }
      });
    }

    public deleteDesignation(designation: BankDesignation): void {
      this.bankService.deleteDesignation(designation).then((result: boolean) => {
        if (result) {
          this.populateDesignationList();
        }
      });
    }
  }

  UMS.controller('BankDesignationController', BankDesignationController);
}
