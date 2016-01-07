///<reference path="ProgramSelectorModelImpl.ts"/>
module ums {
  export class NewSyllabusModelImpl extends ProgramSelectorModelImpl implements NewSyllabusModel {
    semesterId:string;
    syllabusId:string;

    constructor(appConstants:any, httpClient:HttpClient) {
      super(appConstants, httpClient);

      this.semesterId = '';
      this.syllabusId = '';
    }
  }
}