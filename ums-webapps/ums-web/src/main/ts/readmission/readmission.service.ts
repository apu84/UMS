module ums {
  export class ReadmissionService {
    public static $inject = ['HttpClient'];
    private responseMap: {[key: string]: string} = {
      APPLIED: 'APPLIED',
      ALLOWED: 'ALLOWED',
      NOT_ALLOWED: 'NOT_ALLOWED',
      NOT_IN_READMISSION_SLOT: 'NOT_IN_READMISSION_SLOT',
      REQUIRES_SESSIONAL: 'REQUIRES_SESSIONAL',
      NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE: 'NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE',
      NOT_TAKEN_MINIMUM_NO_OF_COURSE: 'NOT_TAKEN_MINIMUM_NO_OF_COURSE',
      CONTAINS_INVALID_COURSE: 'CONTAINS_INVALID_COURSE'
    };

    constructor(private httpClient: HttpClient) {
    }

    public isReadmissionApplicable(): ng.IPromise < boolean > {
      return this.httpClient.get('readmission', HttpClient.MIME_TYPE_TEXT, (response: any) => {
        return true;
      });
    }
  }
  UMS.service('ReadmissionService', ReadmissionService);
}