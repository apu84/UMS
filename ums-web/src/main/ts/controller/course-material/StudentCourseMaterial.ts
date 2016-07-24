module ums {
  export class StudentCourseMaterial {
    public static $inject = ['$scope', '$stateParams'];

    constructor(private $scope: any, private $stateParams: any) {
      var semesterName = this.$stateParams["1"];
      var courseNo = this.$stateParams["2"];
      var baseUri: string = '/ums-webservice-common/academic/student/courseMaterial/semester/' + semesterName + "/course/" + courseNo;
      var downloadBaseUri: string = '/ums-webservice-common/academic/student/courseMaterial/download/semester/' + semesterName + "/course/" + courseNo;
      FILEMANAGER_CONFIG.set({
        appName: semesterName + ' > ' + courseNo,
        listUrl: baseUri,
        uploadUrl: baseUri + "/upload",
        downloadFileUrl: downloadBaseUri,
        downloadMultipleUrl: downloadBaseUri,
        hidePermissions: true,
        hideOwner: false,
        multipleDownloadFileName: 'CourseMaterial-' + semesterName + "-" + courseNo + '.zip',
        allowedActions: angular.extend(FILEMANAGER_CONFIG.$get().allowedActions, {
          createAssignmentFolder: false,
          upload: true,
          rename: true,
          move: false,
          copy: false,
          edit: false,
          changePermissions: false,
          compress: false,
          compressChooseName: false,
          extract: false,
          download: true,
          downloadMultiple: true,
          preview: false,
          remove: false,
          createFolder: false
        })
      });
    }
  }

  UMS.controller("StudentCourseMaterial", StudentCourseMaterial);
}