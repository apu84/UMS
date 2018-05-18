module ums {
  export class StudentGradesheetController {
    public static $inject = ['StudentGradesheetService'];
    private programSelectorModel: ProgramSelectorModel;
    private gradesheetModel: GradeSheetModel;

    constructor(private studentGradeSheetService: StudentGradesheetService) {
      this.studentGradeSheetService.getProgramSelectorModel()
          .then((programSelectorModel) => this.programSelectorModel = programSelectorModel);
    }

    public getGradesheet(): void {
      this.studentGradeSheetService.getGradeSheet(this.programSelectorModel.semesterId)
          .then((gradesheetModel) => this.gradesheetModel = gradesheetModel);
    }
  }
}