module ums {
  export class ColumnSorter implements ng.IDirective {
    public bindToController: boolean = true;
    public controller: any = ColumnSorterController;
    public controllerAs: string = 'vm';
    public restrict: string = "A";
    public scope = {
      columnSorter: '@'
    };
  }

  class ColumnSorterController {
    public static $inject = ['$element'];
    private sort: string = 'asc';

    public columnSorter: Function;

    constructor($element: ng.IAugmentedJQuery) {
      $element.on('click', () => {
        this.sort = (this.sort === 'asc' ? 'desc' : 'asc');
        this.columnSorter(this.sort);
      });
    }
  }

  UMS.directive("columnSorter", () => new ColumnSorter());
}
