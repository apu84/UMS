module ums {
  export class GridOptions {
    public datatype: string = "local";
    public editurl: string = 'clientArray';
    public autowidth: boolean = true;
    public rownumbers: boolean = true;
    public height: number = 500;
    public rowList: Array<any> = [];        // disable page size dropdown
    public pgbuttons: boolean = false;     // disable page control like next, back button
    public pgtext: string = null;
    public scroll: boolean = true;
    public scrollrows: boolean = true;
    public currentSelectedRowId: string = '';
    public colModel: any;

    public ondblClickRow(rowid, iRow, iCol, e) {
      // do nothing, will be implemented in calling class
    }

    public onSelectRow = (id: string) => {
      this.currentSelectedRowId = id;
    }
  }
}