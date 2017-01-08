module ums {
  export class StudentCourseMaterial {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient', 'fileManagerConfig'];
    private currentUser: Student;
    private courseMaterialSearchParamModel: ProgramSelectorModel;

    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,
                private fileManagerConfig: any) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      this.httpClient.get("academic/student", HttpClient.MIME_TYPE_JSON,
          (response: Student) => {
            this.courseMaterialSearchParamModel = new ProgramSelectorModel(this.appConstants, this.httpClient,
                true);
            this.courseMaterialSearchParamModel.setProgramType(response.programTypeId, FieldViewTypes.hidden);
            this.courseMaterialSearchParamModel.setDepartment(response.departmentId, FieldViewTypes.hidden);
            this.courseMaterialSearchParamModel.setProgram(response.programId, FieldViewTypes.hidden);
            this.currentUser = response;
            this.$scope.courseMaterialSearchParamModel = this.courseMaterialSearchParamModel;
          });

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.initFileManager = this.initFileManager.bind(this);

      //this.initFileManager("Fall,2015", "EEE 1101");
    }

    private fetchCourseInfo(courseNo: string): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;
      this.$scope.selectedCourseNo = '';
      this.httpClient.get("academic/course/semester/" + this.courseMaterialSearchParamModel.semesterId + "/program/"
          + this.courseMaterialSearchParamModel.programId + "/year/"
          + this.currentUser.year + "/academicSemester/" + this.currentUser.academicSemester, HttpClient.MIME_TYPE_JSON,
          (response: {entries: Course[]}) => {
            this.$scope.entries = response.entries;
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          }
      );
    }

    private initFileManager(courseNo: string,
                            semesterId: string,
                            courseId: string): void {
      var semesterName = this.getSelectedSemester();
      var baseUri: string = '/ums-webservice-academic/academic/student/courseMaterial/semester/' + semesterName + "/course/" + courseNo;
      var downloadBaseUri: string = '/ums-webservice-academic/academic/student/courseMaterial/download/semester/' + semesterName + "/course/" + courseNo;

      $("#courseSelectionDiv").hide(80);
      $("#topArrowDiv").show(50);

      this.fileManagerConfig.appName = semesterName + ' > ' + courseNo;
      this.fileManagerConfig.tplPath = 'views/file-manager';
      this.fileManagerConfig.listUrl = baseUri;
      this.fileManagerConfig.uploadUrl = baseUri + "/upload";
      this.fileManagerConfig.downloadFileUrl = downloadBaseUri;
      this.fileManagerConfig.downloadMultipleUrl = downloadBaseUri;
      this.fileManagerConfig.hidePermissions = true;
      this.fileManagerConfig.hideOwner = false;
      this.fileManagerConfig.multipleDownloadFileName = 'CourseMaterial-' + semesterName + "-" + courseNo + '.zip';
      this.fileManagerConfig.searchForm = true;
      this.fileManagerConfig.languageSelection = false;

      this.fileManagerConfig.additionalParams = {
        semesterId: semesterId,
        courseId: courseId
      };

      this.fileManagerConfig.allowedActions = angular.extend(this.fileManagerConfig.allowedActions, {
        createAssignmentFolder: false,
        upload: true,
        rename: false,
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
      });

      this.fileManagerConfig.pickCallback = (item) => {
        var msg = 'Picked %s "%s" for external use'
            .replace('%s', item.type)
            .replace('%s', item.fullPath());
        window.alert(msg);
      };

      this.$scope.reloadOn = courseNo;
    }

    private getSelectedSemester(): string {
      var semesters = this.courseMaterialSearchParamModel.getSemesters();
      var semesterName = "";
      for (var i = 0; i < semesters.length; i++) {
        if (semesters[i].id == this.courseMaterialSearchParamModel.semesterId) {
          semesterName = semesters[i].name;
        }
      }

      return semesterName;
    }
  }

  UMS.controller("StudentCourseMaterial", StudentCourseMaterial);
}
