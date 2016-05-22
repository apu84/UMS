module ums {
  export class GridDecorator {
    public static decorate(toDecorate: GridEditActions): GridConfig {
      toDecorate.decorateScope().gridOptions = new GridOptions();
      toDecorate.decorateScope().gridOptions.colModel = toDecorate.getColumnModel();
      toDecorate.decorateScope().grid = {
        api: {}
      };
      toDecorate.decorateScope().grid.api = new JqGridApiImpl();
      toDecorate.decorateScope().grid.api.gridEditActions = toDecorate;

      toDecorate.decorateScope().gridOptions.ondblClickRow = (rowid: string, iRow: number, iCol: number, e: Event) => {
        toDecorate.decorateScope().grid.api.editGridRow(rowid);
      };

      return toDecorate;
    }

  }
}