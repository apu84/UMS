/// <reference path="GenericModel.ts"/>
module ums {
  export interface ProgramModel extends GenericModel {
    longName: string;
  }
  export interface ProgramSelectorModel {
    programTypes: Array<GenericModel>;
    departments: Array<GenericModel>;
    programs: Array<ProgramModel>;
    semesters: Array<GenericModel>;

    programId: string;
    departmentId: string;
    programTypeId: string;

    getDepartments(): void;
    getPrograms(): void;
  }
}