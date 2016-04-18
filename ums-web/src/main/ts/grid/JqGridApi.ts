module ums {
  export interface RowData<I> {
    id: I;
  }

  export interface JqGridApi {
    insert(rows: Array<RowData<any>>): void;
    clear() : void;
    refresh(): void;
    getRowData(rowId: string): RowData<any>;
    editRow(rowId: string, event: Event): void;
    editGridRow(rowId: string): void;
    toggleMessage(message?: string): void
    resize(): void;
  }

  export interface JqGrid {
    api?: JqGridApi;
  }
}