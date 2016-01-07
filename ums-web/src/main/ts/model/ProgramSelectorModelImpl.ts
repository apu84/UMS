///<reference path="GenericModel.ts"/>
///<reference path="ProgramSelectorModel.ts"/>

module ums {/*
  export class ProgramSelectorModelImpl implements ProgramSelectorModel {
    programTypes:Array<GenericModel>;
    departments:Array<GenericModel>;
    programs:Array<ProgramModel>;
    semesters:Array<GenericModel>;

    programId:string;
    departmentId:string;
    programTypeId:string;

    constructor(private appConstants:any, private httpClient:HttpClient) {
      this.programTypes = this.appConstants.programType;
      this.departments = [];
      this.programs = this.appConstants.ugPrograms;
      this.semesters = [];

      this.programId = '';
      this.departmentId = '';
      this.programTypeId = '';
    }

    public getDepartments():void {
      if (this.programTypeId == "11") {
        this.departments = this.appConstants.ugDept;
      }
      else if (this.programTypeId == "22") {
        this.departments = this.appConstants.pgDept;
      }
      else {
        this.departments = [{id: '', name: 'Select Dept./School'}];
      }

      if (this.programTypeId != this.appConstants.Empty) {
        this.httpClient.get('academic/semester/program-type/' + this.programTypeId + "/limit/0", 'application/json',
            (json:any, etag:string) => {
              this.semesters = json.entries;
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }
    }

    public getPrograms():void {
      if (this.departmentId != "" && this.programTypeId == "11") {
        var ugProgramsArr:any = this.appConstants.ugPrograms;
        var ugProgramsJson = $.map(ugProgramsArr, (el) => {
          return el
        });
        var resultPrograms:any = $.grep(ugProgramsJson, (e:any) => {
          return e.deptId == this.departmentId;
        });

        this.programs = resultPrograms[0].programs;
        this.programId = this.programs[0].id;
      }
      else {
        this.programs = [{id: '', longName: 'Select a Program'}];
        this.programId = "";
      }

    }
  }*/
}