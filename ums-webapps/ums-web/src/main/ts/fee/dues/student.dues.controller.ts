module ums {
  export class StudentDuesController {
    public static $inject = ['$scope', 'StudentDuesService'];
    public dues: StudentDue[];
    public selectedDues: {[key: string]: boolean};
    public selected: string[];

    constructor(private $scope: ng.IScope, private studentDuesService: StudentDuesService) {
      this.listDues();
      this.$scope.$watch(()=> {
        return this.selectedDues;
      }, () => {
        if (this.selectedDues) {
          this.selected = Object.keys(this.selectedDues)
              .map(
                  (key) => {
                    return this.selectedDues[key] ? key : undefined;
                  })
              .filter((el)=> !!el);
        }
      }, true);
    }

    public pay(): void {
      this.studentDuesService.payDues(this.selected).then(
          (success) => {
            if (success) {
              this.listDues();
              this.selected = [];
            }
          }
      );
    }

    private listDues(): void {
      this.studentDuesService.getDues().then(
          (dues: StudentDue[]) => {
            this.dues = dues;
          }
      );
    }
  }

  UMS.controller('StudentDuesController', StudentDuesController);
}
