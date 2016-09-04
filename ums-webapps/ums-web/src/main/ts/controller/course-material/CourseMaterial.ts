module ums {
  export class CourseMaterial {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient'];
    private currentUser: LoggedInUser;
    private courseMaterialSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: any, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      this.httpClient.get("users/current", HttpClient.MIME_TYPE_JSON,
          (response: LoggedInUser) => {
            this.courseMaterialSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
            this.courseMaterialSearchParamModel.programSelector.setDepartment(response.departmentId);
            this.currentUser = response;
            this.$scope.courseMaterialSearchParamModel = this.courseMaterialSearchParamModel;
          });

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.initFileManager = this.initFileManager.bind(this);

      //this.initFileManager("Fall,2015", "EEE 1101");
    }

    private fetchCourseInfo(): void {

      $("#leftDiv").hide();
      $("#arrowDiv").show();
      $("#rightDiv").removeClass("orgRightClass");
      $("#rightDiv").addClass("newRightClass");

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
      var courseMap: {[courseId:string]: CourseTeacherModel} = {};
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
          createAssignmentFolder: true,
          changePermissions: false
        }),
        additionalParams : {
          semesterId: semesterId,
          courseId: courseId
        }
      });

      this.$scope.reloadOn = courseNo;
    }
  }

  UMS.controller("CourseMaterial", CourseMaterial);
}