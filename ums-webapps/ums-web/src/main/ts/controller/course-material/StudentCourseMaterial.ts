module ums {
  export class StudentCourseMaterial {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient', 'fileManagerConfig','semesterService'];
      public enrolledSemesterList:Array<Semester>;
      public enrolledSemester:Semester;
      public selectedSemesterId:number;
      public selectedSemesterName:string;
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
      this.$scope.getEnrolledSemester=this.getEnrolledSemesters.bind(this);
      this.$scope.semesterChanged=this.semesterChanged.bind(this);
      this.getEnrolledSemesters();

      //this.initFileManager("Fall,2015", "EEE 1101");
    }
      private semesterChanged(val:any){
          this.$scope.selectedSemesterId=val.id;
          this.$scope.selectedSemesterName=val.name;
      }
    private getEnrolledSemesters(){
                    this.$scope.enrolledSemesterList=[];
                    this.httpClient.get('/ums-webservice-academic/academic/semester/enrolledSemesters', 'application/json',
                          (json: any, etag: string) => {
                             this.$scope.enrolledSemesterList=json.entries;
                             this.$scope.enrolledSemester=this.$scope.enrolledSemesterList[0];
                              this.$scope.selectedSemesterId=this.$scope.enrolledSemester.id;
                              this.$scope.selectedSemesterName=this.$scope.enrolledSemester.name;
                       },
                        (response: ng.IHttpPromiseCallbackArg<any>) => {
                               console.error(response);
                        });
    }

    private fetchCourseInfo(courseNo: string): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;
      this.$scope.selectedCourseNo = '';
      this.httpClient.get("academic/course/semester/" + this.$scope.selectedSemesterId + "/program/"
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
      var semesterName = this.$scope.selectedSemesterName;
        console.log("CourseNo: "+courseNo+"\nSem: "+semesterId+"\ncourseId: "+courseId+"\nName: "+semesterName);
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
        console.log("Test");
        console.log("Another test");
        console.log("Yo YO");
        window.alert(msg);
      };

      this.$scope.reloadOn = courseNo;
    }

  }

  UMS.controller("StudentCourseMaterial", StudentCourseMaterial);
}
