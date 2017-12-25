module ums {
  interface GradeSheetCourse {
    courseNo: string;
    courseTitle: string;
    courseId: string;
    crhr: number;
    grade: string;
    gradePoint: number;
    regType: number;
    semesterId: string;
    carryOver: boolean;
    clearenceImprovementCourse: GradeSheetCourse;
  }
  export interface StudentGradeSheet {
    gpa: number;
    cgpa: number;
    remarks: string;
    courses: GradeSheetCourse[];
  }


  export class GradeSheetService {
    public static $inject = ['HttpClient', 'appConstants', '$q'];

    constructor(private httpClient: HttpClient,
                private appConstants: any,
                private $q: ng.IQService) {

    }

    public getGradeSheet(semesterId: string): ng.IPromise<StudentGradeSheet> {
      const defer = this.$q.defer();
      this.httpClient.get(`academic/studentGradeSheet/semester/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: StudentGradeSheet) => {
            response.courses = this.processResult(response.courses, semesterId);
            defer.resolve(response);
          }
      );
      console.log("so it works...awesome, ts is excluded");
      return defer.promise;
    }

    private processResult(courses: GradeSheetCourse[], semesterId: string): GradeSheetCourse[] {

      for (let i = courses.length - 1; i >= 0; i--) {
        if (courses[i].carryOver && courses[i].semesterId !== semesterId) {
          delete courses[i].grade;
          delete courses[i].gradePoint;
        }

        if (courses[i].semesterId === semesterId
            && (courses[i].regType === this.appConstants.courseRegType.CLEARANCE
            || courses[i].regType === this.appConstants.courseRegType.IMPROVEMENT)) {

          for (let j = courses.length - 1; j >= 0; j--) {
            if (i !== j && courses[i].courseId === courses[j].courseId) {
              courses[j].clearenceImprovementCourse = courses[i];
              courses.splice(i, 1);
              break;
            }
          }
        }
      }
      return courses;
    }
  }

  UMS.service("GradeSheetService", GradeSheetService);
}
