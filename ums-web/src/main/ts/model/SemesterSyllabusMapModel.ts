///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export class SemesterSyllabusMapModel {
    semesterId: string;
    mapId:string;
    syllabuses:any;
    programSelector: ProgramSelectorModel;
    
    constructor(appConstants:any, httpClient:HttpClient) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient);
      this.semesterId = '';
      this.mapId = '';
      this.syllabuses = '';
    }
  }
}