///<reference path="../model/ProgramSelectorModel.ts"/>

module ums {
  interface ProgramSelectorScope extends ng.IScope {
    model: ProgramSelectorModel;
  }

  export class ProgramSelector implements ng.IDirective {

    constructor(private appConstants:any, private httpClient:HttpClient) {
    }

    public restrict:string = "A";
    public scope = {
      model: "="
    };

    public link = (scope:ProgramSelectorScope, element:JQuery, attributes:any) => {
      scope.model.programTypes = this.appConstants.programType;
      scope.model.programs = this.appConstants.ugPrograms;

      //scope.getDepartments = this.getDepartments.bind(scope);
    };

    public templateUrl:"/views/directive/program-selector.html";

/*    private getDepartments(programType:string, scope:ProgramSelectorScope) {
      scope.model.programs = [{id: '', longName: 'Select a Program'}];
      scope.selectedProgram = "";

      if (programType == "11") {
        scope.model.departments = this.appConstants.ugDept;
      }
      else if (programType == "22") {
        scope.model.departments = this.appConstants.pgDept;
      }
      else {
        scope.model.departments = [{id: '', name: 'Select Dept./School'}];
      }

      scope.selectedDepartment = "";
      /!**--------Semester Load----------------**!/
      this.httpClient.get('academic/semester/program-type/' + scope.selectedProgramType + '/limit/0',
          this.appConstants.mimeTypeJson,
          (responseJson:any, etag:string) => {
            scope.model.semesters = responseJson.data.entries;
          },
          (error) => {
            console.error(error);
          });
    }

    private getPrograms(departmentId:string, scope:ProgramSelectorScope):void {
      if (departmentId != "" && scope.selectedProgramType == "11") {
        var ugProgramsArr:any = this.appConstants.ugPrograms;
        var ugProgramsJson = $.map(ugProgramsArr, (el) => {
          return el
        });
        var resultPrograms:any = $.grep(ugProgramsJson, (e:any) => {
          return e.deptId == scope.selectedDepartment;
        });

        scope.model.programs = resultPrograms[0].programs;
        scope.selectedProgram = scope.model.programs[0].id;
      }
      else {
        scope.model.programs = [{id: '', longName: 'Select a Program'}];
        scope.selectedProgram = "";
      }
    }*/
  }

  UMS.directive("programSelector", ['appConstants', 'httpClient', (appConstants, httpClient)
      => new ProgramSelector(appConstants, httpClient)]);
}