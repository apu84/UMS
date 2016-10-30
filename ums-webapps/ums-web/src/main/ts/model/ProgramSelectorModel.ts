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

    private setProgramPreset: Function;
    private isProgramPreset: Function;

    private setProgramTypePreset: Function;
    private isProgramTypePreset: Function;

    private enableAllDepartment: Function;
    private isAllDepartmentEnabled: Function;

    private enableAllProgram: Function;
    private isAllProgramEnabled: Function;

    private isSemesterEnabled: Function;
    private enableSemester: Function;

    programId: string;
    departmentId: string;
    programTypeId: string;
    semesterId: string;

    constructor(pAppConstants: any, pHtpClient: HttpClient) {

      var appConstants = pAppConstants;
      var httpClient = pHtpClient;

      var programTypes = appConstants.programType;
      var departments = appConstants.initDept;
      var programs = appConstants.initProgram;
      var semesters = appConstants.initSemester;
      var programPreset: boolean = false;
      var departmentPreset: boolean = false;
      var programTypePreset: boolean = false;
      var enableAllDepartments: boolean = false;
      var enableAllPrograms: boolean = false;
      var semesterEnabled: boolean = false;

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
        // semesters.splice(0, 0, appConstants.initSemester[0]);
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

      this.setProgramPreset = (preset: boolean) => {
        programPreset = preset;
      };

      this.isProgramPreset = () => {
        return programPreset;
      };

      this.setProgramTypePreset = (preset: boolean) => {
        programTypePreset = preset;
      };

      this.isProgramTypePreset = () => {
        return programTypePreset;
      };

      this.isAllDepartmentEnabled = () => {
        return enableAllDepartments;
      };

      this.enableAllDepartment = (pEnable: boolean) => {
        enableAllDepartments = pEnable;
      };

      this.isAllProgramEnabled = () => {
        return enableAllPrograms;
      };

      this.enableAllProgram = (pEnable: boolean) => {
        enableAllPrograms = pEnable;
      };

      this.isSemesterEnabled = () => {
        return semesterEnabled;
      };

      this.enableSemester = (pEnable: boolean) => {
        semesterEnabled = pEnable;
      };

      this.programId = '';
      this.departmentId = '';
    }

    public setDepartment(departmentId: string, preSelected?: boolean): void {
      this.departmentId = departmentId;
      if (!preSelected) {
        this.setDepartmentPreset(true);
      }
    }

    public setProgramId(programId: string, preSelected?: boolean): void {
      this.programId = programId;
      if (!preSelected) {
        this.setProgramPreset(true);
      }
    }

    public setProgramTypeId(programTypeId: string, preSelected?: boolean): void {
      this.programTypeId = programTypeId;
      if (!preSelected) {
        this.setProgramTypePreset(true);
      }
      this.loadDepartments();
      this.loadSemester();
    }

    public enableAllDepartmentOption(enable: boolean) {
      this.enableAllDepartment(enable);
    }

    public enableAllProgramOption(enable: boolean) {
      this.enableAllProgram(enable);
    }

    public enableSemesterOption(enable: boolean): void {
      this.enableSemester(enable);
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

      if (this.isAllDepartmentEnabled()) {
        if (this.getDepartments()[this.getDepartments().length - 1].id != '') {
          this.getDepartments().push({id: '', name: 'All'})
        }
      }

      if (!this.isDepartmentPreset) {
        this.departmentId = '';
      } else {
        this.loadPrograms();
      }
    }

    public loadPrograms(): void {
      if (this.departmentId != "" && this.programTypeId == "11") {
        var ugProgramsArr: any = this.getAppConstants().ugPrograms;
        var ugProgramsJson = $.map(ugProgramsArr, (el) => {
          return el
        });
        var resultPrograms: any = $.grep(ugProgramsJson, (e: any) => {
          return e.deptId == this.departmentId;
        });

        this.setPrograms(resultPrograms[0].programs);
        this.programId = this.getPrograms()[0].id;
      }
      else {
        this.setPrograms([{id: '', longName: 'Select a Program'}]);
        this.programId = "";
      }

      if (this.isAllProgramEnabled()) {
        this.getPrograms().push({id: '', longName: 'All'});
      }
    }

    public loadSemester(): void {
      if (this.programTypeId && this.programTypeId != this.getAppConstants().Empty) {
        this.getHttpClient().get('academic/semester/program-type/' + this.programTypeId + "/limit/10",
            HttpClient.MIME_TYPE_JSON,
            (json: any, etag: string) => {
              this.setSemesters(json.entries);
              if (this.isSemesterEnabled()) {
                this.semesterId = (this.getSemesters()[0]).id +'';
              }
            },
            (response: ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }
    }
  }
}
