///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export interface CourseTeacherSearchParamModel extends ProgramSelectorModel{
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;
  }
}