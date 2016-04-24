module ums {
  export interface RowData {
    id: any;
  }

  export interface JqGridApi {
    insert(rows: Array<RowData>): void;
    clear() : void;
    refresh(): void;
    getRowData(rowId: string): any;
    editRow(rowId: string, event: Event): void;
    editGridRow(rowId: string): void;
    toggleMessage(message?: string): void
    resize(): void;
  }

  export interface JqGrid {
    api?: JqGridApi;
  }
}