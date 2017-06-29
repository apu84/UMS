module ums {
  export class AddDues {
    public static $inject = ['$scope', 'StudentDuesService'];
    public dues: StudentDue[];
    public filters: Filter[] = [];

    constructor($scope: ng.IScope, private studentDuesService: StudentDuesService) {
      this.navigate();
      $scope.$watch(() => this.filters, ()=> {
        this.studentDuesService.listDues(this.filters).then((dues: StudentDue[]) => {
          this.dues = dues;
        });
      }, true);
    }

    public navigate(url?: string): void {
      this.studentDuesService.listDues(this.filters, url).then((dues: StudentDue[]) => {
        this.dues = dues;
      });
    }
  }

  UMS.controller('AddDues', AddDues);
}
