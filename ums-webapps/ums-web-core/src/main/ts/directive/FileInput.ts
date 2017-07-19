/**
 * Created by My Pc on 17-Jul-17.
 */
module ums {
  class FileInput implements ng.IDirective {

    static $inject = ['$parse'];

    constructor(private $parse: ng.IParseService) {

    }

    public restrict: string = "A";

    public link = ($scope: any, element: any, attributes: any) => {
      element.bind('change', () => {

        this.$parse(attributes.fileInput)
            .assign($scope, element[0].files);
        $scope.$apply();
      });
    };


  }

  UMS.directive("fileInput", ['$parse', ($parse: ng.IParseService) => new FileInput($parse)]);
}