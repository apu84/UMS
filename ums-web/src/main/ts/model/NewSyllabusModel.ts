///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export class NewSyllabusModel {
    semesterId: string;
    syllabusId: string;
    programSelector: ProgramSelectorModel;

    constructor(appConstants: any, httpClient: HttpClient) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient);

      this.semesterId = '';
      this.syllabusId = '';
    }
  }
}