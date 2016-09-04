module ums {

  export interface SemesterEnrollmentStatus {
    id: number;
    year: number;
    semester: number;
    type: number;
    enrollmentDate: string;
    checked: boolean;
  }

  export class SemesterEnrollmentModel {
    enrollmentType: number;
    programSelector: ProgramSelectorModel;
    semesterId: string;
    status: Array<SemesterEnrollmentStatus>;

    getEnrollmentTypes: Function;

    constructor(appConstants: any, httpClient: HttpClient) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient);
      this.semesterId = '';
      this.enrollmentType = 0;

      this.getEnrollmentTypes = () => {
        return appConstants.semesterEnrollmentTypes;
      };

      this.status = [];
    }
  }
}