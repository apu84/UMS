module ums {
  export class ListDues {
    public static $inject = ['$scope', 'StudentDuesService', '$modal'];
    public dues: StudentDue[];
    public filters: Filter[] = [];

    constructor($scope: ng.IScope, private studentDuesService: StudentDuesService, private $modal: any) {
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

    public addDues(): void {
      this.$modal.open({
        templateUrl: 'views/fee/dues/add.dues.html',
        controller: AddDues,
        resolve: {
          loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad.load({
              files: [
                'vendors/bootstrap-datepicker/css/datepicker.css',
                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
              ]
            });
          }]
        }
      });
    }
  }

  UMS.controller('ListDues', ListDues);
}
