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
            console.log(data);
            if (scope.grid.api.gridEditActions
                && scope.grid.api.gridEditActions.edit) {
              scope.grid.api.gridEditActions.edit(data);
            }
          },
          recreateForm: true,
          beforeShowForm: function ($form) {
            $form.find(".FormElement[readonly]")
                .prop("disabled", true)
                .addClass("ui-state-disabled")
                .closest(".DataTD")
                .prev(".CaptionTD")
                .prop("disabled", true)
                .addClass("ui-state-disabled")
          },
          beforeInitData: function($form) {
            scope.grid.api.gridEditActions.beforeShowEditForm($form, table);
          },
          afterShowForm: function ($form) {
            scope.grid.api.gridEditActions.afterShowEditForm($form, table);
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
          },
          addCaption:'Add New.....',
          beforeShowForm: function(form) {
            alert("inside before show form");
            return false;
          },
          afterSubmit:function(response){
            return [false,'',null];
          },
          closeAfterAdd: true
        };

        var deleteOptions = {
          closeAfterEdit: true,
          errorTextFormat: function (data) {
            return 'Error: ' + data.responseText
          },
          onclickSubmit: function (row, data) {
            if (scope.grid.api.gridEditActions
                && scope.grid.api.gridEditActions.remove) {
              scope.grid.api.gridEditActions.remove(data);
            }
          },
          afterSubmit:function(response){
            return [false,'',null];
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