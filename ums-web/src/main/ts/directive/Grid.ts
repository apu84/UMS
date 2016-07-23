///<reference path="../grid/JqGridApi.ts"/>
///<reference path="../grid/JqGridApiImpl.ts"/>
///<reference path="../grid/InlineNavigationOptions.ts"/>
module ums {
  export interface JqGridScope extends ng.IScope {
    config: JQueryJqGridOptions;
    data: any;
    insert: any;
    grid: JqGrid;
    addnew: boolean;
    inlineNavOptions: any;
  }

  export class Grid implements ng.IDirective {
    public restrict: string = 'A';
    public replace: boolean = true;

    public scope = {
      config: '=',
      data: '=',
      grid: '='
    };

    public link = (scope: JqGridScope, element: JQuery, attrs: any) => {
      var table, div;

      scope.$watch('config', (value: GridConfig) => {
        element.empty();
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

        scope.grid.api.gridElement(table);


        var editOptions = {
          editCaption: "The Edit Dialog",
          closeOnEscape: true,
          closeAfterEdit: true,
          errorTextFormat: function (data) {
            return 'Error: ' + data.responseText
          },
          onclickSubmit: function (row, data) {
            if (scope.grid.api.gridEditActions
                && scope.grid.api.gridEditActions.edit) {
              scope.grid.api.gridEditActions.edit(data);
            }
          }
        };

        var addOptions = {
          closeOnEscape: true,
          closeAfterEdit: true,
          errorTextFormat: function (data) {
            return 'Error: ' + data.responseText
          },
          onclickSubmit: function (row, data) {
            if (scope.grid.api.gridEditActions
                && scope.grid.api.gridEditActions.insert) {
              scope.grid.api.gridEditActions.insert(data);
            }
          }
        };

        var deleteOptions = {
          errorTextFormat: function (data) {
            return 'Error: ' + data.responseText
          },
          onclickSubmit: function (row, data) {
            if (scope.grid.api.gridEditActions
                && scope.grid.api.gridEditActions.remove) {
              scope.grid.api.gridEditActions.remove(data);
            }
          }
        };

        table.jqGrid('navGrid', '#' + attrs.pagerid,
            {
              edit: true,
              add: true,
              del: true,
              search: true,
              refresh: true,
              view: false,
              position: "left",
              cloneToTop: false
            },
            editOptions, addOptions, deleteOptions);

        table.jqGrid('bindKeys', {
          "onEnter": function (rowId) {
            table.jqGrid('editGridRow', rowId, editOptions);
          }
        });

        $(document).keydown(function (evt: any) {
          // alt + n to create new row dialog
          if (evt.altKey && evt.keyCode == 78) {
            table.jqGrid('editGridRow', 'new', addOptions);
          }
        });

        $(element).focus();


      });


      scope.$watch('data', function (value) {
        table.jqGrid('setGridParam', {data: value})
            .trigger('reloadGrid');
        scope.grid.api.gridData(scope.data);
      });


    };

    constructor() {
    }
  }

  UMS.directive('jqGrid', [() => {
    return new Grid();
  }]);

}