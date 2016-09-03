module ums {
  interface Attributes extends ng.IAttributes {
    disableOperation: string;
  }
  export class DisableOperation implements ng.IDirective {
    constructor() {

    }

    public restrict = 'A';
    public scope = false;
    private defaultDisabledOperations:string = "cut copy paste";

    public link = ($scope:any, element:JQuery, attributes:Attributes) => {
      if (attributes.disableOperation != '') {
        this.defaultDisabledOperations = attributes.disableOperation;
      }
      $(element).bind(this.defaultDisabledOperations, (e) => {
        e.preventDefault();
      });
    };

  }
  UMS.directive('disableOperation', () => new DisableOperation());
}