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
    shortName: string;
  }

  export class CourseTeacherService {
    public courseMapWithCourseTeachers: any = {};
    public courseTeacherList: CourseTeacherInterface[] = [];
    public courseTeacherUrl: string;
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

      this.courseTeacherUrl = '/ums-webservice-academic/academic/courseTeacher';
    }

    public getCourseTeacherBySemesterAndCourseAndSection(semesterId: number, courseId: string, section: string): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.get(this.courseTeacherUrl + "/semesterId/" + semesterId + "/courseId/" + courseId + "/section/" + section, HttpClient.MIME_TYPE_JSON,
          (response: CourseTeacherInterface[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getCourseTeacherByProgramAndSemesterAndYearAndAcademicSemester(semesterId: number, programId: number, section: string, year: number, semester: number): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.get(this.courseTeacherUrl + "/programId/" + programId + "/semesterId/" + semesterId + "/section/" + section + "/year/" + year + "/semester/" + semester, HttpClient.MIME_TYPE_JSON,
          (response: CourseTeacherInterface[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getCourseTeacherBySemesterAndTeacher(semesterId: number, teacherId: string): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.get(this.courseTeacherUrl + "/semesterId/" + semesterId + "/teacherId/" + teacherId, HttpClient.MIME_TYPE_JSON,
          (response: CourseTeacherInterface[]) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getCourseTeacherBySemesterAndCourseList(semesterId: number, courseIdList: string[]): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.post(this.courseTeacherUrl + "/semesterId/"+semesterId, courseIdList, HttpClient.MIME_TYPE_JSON)
          .success((response: CourseTeacherInterface[]) => defer.resolve(response))
          .error((response) => {
            this.notify.error("Error in saving data");
            console.error(response);
            defer.resolve(response);
          })
      return defer.promise;
    }


    public saveOrUpdateCourseTeacher(courseTeacherList: CourseTeacherInterface[]): ng.IPromise<CourseTeacherInterface[]> {
      let defer: ng.IDeferred<CourseTeacherInterface[]> = this.$q.defer();
      this.httpClient.put(this.courseTeacherUrl + "/saveOrUpdate", courseTeacherList, HttpClient.MIME_TYPE_JSON)
          .success((response: CourseTeacherInterface[]) => defer.resolve(response))
          .error((response) => {
            this.notify.error("Error in saving data");
            console.error(response);
            defer.resolve(undefined);
          })
      return defer.promise;
    }

    public delete(id: string) {
      this.httpClient.doDelete(this.courseTeacherUrl + "/id/" + id)
          .success((response: CourseTeacherInterface[]) => '')
          .error((response) => {
            this.notify.error("Error in deleting data");
          });
    }

    public deleteTeacherList(courseTeacherList: CourseTeacherInterface[]): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      let teacherList: CourseTeacherInterface[] = [];
      courseTeacherList.forEach((c: CourseTeacherInterface) => {
        if (c.id != undefined)
          teacherList.push(c);
      })
      this.httpClient.put(this.courseTeacherUrl + "/delete", teacherList, HttpClient.MIME_TYPE_JSON)
          .success((response) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            this.notify.error("Error in deleting teacher list");
            defer.resolve(undefined);
          })
      return defer.promise;
    }
  }

  UMS.service("courseTeacherService", CourseTeacherService);
}