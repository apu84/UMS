module ums {
  export class JqGridApiImpl implements JqGridApi {
    private currentGridElement: JQuery;
    private currentGridData: any;
    public gridEditActions: GridEditActions;

    public gridElement(element: JQuery) {
      this.currentGridElement = element;
    }

    public gridData(data: any) {
      this.currentGridData = data;
    }


    resize(): void {
      $(window).bind('resize', () => {
        this.currentGridElement.jqGrid('setGridWidth', ($(window).width()));
      }).trigger('resize');
    }

    private messageDisplayed: boolean = false;

    public insert(rows: Array<RowData>): void {
      if (rows) {
        for (var i = 0; i < rows.length; i++) {
          this.currentGridData.push(rows[i]);
        }
        this.currentGridElement.jqGrid('setGridParam', {data: this.gridData})
            .trigger('reloadGrid');
      }
    }

    public clear(): void {
      this.gridData.length = 0;
      this.currentGridElement.jqGrid('clearGridData', {data: this.gridElement})
          .trigger('reloadGrid');
    }

    public refresh(): void {
      this.currentGridElement
          .jqGrid('clearGridData')
          .jqGrid('setGridParam', {data: this.gridData})
          .trigger('reloadGrid');
    }

    public getRowData(rowId: string): any {
      return this.currentGridElement.jqGrid('getRowData', rowId);
    }


    public editGridRow(rowId: string): void {
      this.currentGridElement.jqGrid('editGridRow', rowId, {
        editCaption: "The Edit Dialog",
        closeOnEscape: true,
        closeAfterEdit: true,
        errorTextFormat: function (data) {
          return 'Error: ' + data.responseText
        },
        onclickSubmit: (row, data) => {
          if (this.gridEditActions
              && this.gridEditActions.edit) {
            this.gridEditActions.edit(data);
          }
        }
      });
    }

    public toggleMessage(message: string): void {
      if (!this.messageDisplayed) {
        var text = !message ? 'Loading...' : message;
        $(".loading").html(text).show();
        $(".ui-overlay").show();
      } else {
        $(".loading").html('').hide();
        $(".ui-overlay").hide();
      }
      this.messageDisplayed = !this.messageDisplayed;
    }

    constructor() {
    }

  }
}