module ums {
  export class InlineNavigationOptionsImpl implements InlineNavigationOptions {
    public keys: boolean = true;
    public edit: boolean = false;
    public save: boolean = false;
    public cancel: boolean = false;
    public addParams: AddParams = {
      useFormatter: true,
      position: 'last'
    };
    public restoreAfterSelect: boolean = false;
  }
}