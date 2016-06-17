module ums {

  interface ModalScope extends ng.IScope {
    header: string;
    body: string;
    callbackButtonLeft: string;
    callbackButtonRight: string;
    handler: string;
  }

  export class Modal implements ng.IDirective {
    constructor() {

    }

    public restrict:string = 'AE';

    public transclude:boolean = true;

    public scope:any = {
      header: '=',
      body: '=',
      callbackButtonLeft: '&ngClickLeftButton',
      callbackButtonRight: '&ngClickRightButton',
      handler: '='
    };

    public controller = ($scope:ModalScope)=> {
      //$scope.handler = 'lolo';
    };

    public templateUrl:string = './views/common/modal.html';
  }

  UMS.directive("modal", [() => {
    return new Modal();
  }]);
}