module ums {
  export enum CourseType {
    theory = 1,
    sessional = 2,
    thesis_project = 3,
    none = 0
  }


  export interface CourseTeacherInterface {
    id: string;
    courseType: CourseType;
    courseId: string;
    course: Course;
    teacherId: string;
    semesterId: string;
    teacher: Teacher;
    section: string;
  }

  export class CourseTeacherService {
    public courseMapWithCourseTeachers: any = {};
    public courseTeacherList: CourseTeacherInterface[] = [];
    public courseTeacherUrl: string = 'academic/courseTeacher';
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public getCourseTeacherBySemesterAndCourseAndSection(semesterId: number, courseId: string, section: string): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.get(this.courseTeacherUrl + "/semesterId/" + semesterId + "/courseId/" + courseId + "/section/" + section, HttpClient.MIME_TYPE_JSON,
          (response: CourseTeacherInterface[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getCourseTeacherByProgramAndSemesterAndYearAndAcademicSemester(semesterId: number, courseId: string, section: string): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.get(this.courseTeacherUrl + "/semesterId/" + semesterId + "/courseId/" + courseId + "/section/" + section, HttpClient.MIME_TYPE_JSON,
          (response: CourseTeacherInterface[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public saveOrUpdateCourseTeacher(courseTeacherList: CourseTeacherInterface[]): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.put(courseTeacherList + "/saveOrUpdate", courseTeacherList, HttpClient.MIME_TYPE_JSON)
          .success((response: CourseTeacherInterface[]) => defer.resolve(response))
          .error((response) => {
            this.notify.error("Error in saving data");
            console.error(response);
            defer.resolve(undefined);
          })
      return defer.promise;
    }
  }

  UMS.service("courseTeacherService", CourseTeacherService);
}