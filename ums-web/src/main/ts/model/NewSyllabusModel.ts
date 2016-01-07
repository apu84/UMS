///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export interface NewSyllabusModel extends ProgramSelectorModel{
    semesterId: string;
    syllabusId : string;
  }
}