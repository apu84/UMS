module ums {
  export interface RowData {
    id: any;
  }

  export interface JqGridApi {
    insertRows?(rows: Array<RowData>): void;
    clear?() : void;
    refresh?(): void;
    getRowData?(rowId: string): any;
    editGridRow?(rowId: string): void;
    toggleMessage?(message?: string): void
    resize?(): void;
    gridElement?(element: JQuery);
    gridData?(data: any);
    gridEditActions?: GridEditActions;
  }

  export interface GridEditActions {
    insert(rowData: RowData): void;
    edit(rowData: RowData): void;
    remove(rowData: RowData): void;
    getColumnModel(): any;
    decorateScope():GridConfig;
  }

  export interface JqGrid {
    api?: JqGridApi;
  }
}