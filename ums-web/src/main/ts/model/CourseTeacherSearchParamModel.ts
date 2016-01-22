///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export interface CourseTeacherSearchParamModel extends ProgramSelectorModel{
    semesterId: string;
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;
  }
}