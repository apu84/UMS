module ums {
  export class StudentInfoService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private notify: Notify) {

    }

    public getStudent(): ng.IPromise<Student> {
      let defer: ng.IDeferred<Student> = this.$q.defer();
      this.httpClient.get('academic/student', HttpClient.MIME_TYPE_JSON,
          (response: any) => defer.resolve(response));
      return defer.promise;
    }
  }

  UMS.service('StudentInfoService', StudentInfoService);
}
