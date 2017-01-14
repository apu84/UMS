module ums {
  export interface StudentGradeSheetScope extends ng.IScope {
    gradeSheetSelector: CourseTeacherSearchParamModel;
    fetchGradeSheet: Function;
    gradeSheet: StudentGradeSheet;
  }
  export class StudentGradeSheet {
    public static $inject = ['$scope', 'appConstants', 'HttpClient', 'GradeSheetService'];
    private gradeSheetSelector: CourseTeacherSearchParamModel;

    constructor(private $scope: StudentGradeSheetScope,
                private appConstants: any,
                private httpClient: HttpClient,
                private gradeSheetService: GradeSheetService) {
      this.httpClient.get("academic/student", HttpClient.MIME_TYPE_JSON,
          (response: Student) => {
            this.gradeSheetSelector = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
            this.gradeSheetSelector.programSelector.setDepartment(response.deptId);
            this.gradeSheetSelector.programSelector.setProgram(response.programId);
            this.gradeSheetSelector.programSelector.setProgramType(response.programTypeId);
            this.gradeSheetSelector.programSelector.enableSemesterOption(true);
            this.$scope.gradeSheetSelector = this.gradeSheetSelector;
          });
      this.$scope.fetchGradeSheet = this.fetchGradeSheet.bind(this);
    }

    private fetchGradeSheet(): void {
      this.gradeSheetService
          .getGradeSheet(this.gradeSheetSelector.programSelector.semesterId)
          .then((studentGradeSheet: StudentGradeSheet)=> {
            this.$scope.gradeSheet = studentGradeSheet;
          });
    }
  }

  UMS.controller('StudentGradeSheet', StudentGradeSheet);
}
