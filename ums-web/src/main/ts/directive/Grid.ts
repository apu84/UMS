///<reference path="grid/JqGridApi.ts"/>
///<reference path="grid/JqGridApiImpl.ts"/>
///<reference path="grid/InlineNavigationOptions.ts"/>
module ums {
  export interface JqGridScope extends ng.IScope {
    config: JQueryJqGridOptions;
    data: any;
    insert: any;
    grid: {
      api: JqGridApi;
    };
    addnew: boolean;
    inlineNavOptions: any;
  }

  export class JqGrid implements ng.IDirective {
    public restrict: string = 'A';
    public replace: boolean = true;

    public scope = {
      config: '=',
      data: '=',
      insert: '=?',
      grid: '=',
      addnew: "=",
      inlineNavOptions: '=?'
    };

    public link = (scope: JqGridScope, element: JQuery, attrs: any) => {
      var table, div, messageDisplayed: boolean = false;

      scope.$watch('config', (value) => {
        element.children().empty();
        table = angular.element('<table id="' + attrs.gridid + '"></table>');

        element.append(table);

        if (attrs.pagerid) {
          value.pager = '#' + attrs.pagerid;
          var pager = angular.element(value.pager);
          if (pager.length == 0) {
            div = angular.element('<div id="' + attrs.pagerid + '"></div>');
            element.append(div);
          }
        }
        table.jqGrid(value);

        if (scope.addnew == true) {
          table.jqGrid("inlineNav", '#' + attrs.pagerid, scope.inlineNavOptions);
        }

        // allow to insert(), clear(), refresh() the grid from
        // outside (e.g. from a controller). Usage:
        //   view:  <ng-jqgrid … api="gridapi">
        //   ctrl:  $scope.gridapi.clear();
        scope.grid.api = new JqGridApiImpl(table, scope.data);
      });


      scope.$watch('data', function (value) {
        table.jqGrid('setGridParam', {data: value})
            .trigger('reloadGrid');
      });
    };

    constructor() {
    }
  }

  UMS.directive('jqGrid', [() => {
    return new JqGrid();
  }]);

}