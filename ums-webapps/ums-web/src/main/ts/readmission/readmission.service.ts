module ums {
  export interface ReadmissionCourse {
    courseId: string;
    courseTitle: string;
    courseCrHr: string;
    lastAppeared: string;
    appliedOn: string;
    applied: boolean;
  }

  interface ReadmissionCourseResponse {
    entries: ReadmissionCourse[];
  }

  export interface ReadmissionResponseMap {
    APPLIED: string,
    ALLOWED: string,
    NOT_ALLOWED: string,
    NOT_IN_READMISSION_SLOT: string,
    REQUIRES_SESSIONAL: string,
    NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE: string,
    NOT_TAKEN_MINIMUM_NO_OF_COURSE: string,
    CONTAINS_INVALID_COURSE: string
  }

  export class ReadmissionService {
    public static $inject = ['HttpClient', 'notify'];
    public responseMap: ReadmissionResponseMap = {
      APPLIED: 'APPLIED',
      ALLOWED: 'ALLOWED',
      NOT_ALLOWED: 'NOT_ALLOWED',
      NOT_IN_READMISSION_SLOT: 'NOT_IN_READMISSION_SLOT',
      REQUIRES_SESSIONAL: 'REQUIRES_SESSIONAL',
      NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE: 'NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE',
      NOT_TAKEN_MINIMUM_NO_OF_COURSE: 'NOT_TAKEN_MINIMUM_NO_OF_COURSE',
      CONTAINS_INVALID_COURSE: 'CONTAINS_INVALID_COURSE'
    };

    constructor(private httpClient: HttpClient,
                private notify: Notify,
                private $q: ng.IQService) {
    }

    public getReadmissionStatus(): ng.IPromise <string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.get('readmission', HttpClient.MIME_TYPE_TEXT, (response: string) => {
        defer.resolve(response);
      });
      return defer.promise;
    }

    public getAppliedCourses(): ng.IPromise<ReadmissionCourse[]> {
      let defer: ng.IDeferred<ReadmissionCourse[]> = this.$q.defer();
      this.httpClient.get('readmission/application', HttpClient.MIME_TYPE_JSON,
          (response: ReadmissionCourseResponse) => response.entries);
      return defer.promise;
    }

    public getApplicableCourse(): ng.IPromise<ReadmissionCourse[]> {
      let defer: ng.IDeferred<ReadmissionCourse[]> = this.$q.defer();
      this.httpClient.get('readmission/applicable-courses', HttpClient.MIME_TYPE_JSON,
          (response: ReadmissionCourseResponse) => response.entries);
      return defer.promise;
    }

    public apply(courses: ReadmissionCourse[]): ng.IPromise<string> {
      let defer: ng.IDeferred<string> = this.$q.defer();
      this.httpClient.post('readmission/apply', {'entries': this.getSelectedCourses(courses)}, HttpClient.MIME_TYPE_JSON)
          .success((response: string) => defer.resolve(response))
          .error((data) => {
            this.notify.error(data);
            defer.resolve(null);
          });
      return defer.promise;
    }

    private getSelectedCourses(courses: ReadmissionCourse[]) {
      var selectedCourses = [];
      for (var i = 0; i < courses.length; i++) {
        if (courses[i].applied) {
          selectedCourses.push(courses[i]);
        }
      }
    }
  }

  UMS.service('ReadmissionService', ReadmissionService);
}
