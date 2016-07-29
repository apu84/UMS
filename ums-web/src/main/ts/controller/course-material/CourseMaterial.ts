module ums {
  export class CourseMaterial {
    public static $inject = ['$scope', '$stateParams'];

    constructor(private $scope: any, private $stateParams: any) {
      var semesterName = this.$stateParams["1"];
      var courseNo = this.$stateParams["2"];
      var baseUri: string = '/ums-webservice-common/academic/courseMaterial/semester/' + semesterName + "/course/" + courseNo;
      var downloadBaseUri: string = '/ums-webservice-common/academic/courseMaterial/download/semester/' + semesterName + "/course/" + courseNo;
      FILEMANAGER_CONFIG.set({
        appName: semesterName + ' > ' + courseNo,
        listUrl: baseUri,
        createFolderUrl: baseUri,
        uploadUrl: baseUri + "/upload",
        renameUrl: baseUri,
        copyUrl: baseUri,
        moveUrl: baseUri,
        removeUrl: baseUri,
        downloadFileUrl: downloadBaseUri,
        downloadMultipleUrl: downloadBaseUri,
        compressUrl: baseUri,
        hidePermissions: true,
        hideOwner: false,
        multipleDownloadFileName: 'CourseMaterial-' + semesterName + "-" + courseNo + '.zip',
        searchForm: true,
        languageSelection: false,
        allowedActions: angular.extend(FILEMANAGER_CONFIG.$get().allowedActions, {
          createAssignmentFolder: true
        })
      });
    }
  }

  UMS.controller("CourseMaterial", CourseMaterial);
}