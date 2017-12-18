module ums {
  export class GridDecorator {
    public static decorate(toDecorate: GridEditActions): any {
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

  export class LoadCompleteDecorator{
    public static decorate(toDecorate: GridEditActions,loadComplete:LoadComplete): any {
      toDecorate.decorateScope().gridOptions.loadComplete=loadComplete.loadComplete();
      return toDecorate;
    }
  }
  export class GridCompleteDecorator{
    public static decorate(toDecorate: GridEditActions,gridComplete:GridComplete): any {
      toDecorate.decorateScope().gridOptions.gridComplete=gridComplete.gridComplete();
      return toDecorate;
    }
  }
  export class RowAttributeDecorator{
    public static decorate(toDecorate: GridEditActions,rowAttribute:RowAttribute): any {
      toDecorate.decorateScope().gridOptions.rowattr=rowAttribute.rowattr;
      return toDecorate;
    }
  }

}