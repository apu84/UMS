module ums {
  export interface ProgramSelectorModel {
    programTypes: Array<GenericProperty>;
    departments: Array<GenericProperty>;
    programs: [{
      id: string,
      longName: string;
    }];
    semesters: Array<GenericProperty>;

    selectedProgram: string;
    selectedDepartment: string;
    selectedProgramType: string;

    getDepartments(programType:string): void;
    getPrograms(deparment:string): void;
  }
}