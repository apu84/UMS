module ums {
  export class CourseMaterial {
    public static $inject = ['$scope', '$stateParams'];

    constructor(private $scope: any, private $stateParams: any) {
      var semesterName = this.$stateParams["1"];
      var courseNo = this.$stateParams["2"];
      var baseUri: string = '/ums-webservice-common/academic/courseMaterial/semester/' + semesterName + "/course/" + courseNo;
      FILEMANAGER_CONFIG.set({
        listUrl: baseUri,
        createFolderUrl: baseUri,
        uploadUrl: baseUri,
        renameUrl: baseUri,
        copyUrl: baseUri,
        moveUrl: baseUri,
        removeUrl: baseUri
      });
    }
  }

  UMS.controller("CourseMaterial", CourseMaterial);
}