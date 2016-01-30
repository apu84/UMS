///<reference path="ProgramSelectorModel.ts"/>

module ums {
  export class CourseTeacherSearchParamModel {
    semesterId: string;
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;
    programSelector: ProgramSelectorModel;

    constructor(appConstants:any, httpClient:HttpClient) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient);
      this.semesterId = '';
      this.academicYearId = '';
      this.academicSemesterId = '';
      this.courseCategoryId = '';
    }
  }
}