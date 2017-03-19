module ums {
  /**
   * This directive works as a wrapper  for a table with pagination.
   * The main target of this directive is to keep the table data and pagination scope centralize.
   * So that who ever uses pagination need not think about the detail implementation, they will
   * only implement the basic data fetch method.  And other implementation detail will be part of this wrapper directive.
   */
  export class TablePaginatorWrapper implements ng.IDirective {
    constructor() {

    }

    public restrict: string = "E";
    public scope = {
      datafetcher:"&",
      ipp:"@"
    };

    public transclude: string = 'element';
    public replace: boolean = false;
    public compile= (tElement, tAttrs) => {

      var parentElement = tElement.parent();

      return function (scope, element, attrs, ctrl, transclude) {
        transclude(scope.$new(), function (clone, scope) {

          parentElement.append(clone);

          scope.pagination={};
          scope.pagination.currentPage = 1;

          //Sort function which will be called when  a user tries to sort record by pressing column header.
          scope.sort = function (field: string): any {
            return (order: string) => {
              scope.orderBy = "Order by " + field + " " + order;

              //As soon as the user sort a column we will fetch data for the first page
              //and show him the first page.
              scope.pageChanged(scope.pagination.currentPage);
              // scope.pagination.currentPage = 1;

            }

          }


          /**
           * pageChanged will be called from two different source.
           * When users  sort a column
           * When user click on a page number from footer pagination.
           */
          scope.pageChanged = function(pageNumber:number): any{
            scope.datafetcher()(pageNumber,scope.orderBy,scope.ipp).then((resultData: any) => {
                scope.records = resultData.entries;
                scope.total = resultData.total;
              });
          }


          scope.pageChanged(scope.pagination.currentPage);

        });
      }
    }
  }

  UMS.directive("tablePaginatorWrapper", () => new TablePaginatorWrapper());
}