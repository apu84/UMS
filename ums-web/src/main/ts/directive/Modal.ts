module ums {

  interface ModalScope extends ng.IScope {
    title: string;
    header: string;
    body: string;
    footer: string;
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
      title: '=',
      header: '=',
      body: '=',
      footer: '=',
      callbackButtonLeft: '&ngClickLeftButton',
      callbackButtonRight: '&ngClickRightButton',
      handler: '='
    };

    public controller = ($scope:ModalScope)=> {
      $scope.handler = 'pop';
    };

    public templateUrl:string = './views/common/modal.html';
  }

  UMS.directive("modal", [() => {
    return new Modal();
  }]);
}