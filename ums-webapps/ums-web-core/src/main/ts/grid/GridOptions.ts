module ums {
  export class GridOptions {
    public datatype: string = "local";
    public editurl: string = 'clientArray';
    public autowidth: boolean = true;
    public rownumbers: boolean = true;
    public height: string = "auto";
    public scroll: boolean = false;
    public scrollrows: boolean = true;
    public rowNum:number = 18;
    public currentSelectedRowId: string = '';
    public colModel: any;
    public loadonce: boolean = true;

    public ondblClickRow(rowid, iRow, iCol, e) { console.debug(rowid);
      // do nothing, will be implemented in calling class
    }

    public onSelectRow = (id: string) => {
      this.currentSelectedRowId = id;
    }

    public loadComplete(){
      //do nothing, will be implemented in calling class
      //Called after the data being loaded.
    }

    public gridComplete(){
      //do nothing, will be implemented in calling class
      //Called after the grid being loaded.
    }

    public rowattr(rowData:RowData){
      //do nothing, will be implemented in calling class
      //return row attribute;
    }
  }
}