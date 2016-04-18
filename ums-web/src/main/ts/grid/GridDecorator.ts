module ums {
  export class GridDecorator implements GridConfig {
    public static decorate(toDecorate: GridConfig): GridConfig {
      toDecorate.gridOptions = new GridOptions();
      toDecorate.inlineNavOptions = new InlineNavigationOptionsImpl();
      toDecorate.grid = {};

      toDecorate.gridOptions.ondblClickRow = (rowid: string, iRow: number, iCol: number, e: Event) => {
        toDecorate.grid.api.editRow(rowid, e);
      };

      return toDecorate;
    }

  }
}