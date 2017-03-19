module ums {
  export class ColumnSorter implements ng.IDirective {
    public bindToController: boolean = true;
    public controller: any = ColumnSorterController;
    public controllerAs: string = 'vm';
    public restrict: string = "A";
    public scope = {
      columnSorter: '&'
    };
  }

  class ColumnSorterController {
    public static $inject = ['$element'];
    private sort: string = 'asc';

    public columnSorter: Function;

    constructor($element: ng.IAugmentedJQuery,attrs:any) {

      $element.on('click', () => {

        console.log("inside onclick of columnSorter");
        if (this.sort === 'asc') {
          this.sort = 'desc';
          $element.removeClass('headerSortDown');
          $element.addClass('headerSortUp');
        } else {
          this.sort = 'asc';
          $element.removeClass('headerSortUp');
          $element.addClass('headerSortDown');
        }
        console.log("inside onclick of columnSorter"+ this.sort+"-------");
        console.log(this);
        console.log(this.columnSorter);
        // console.log(this.columnSorter());
        this.columnSorter()(this.sort);
      });
    }
  }

  UMS.directive("columnSorter", () => new ColumnSorter());
}

