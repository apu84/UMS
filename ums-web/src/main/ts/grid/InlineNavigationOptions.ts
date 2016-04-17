module ums {
  export interface InlineNavigationOptions {
    keys?: boolean;
    edit?: boolean;
    save?: boolean;
    cancel?: boolean;
    addParams?: {
      useFormatter?: boolean
    }
    restoreAfterSelect?: boolean
  }
}