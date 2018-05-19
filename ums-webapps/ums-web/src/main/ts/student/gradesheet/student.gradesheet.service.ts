module ums {
  export interface GradeSheetModel {
    name: string;
    department: string;
    program: string;
    courses: any[];
  }

  export class StudentGradesheetService {
    public static $inject = ['HttpClient', 'appConstants', 'notify', '$q'];

    constructor(private httpClient: HttpClient,
                private appConstants: any,
                private notify: Notify,
                private $q: ng.IQService) {

    }

    public getProgramSelectorModel(): ng.IPromise<ProgramSelectorModel> {
      let defer: ng.IDeferred<ProgramSelectorModel> = this.$q.defer();
      this.getLoggedInStudent().then(
          (response: Student) => {
            let programSelectorModel: ProgramSelectorModel
                = new ProgramSelectorModel(this.appConstants, this.httpClient, true);
            programSelectorModel.setProgramType(response.programTypeId, FieldViewTypes.hidden);
            programSelectorModel.setDepartment(response.departmentId, FieldViewTypes.hidden);
            programSelectorModel.setProgram(response.programId, FieldViewTypes.hidden);
            defer.resolve(programSelectorModel);
          });
      return defer.promise;
    }

    public getLoggedInStudent(): ng.IPromise<Student> {
      let defer: ng.IDeferred<Student> = this.$q.defer();
      this.httpClient.get("academic/student", HttpClient.MIME_TYPE_JSON,
          (response: Student) => {
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getGradeSheet(semesterId: string): ng.IPromise<GradeSheetModel> {
      let defer: ng.IDeferred<GradeSheetModel> = this.$q.defer();
      this.httpClient.get(`academic/gradesheet/semester/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: GradeSheetModel) => {
            defer.resolve(response);
          },
          (errorResponse: any) => {
            this.notify.error("No gradesheet found for desired semester");
            defer.resolve(null);
          });
      return defer.promise;
    }
  }

  UMS.service('StudentGradesheetService', StudentGradesheetService);
}
