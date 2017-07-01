module ums {
  interface AddDuesScope extends ng.IScope {
    duesCategories: FeeCategory[];
    ok: Function;
    addDues: Function;
    studentDue: StudentDue;
    editMode: boolean
  }
  export class AddDues {
    public static $inject = ['$scope', 'StudentDuesService', '$modalInstance', 'studentDue', 'notify', 'reload'];

    constructor(private $scope: AddDuesScope,
                private studentDuesService: StudentDuesService,
                private $modalInstance: any,
                private studentDue: StudentDue,
                private notify: Notify,
                private reload: ReloadRef) {
      if (!studentDue) {
        this.initialize();
      }
      else {
        this.$scope.studentDue = studentDue;
        this.$scope.editMode = true;
      }

      studentDuesService.getFeeCategories().then(
          (feeCategories: FeeCategory[]) => {
            this.$scope.duesCategories = feeCategories;
          });
      this.$scope.ok = this.ok.bind(this);
      this.$scope.addDues = this.addDues.bind(this);
    }

    public addDues(): void {
      if (this.$scope.editMode) {
        this.updateDues();
        return;
      }
      this.studentDuesService.addDues(this.$scope.studentDue).then((success) => {
        if (success) {
          this.initialize();
        }
      });
    }

    private updateDues(): void {
      this.studentDuesService.updateDues(this.$scope.studentDue).then((success) => {
        if (success) {
          this.initialize();
          this.$scope.editMode = false;
        }
        else {
          this.notify.error('Failed to update');
        }
      });
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
    }

    private initialize(): void {
      this.$scope.studentDue = {
        studentId: undefined,
        feeCategoryId: undefined,
        payBefore: undefined,
        amount: undefined,
        description: undefined
      };
      this.reload.reloadList = true;
    }
  }
}
