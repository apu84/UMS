module ums {
  export interface AddParams {
    useFormatter? : boolean;
    position?: string;
  }
  export interface InlineNavigationOptions {
    keys?: boolean;
    edit?: boolean;
    save?: boolean;
    cancel?: boolean;
    addParams?: AddParams;
    restoreAfterSelect?: boolean
  }
}