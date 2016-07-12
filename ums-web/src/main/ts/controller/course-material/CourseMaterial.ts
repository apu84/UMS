module ums {
  export class CourseMaterial {
    public static $inject = ['$scope', '$stateParams'];

    constructor(private $scope: any, private $stateParams: any) {
      var semesterName = this.$stateParams["1"];
      var courseNo = this.$stateParams["2"];

      angular.module('FileManagerApp').config(
          ['fileManagerConfigProvider', (config) => {
            var defaults = config.$get();
            config.set(
                {
                  appName: 'Course Materials',
                  listUrl: 'academic/courseMaterial/semester/' + semesterName + "/course/" + courseNo,
                  tplPath: 'views/file-manager',
                  pickCallback: function (item) {
                    var msg = 'Picked %s "%s" for external use'
                        .replace('%s', item.type)
                        .replace('%s', item.fullPath());
                    window.alert(msg);
                  },

                  allowedActions: angular.extend(
                      defaults.allowedActions, {
                        pickFiles: false,
                        pickFolders: false,
                      }),
                });
          }]);

    }
  }

  UMS.controller("CourseMaterial", CourseMaterial);
}