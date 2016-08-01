///<reference path="GenericModel.ts"/>

module ums {
  export class ProgramSelectorModel {
    getProgramTypes: Function;
    getDepartments: Function;
    getPrograms: Function;
    getSemesters: Function;
    private setProgramTypes: Function;
    private setDepartments: Function;
    private setPrograms: Function;
    private setSemesters: Function;

    private getAppConstants: Function;
    private getHttpClient: Function;

    private setDepartmentPreset: Function;
    private isDepartmentPreset: Function;

    programId:string;
    departmentId:string;
    programTypeId:string;

    constructor(pAppConstants: any, pHtpClient: HttpClient) {

      var appConstants = pAppConstants;
      var httpClient = pHtpClient;

      var programTypes = appConstants.programType;
      var departments = appConstants.initDept;
      var programs = appConstants.initProgram;
      var semesters = appConstants.initSemester;
      var departmentPreset: boolean = false;


      this.getProgramTypes = () => {
        return programTypes;
      };

      this.getDepartments = () => {
        return departments;
      };

      this.getPrograms = () => {
        return programs;
      };

      this.getSemesters = () => {
        return semesters;
      };

      this.setProgramTypes = () => {
        return programTypes;
      };

      this.setDepartments = (pDepartments: any) => {
        departments = pDepartments;
      };

      this.setPrograms = (pPrograms: any) => {
        programs = pPrograms;
      };

      this.setSemesters = (pSemesters: any) => {
        semesters = pSemesters;
        semesters.splice(0, 0, appConstants.initSemester[0]);
      };

      this.getAppConstants = (): Constants => {
        return appConstants;
      };

      this.getHttpClient = () => {
        return httpClient;
      };

      this.setDepartmentPreset = (preset: boolean) => {
        departmentPreset = preset;
      };

      this.isDepartmentPreset = () => {
        return departmentPreset;
      };

      this.programId = '';
      this.departmentId = '';
      this.programTypeId = '';
    }

    public setDepartment(departmentId: string): void {
      this.departmentId = departmentId;
      this.setDepartmentPreset(true);
    }

    public loadDepartments(): void {
      this.setPrograms(this.getAppConstants().initProgram);
      this.programId = '';

      if (this.programTypeId == "11") {
        this.setDepartments(this.getAppConstants().ugDept);
      }
      else if (this.programTypeId == "22") {
        this.setDepartments(this.getAppConstants().pgDept);
      }
      else {
        this.setDepartments([{id: '', name: 'Select Dept./School'}]);
      }

      if (!this.isDepartmentPreset) {
        this.departmentId = '';
      } else {
        this.loadPrograms();
      }

      if (this.programTypeId != this.getAppConstants().Empty) {
        this.getHttpClient().get('academic/semester/program-type/' + this.programTypeId + "/limit/0", 'application/json',
            (json:any, etag:string) => {
              this.setSemesters(json.entries);
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }
    }

    public loadPrograms(): void {
      if (this.departmentId != "" && this.programTypeId == "11") {
        var ugProgramsArr:any = this.getAppConstants().ugPrograms;
        var ugProgramsJson = $.map(ugProgramsArr, (el) => {
          return el
        });
        var resultPrograms:any = $.grep(ugProgramsJson, (e:any) => {
          return e.deptId == this.departmentId;
        });

        this.setPrograms(resultPrograms[0].programs);
        this.programId = this.getPrograms()[0].id;
      }
      else {
        this.setPrograms([{id: '', longName: 'Select a Program'}]);
        this.programId = "";
      }
    }
  }
}