module ums {
  export class JqGridApiImpl implements JqGridApi {
    resize(): void {
      $(window).bind('resize', () => {
        this.gridElement.jqGrid('setGridWidth',($(window).width()));
      }).trigger('resize');
    }

    private messageDisplayed: boolean = false;

    public insert(rows: Array<RowData<any>>): void {
      if (rows) {
        for (var i = 0; i < rows.length; i++) {
          this.gridData.push(rows[i]);
        }
        this.gridElement.jqGrid('setGridParam', {data: this.gridData})
            .trigger('reloadGrid');
      }
    }

    public clear(): void {
      this.gridData.length = 0;
      this.gridElement.jqGrid('clearGridData', {data: this.gridElement})
          .trigger('reloadGrid');
    }

    public refresh(): void {
      this.gridElement
          .jqGrid('clearGridData')
          .jqGrid('setGridParam', {data: this.gridData})
          .trigger('reloadGrid');
    }

    public getRowData(rowId: string): any {
      return this.gridElement.jqGrid('getRowData', rowId);
    }

    public editRow(rowId: string, event: Event): void {
      if (!event) event = window.event; // get browser independent object
      var element = event.target || event.srcElement;
      jQuery.fn.fmatter.rowactions.call(element, 'edit');
    }

    public editGridRow(rowId: string): void {
      this.gridElement.jqGrid('editGridRow', rowId, {keys: true});
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

    constructor(private gridElement: JQuery,
                private gridData: any) {

    }

  }
}