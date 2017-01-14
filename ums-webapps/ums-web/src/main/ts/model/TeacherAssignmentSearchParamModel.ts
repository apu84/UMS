///<reference path="ProgramSelectorModel.ts"/>

module ums {
  export class TeacherAssignmentSearchParamModel {
    semesterId: string;
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;
    programSelector: ProgramSelectorModel;
    courseId: string;

    constructor(appConstants:any, httpClient:HttpClient,
                enableSemester: boolean, pEnableAllDepartment?: boolean,
                pEnableAllProgram?: boolean) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient, enableSemester, pEnableAllDepartment,
          pEnableAllProgram);
      this.semesterId = '';
      this.academicYearId = '';
      this.academicSemesterId = '';
      this.courseCategoryId = '';
      this.courseId = '';
    }
  }
}
