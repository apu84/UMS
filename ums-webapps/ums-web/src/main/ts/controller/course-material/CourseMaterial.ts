module ums {
  export class CourseMaterial {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient', 'fileManagerConfig'];
    private currentUser: LoggedInUser;
    private courseMaterialSearchParamModel: ProgramSelectorModel;

    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any,
                private httpClient: HttpClient,
                private fileManagerConfig: any) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.preCourse = '';
      $scope.currentCourse = '';
      this.httpClient.get("users/current", HttpClient.MIME_TYPE_JSON,
          (response: LoggedInUser) => {
            this.courseMaterialSearchParamModel = new ProgramSelectorModel(this.appConstants, this.httpClient, true);
            this.courseMaterialSearchParamModel.setProgramType(this.appConstants.programTypeEnum.UG,
                FieldViewTypes.selected);
            this.courseMaterialSearchParamModel.setDepartment(response.departmentId,
                FieldViewTypes.hidden);
            this.courseMaterialSearchParamModel.setProgram(null, FieldViewTypes.hidden);
            this.currentUser = response;
            this.$scope.courseMaterialSearchParamModel = this.courseMaterialSearchParamModel;
          });

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.initFileManager = this.initFileManager.bind(this);

      //this.initFileManager("Fall,2015", "EEE 1101");
    }

    private fetchCourseInfo(): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;
      this.$scope.selectedCourseNo = '';

      this.httpClient.get("academic/courseTeacher/" + this.courseMaterialSearchParamModel.semesterId + "/"
          + this.currentUser.employeeId + "/course", HttpClient.MIME_TYPE_JSON,
          (response: {entries: CourseTeacherModel[]}) => {
            this.$scope.entries = this.aggregateResult(response.entries);
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          }
      )
    }

    private aggregateResult(courses: CourseTeacherModel[]): CourseTeacherModel[] {
      var courseList: CourseTeacherModel[] = [];
      var courseMap: {[courseId: string]: CourseTeacherModel} = {};
      courses.forEach((courseTeacher: CourseTeacherModel) => {

        if (courseTeacher.courseId in courseMap) {
          courseMap[courseTeacher.courseId].section
              = courseMap[courseTeacher.courseId].section + "," + courseTeacher.section;
        } else {
          courseList[courseList.length] = courseTeacher;
          courseMap[courseTeacher.courseId] = courseTeacher;
        }
      });

      return courseList;
    }

    private initFileManager(semesterName: string, courseNo: string,
                            semesterId: string, courseId: string): void {

      var baseUri: string = '/ums-webservice-academic/academic/courseMaterial/semester/' + semesterName + "/course/" + courseNo;
      var downloadBaseUri: string = '/ums-webservice-academic/academic/courseMaterial/download/semester/' + semesterName + "/course/" + courseNo;

      $("#courseSelectionDiv").hide(80);
      $("#topArrowDiv").show(50);

      this.fileManagerConfig.appName = semesterName + ' > ' + courseNo;
      this.fileManagerConfig.tplPath = 'views/file-manager';
      this.fileManagerConfig.listUrl = baseUri;
      this.fileManagerConfig.createFolderUrl = baseUri;
      this.fileManagerConfig.uploadUrl = baseUri + "/upload";
      this.fileManagerConfig.renameUrl = baseUri;
      this.fileManagerConfig.copyUrl = baseUri;
      this.fileManagerConfig.moveUrl = baseUri;
      this.fileManagerConfig.removeUrl = baseUri;
      this.fileManagerConfig.downloadFileUrl = downloadBaseUri;
      this.fileManagerConfig.downloadMultipleUrl = downloadBaseUri;
      this.fileManagerConfig.compressUrl = baseUri;
      this.fileManagerConfig.hidePermissions = true;
      this.fileManagerConfig.hideOwner = false;
      this.fileManagerConfig.multipleDownloadFileName = 'CourseMaterial-' + semesterName + "-" + courseNo + '.zip';
      this.fileManagerConfig.searchForm = true;
      this.fileManagerConfig.languageSelection = false;
      this.fileManagerConfig.allowedActions = angular.extend(this.fileManagerConfig.allowedActions, {
        createAssignmentFolder: true,
        changePermissions: false,
        createFolder: true,
        pickFiles: true,
        pickFolders: true
      });
      this.fileManagerConfig.additionalParams = {
        semesterId: semesterId,
        courseId: courseId
      };
      this.fileManagerConfig.pickCallback = (item) => {
        var msg = 'Picked %s "%s" for external use'
            .replace('%s', item.type)
            .replace('%s', item.fullPath());
        window.alert(msg);
      };

      this.$scope.reloadOn = courseNo;
    }
  }

  UMS.controller("CourseMaterial", CourseMaterial);
}
