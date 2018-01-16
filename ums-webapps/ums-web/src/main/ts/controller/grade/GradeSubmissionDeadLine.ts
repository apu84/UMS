module ums {
  import AdditionalRolePermissions = ums.AdditionalRolePermissions;

  interface IGradeSubmissionDeadline extends ng.IScope {
    semesterList: Array<Semester>;
    courseNo: string;
    semesterId: number;
    examType: any;
    courseType: string;
    examRoutineArr: any;
    examGradeStatisticsArr: Array<IExamGrade>;
    examGradeStatisticsArrTemp: Array<IExamGrade>;
    examGradeStatisticsMap: any;
    coloredExamGradeId: number;
    coloredExamGrade: IExamGrade;
    user: User;
    additionalRolePermission: AdditionalRolePermissions;
    examDate: string;
    showLoader: boolean;
    showTable: boolean;
    showButton: boolean;
    editable: boolean;

    //functions
    getSemesters: Function;
    getExamDates: Function;
    fetchDeadlineInformation: Function;
    dateChanged: Function;
    saveChanges: Function;
    cancel: Function;
    semesterSelected: Function;
    checkCourseNo: Function;
    convertToJson: Function;
    dateTouched: Function;
  }

  interface IExamGrade {
    id: number
    examDate: string;
    programShortName: string;
    courseId: string;
    courseNo: string;
    courseTitle: string;
    courseCreditHour: string;
    totalStudents: number;
    lastSubmissionDatePrep: string;
    lastSubmissionDateScr: string;
    lastSubmissionDateHead: string;
    lastSubmissionDateCoe: string;
    changed: boolean;
    backgroundColor: string;
  }

  class GradeSubmissionDeadLine {

    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService',
      'examRoutineService', 'examGradeService', 'userService', 'additionalRolePermissionsService'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IGradeSubmissionDeadline,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService, private semesterService: SemesterService,
                private examRoutineService: ExamRoutineService,
                private examGradeService: ExamGradeService, private userService: UserService, private additionalRolePermissionService: AdditionalRolePermissionsService) {

      $scope.showLoader = false;
      $scope.showTable = false;
      $scope.showButton = false;
      $scope.editable = false;
      $scope.courseNo = "";
      $scope.courseType = "1";
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.getExamDates = this.getExamDates.bind(this);
      $scope.fetchDeadlineInformation = this.fetchDeadlineInformation.bind(this);
      $scope.dateChanged = this.dateChanged.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.convertToJson = this.convertToJson.bind(this);
      $scope.saveChanges = this.saveChanges.bind(this);
      $scope.checkCourseNo = this.checkCourseNo.bind(this);
      $scope.dateTouched = this.dateTouched.bind(this);
      Utils.setValidationOptions("form-horizontal");
      this.getLoggedUserAdditionalPermissions();
    }

    private getLoggedUserAdditionalPermissions() {
      this.additionalRolePermissionService.fetchLoggedUserAdditionalRolePermissions().then((permissions: AdditionalRolePermissions[]) => {
        console.log("Permissions");
        console.log(permissions[0].permissions.entries);
      });
    }

    private checkCourseNo(courseNo: string) {
      this.$scope.courseNo = courseNo;
    }

    private getSemesters(): ng.IPromise<any> {

      this.$scope.semesterList = [];
      var defer = this.$q.defer();

      this.semesterService.fetchSemesters(Utils.UG).then((semesterArr: Array<Semester>) => {
        this.$scope.semesterList = semesterArr;
        this.$scope.semesterId = semesterArr[0].id;
        this.$scope.examType = Utils.EXAM_TYPE_REGULAR;
        this.getExamDates();
        defer.resolve(semesterArr);
      });

      return defer.promise;
    }


    private saveChanges() {
      this.convertToJson().then((json: any) => {
        this.$scope.examGradeStatisticsArr.forEach(e => e.backgroundColor = "");
        this.$scope.examGradeStatisticsArrTemp = [];
        this.$scope.examGradeStatisticsArrTemp = angular.copy(this.$scope.examGradeStatisticsArr);
        console.log(json);
        this.examGradeService.updateGradeSubmissionDeadLine(json).then((message: any) => {
          this.notify.success(message);
          this.$scope.showButton = false;

        });
      });
    }

    private dateChanged(examGrade: IExamGrade) {
      console.log("In date changed");
      console.log(examGrade);
      if (examGrade.lastSubmissionDatePrep != null && examGrade.lastSubmissionDateScr != null && examGrade.lastSubmissionDateHead != null) {
        this.$scope.showButton = true;
        examGrade.changed = true;
      }
      // this.$scope.showButton = true;
      // examGrade.changed = true;
    }


    private dateTouched(examGrade: IExamGrade) {
      if (this.$scope.coloredExamGradeId == null) {
        this.$scope.coloredExamGradeId = examGrade.id;
      } else {
        var tmpExamGrade: IExamGrade = this.$scope.examGradeStatisticsMap[this.$scope.coloredExamGradeId];
        tmpExamGrade.backgroundColor = "";
        this.$scope.coloredExamGradeId = examGrade.id;
      }
      examGrade.backgroundColor = "yellow";
    }

    private cancel() {
      this.$scope.examGradeStatisticsArr = [];

      this.$scope.examGradeStatisticsArr = angular.copy(this.$scope.examGradeStatisticsArrTemp);
      this.$scope.showButton = false;
      this.initializeDatePickers();
    }

    private initializeDatePickers() {
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);
    }

    private getExamDates(): void {

      if (+this.$scope.courseType == Utils.COURSE_TYPE_THEORY) {
        var semester = this.$scope.semesterList[Utils.findIndex(this.$scope.semesterList, this.$scope.semesterId + "")];
        this.$scope.editable = (semester.status == Utils.SEMESTER_STATUS_ACTIVE);

        var examType = +this.$scope.examType;
        this.$scope.examDate = null;
        console.log(examType);
        console.log(this.$scope.semesterId);
        if (this.$scope.semesterId != null && this.$scope.examType != "") {
          this.examRoutineService.getExamRoutineDates(this.$scope.semesterId, examType).then((examDateArr: any) => {

            this.$scope.examRoutineArr = {};
            console.log(examDateArr);
            this.$scope.examRoutineArr = examDateArr;
          });
        }
      } else {
        this.$scope.examDate = "";
      }


    }

    private fetchDeadlineInformation(): void {

      Utils.expandRightDiv();
      this.$scope.showButton = false;
      this.$scope.examGradeStatisticsArr = [];
      this.$scope.examGradeStatisticsArrTemp = [];
      var examType = +this.$scope.examType;
      console.log("Exam date");
      console.log(this.$scope.examDate);

      this.$scope.showLoader = true;
      this.examGradeService.getGradeSubmissionDeadLine(this.$scope.semesterId, examType, this.$scope.examDate, this.$scope.courseType)
          .then((outputArr: Array<IExamGrade>) => {
            this.$scope.examGradeStatisticsMap = {};
            if (outputArr.length == 0) {
              this.$scope.showLoader = false;
              this.notify.error("No relevant data found");
              this.$scope.showTable = false;
            }
            else {
              for (var i = 0; i < outputArr.length; i++) {
                outputArr[i].changed = false;
                outputArr[i].backgroundColor = "";
                this.$scope.examGradeStatisticsMap[outputArr[i].id] = outputArr[i];

              }
              this.$scope.examGradeStatisticsArr = outputArr;
              this.$scope.examGradeStatisticsArrTemp = angular.copy(outputArr);
              this.$scope.showTable = true;
              this.$scope.showLoader = false;

              console.log(outputArr);

              this.initializeDatePickers();
            }
          });
    }

    private convertToJson(): ng.IPromise<any> {

      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      for (var i = 0; i < this.$scope.examGradeStatisticsArr.length; i++) {
        if (this.$scope.examGradeStatisticsArr[i].changed == true) {
          this.$scope.examGradeStatisticsArr[i].changed = false;
          var item: any = {};
          item['id'] = this.$scope.examGradeStatisticsArr[i].id;
          item['semesterId'] = this.$scope.semesterId;
          item['courseId'] = this.$scope.examGradeStatisticsArr[i].courseId;
          item['examType'] = +this.$scope.examType;
          item['lastSubmissionDatePrep'] = this.$scope.examGradeStatisticsArr[i].lastSubmissionDatePrep;
          item['lastSubmissionDateScr'] = this.$scope.examGradeStatisticsArr[i].lastSubmissionDateScr;
          item['lastSubmissionDateHead'] = this.$scope.examGradeStatisticsArr[i].lastSubmissionDateHead;
          jsonObject.push(item);
        }
      }
      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);

      return defer.promise;
    }

  }

  UMS.controller('GradeSubmissionDeadLine', GradeSubmissionDeadLine);
}