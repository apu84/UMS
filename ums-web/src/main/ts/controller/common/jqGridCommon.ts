module ums {
  export class JqGridCommon {
    constructor() {
    }

    public makeReadOnly(gridElement: JQuery, ...fields: string[]): void {
      for (var i = 0; i < arguments.length; i++) {
        gridElement.jqGrid('setColProp', arguments[i], {editoptions: {readonly: "readonly"}});
      }
    }

  }
}