module ums {
  export class FileModel implements ng.IDirective {
    constructor(private $parse: ng.IModelParser) {

    }

    public restrict = 'A';

    public link = ($scope:any, element:any, attrs) => {

      var model = this.$parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', () => {
        $scope.$apply(function () {
          modelSetter($scope, element[0].files[0]);
        });
      });
    }
  }

  UMS.directive('fileModel', ['$parse', ($parse) => new FileModel($parse)])
}