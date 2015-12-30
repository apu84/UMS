///<reference path="GenericProperty.ts"/>

module ums {
  export class ProgramSelectorModelImpl implements ProgramSelectorModel {
    private programTypes:Array<GenericProperty>;
    private departments:Array<GenericProperty>;
    private programs:[{
      id: string,
      longName: string;
    }];
    private semesters:Array<GenericProperty>;

    private selectedProgram:string;
    private selectedDepartment:string;
    private selectedProgramType:string;

    constructor() {
      this.programTypes = [];
      this.departments = [];
      this.programs = [];
      this.semesters = [];

      this.selectedProgram = '';
      this.selectedDepartment = '';
      this.selectedProgramType = '';
    }

    public getDepartments(programType:string):void {

    }

    public getPrograms(deparment:string):void {

    }
  }
}