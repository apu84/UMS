///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export interface CourseTeacherSearchParamModel extends ProgramSelectorModel{
    semesterId: number;
    academicYearId:string;
    academicSemesterId:string;
    courseCategoryId:string;
  }
}