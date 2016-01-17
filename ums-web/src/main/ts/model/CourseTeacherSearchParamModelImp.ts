///<reference path="ProgramSelectorModelImpl.ts"/>
///<reference path="CourseTeacherSearchParamModel.ts"/>
module ums {
  export class CourseTeacherSearchParamModelImp extends ProgramSelectorModelImpl implements CourseTeacherSearchParamModel {
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;

    constructor(appConstants:any, httpClient:HttpClient) {
      super(appConstants, httpClient);

    }
  }
}