module ums {
  export class GridConfigImpl implements GridConfig {
    public gridOptions: GridOptions;
    public inlineNavOptions: InlineNavigationOptions;
    public grid: JqGrid;

    public constructor(private context: GridConfig) {
      context.gridOptions = new GridOptions();
      context.inlineNavOptions = new InlineNavigationOptionsImpl();
      context.grid = {};

      context.gridOptions.ondblClickRow = (rowid: string, iRow: number, iCol: number, e: Event) => {
        context.grid.api.editRow(rowid, e);
      };

      console.debug("context: %o", context);
    }

    public setGridOptions(options: GridOptions) {
      this.context.gridOptions = options;
    }

    setInlineNavOptions(navOptions: InlineNavigationOptions): void {
      this.context.inlineNavOptions = navOptions;
    }

    setGrid(grid: JqGrid) {
      this.context.grid = grid;
    }

  }
}