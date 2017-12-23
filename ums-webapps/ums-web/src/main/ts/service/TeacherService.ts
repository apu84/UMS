module ums {
  export interface ITeacher {
    id?: string;
    name?: string;
  }

  export interface ITeachers {
    entries: Array<ITeacher>;
  }

  export class TeacherService {
    public static $inject = ['HttpClient', '$q'];

    constructor(private httpClient: HttpClient, private $q: ng.IQService) {

    }

    public getByDepartment(departmentId: string): ng.IPromise<ITeachers> {
      var defer = this.$q.defer<ITeachers>();

      this.httpClient.get(`academic/teacher/department/${departmentId}`,
          HttpClient.MIME_TYPE_JSON,
          (data: ITeachers, etag: string) => {
            defer.resolve(data);
          });

      return defer.promise;
    }
  }

  UMS.service("TeacherService", TeacherService);
}