module ums {
  export class EmitLastRepeaterElement implements ng.IDirective {

    constructor() {
    }
    public restrict: string = 'A';

    public link = (scope, element, attrs)=> {
      if (scope.$last){
        scope.$emit('LastRepeaterElement');
      }
    };
  }

  UMS.directive("emitLastRepeaterElement", [() => new EmitLastRepeaterElement()]);
}