module ums {

  /** Represent a Filter Entry
   * An example can be  fieldName = name, operator = contains, fieldValue = ifti
   */
  export interface IFilter{
    fieldName : string;
    operator : string;
    fieldValue: string;
  }

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
      tableprefix:"@",
      ipp:"@",   /* Item Per Pate */
      dataservice:"&",
      datanotify: "&"
    };


    public transclude: string = 'element';
    public replace: boolean = false;
    public compile= (tElement, tAttrs) => {

      var parentElement = tElement.parent();
      return function (scope, element, attrs, ctrl, transclude) {

        transclude(scope.$new(), function (clone, scope) {

          parentElement.append(clone);
          scope.pagination = {};
          scope.pagination.currentPage = 1;
          scope.filterlist = new Array <IFilter>();
          scope.data = {
            readOnlyMode: Boolean,
            showList: Boolean,
            showDelete: Boolean,
            showSave: Boolean
          };

          scope.data.showList = false;
          scope.data.showDelete = false;
          scope.data.showSave = false;

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
          scope.pageChanged = function (pageNumber: number): any {
            scope.fetchData(pageNumber);
          }

          /**
           * Show the action Panel  ( Panel contains view, edit icon in each row )
           */
          scope.showActionPanel = function (prefix: string, index: number): any {
            $("#" + prefix + index).show();
          }

          /**
           * Hide the action Panel  ( Panel contains view, edit icon in each row )
           */
          scope.hideActionPanel = function (prefix: string, index: number): any {
            $("#" + prefix + index).hide();
          }

          /**
           * Clicking on the view icon of  a row wil open the editor in no edit mode in the record editor.
           */
          scope.viewRecord = function (recordId: any): any {
            scope.getRecord(recordId, true);
            scope.data.showList = true;
            scope.data.showDelete = true;
          }

          /**
           * Show Record Entry Panel (Will be trigger when user click on the (+) icon
           * From panel header icon group
           */
          scope.showRecordPanelForNewEntry = function() : void {
            scope.data.showDelete = false;
            scope.data.showList = false;
            scope.data.showSave = true;
            scope.showRecordPanel(false);

          }

          /**
           *Clicking on the edit icon of  a row wil open the editor in edit mode in the record editor.
           */
          scope.editRecord = function (recordId: any): any {
            scope.getRecord(recordId, false);
            scope.data.showList = true;
            scope.data.showDelete = true;
          }

          scope.getRecord = function (recordId: any, readOnlyMode: boolean): void {
            this.dataservice().getRecord(recordId).then(function successCallback(record) {
              scope.showRecordPanel(readOnlyMode);
              scope.record = record;
            }, function errorCallback(response) {
            });
          }

          /**
           * Search record when the user click ont he search button from the search panel.
           * And  the search action will show the for first page.
           */
          scope.search = function (): any {
            scope.showResultPanel();
            scope.fetchData(1);
          }

          /**
           * Show search result panel
           */
          scope.showResultPanel = function (): any {
            $("#searchPanel_" + scope.tableprefix).hide('slide', {direction: 'left'}, 400);
            $("#listPanel_" + scope.tableprefix).show('slide', {direction: 'right'}, 400);
            $("#entryPanel_" + scope.tableprefix).hide('slide', {direction: 'right'});
            scope.data.showDelete = false;
            scope.data.showList = false;
          }

          /**
           * Show Search Panel
           */
          scope.showSearchPanel = function (): any {
            $("#searchPanel_" + scope.tableprefix).show('slide', {direction: 'left'}, 400);
            $("#listPanel_" + scope.tableprefix).hide('slide', {direction: 'right'}, 400);
            $("#entryPanel_" + scope.tableprefix).hide('slide', {direction: 'right'}, 400);
            $("#pageTitle_" + scope.tableprefix).html("Search " + scope.tableprefix);
          }

          /**
           * Show Record Panel
           */
          scope.showRecordPanel = function (readOnlyMode: boolean): any {
            $("#searchPanel_" + scope.tableprefix).hide('slide', {direction: 'left'}, 400);
            $("#listPanel_" + scope.tableprefix).hide('slide', {direction: 'right'}, 400);
            $("#entryPanel_" + scope.tableprefix).show('slide', {direction: 'right'}, 400);
            $("#pageTitle_" + scope.tableprefix).html("New " + scope.tableprefix);
            scope.data.readOnlyMode = readOnlyMode;
            scope.record = {};
          }

          /**
           * Responsible for fetch data from database with the help of service's
           * fetchDataForPaginationTable method.
           */

          scope.fetchData = function (pageNumber: number): any {
            this.dataservice().fetchDataForPaginationTable()(pageNumber, scope.orderBy, scope.ipp, scope.filterlist).then((resultData: any) => {
              scope.records = resultData.entries;
              scope.total = resultData.total;
            });
          }

          /**
           * Save Record.
           */
          scope.saveRecord = function (): void {
            if (scope.record.id === undefined) {
              scope.createNewRecord();
            } else {
              scope.updateRecord();
            }
          }

          /**
           * Create a new record
           */
          scope.createNewRecord = function (): void {
            var self = this;
            this.dataservice().createNewSupplier(scope.record).then(function successCallback(response) {
              scope.datanotify().show(response);
              scope.record = {};
            }, function errorCallback(response) {
              scope.datanotify().error(response);
            });
          }

          /**
           * Update an existing record
           */
          scope.updateRecord = function (): void {
            this.dataservice().updateSupplier(scope.record).then((response) => {
              scope.datanotify().show(response);
            }, (response) => {
              scope.datanotify().error(response);
              console.error(response);
            });
          }

          /**
           * Delete the currently selected record
           */
          scope.deleteRecord = function(): void {
            var deleteConfirmation = confirm("Are you sure, you want to delete selected record ?");
            if (deleteConfirmation == true) {
              this.dataservice().deleteRecord(scope.record.id).then((response) => {
                scope.fetchData(scope.pagination.currentPage);
                scope.showResultPanel();
                scope.datanotify().show(response);
              }, (response) => {
                scope.datanotify().error(response);
                console.error(response);
              });
            }
          }
        });
      }
    }
  }

  UMS.directive("tablePaginatorWrapper", () => new TablePaginatorWrapper());
}
