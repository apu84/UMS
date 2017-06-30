module ums {
  interface AddDuesScope extends ng.IScope {
    duesCategories: FeeCategory[];
    ok: Function;
    addDues: Function;
    studentDue: StudentDue;
  }
  export class AddDues {
    public static $inject = ['$scope', 'StudentDuesService', '$modalInstance'];

    constructor(private $scope: AddDuesScope,
                private studentDuesService: StudentDuesService,
                private $modalInstance: any) {
      this.initialize();
      studentDuesService.getFeeCategories().then(
          (feeCategories: FeeCategory[]) => {
            this.$scope.duesCategories = feeCategories;
          });
      this.$scope.ok = this.ok.bind(this);
      this.$scope.addDues = this.addDues.bind(this);
    }

    public addDues(): void {
      this.studentDuesService.addDues(this.$scope.studentDue).then((success) => {
        if (success) {
          this.initialize();
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
    }
  }
}
